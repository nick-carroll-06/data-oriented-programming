package carroll.nick.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/*
    this class copied from https://www.youtube.com/watch?v=KoIURitLTHQ&t=615s as a jumping off point
 */
@Component
public class AstroDataService implements CommandLineRunner {
    private final static String API_URL = "http://api.open-notify.org/astros.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void run(String... args) throws Exception {
        switch (fetchAndProcessData()){
            case Result.Success success -> System.out.println(success);
            case Result.Failure failure -> System.out.println(failure);
        }
    }

    //response shapes
    public record Astronaut(String name, String craft){}
    public record AstroResponse(List<Astronaut> people, int number, String message){}

    // sealed interface for processing results
    public sealed interface Result {
        // simplified String map
        record Success(Map<String, List<String>> astronautsByCraft) implements Result {}
        record Failure(String error) implements Result {}
    }

    public Result fetchAndProcessData(){
        try (var client = HttpClient.newHttpClient()){
            var request = HttpRequest.newBuilder().uri(URI.create(API_URL)).header("Accept", "application/json").build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200){
                return new Result.Failure("HTTP Error: " +response.statusCode());
            }

            String body = response.body();
            Files.writeString(Path.of(LocalDateTime.now().getNano()+".json"),body);

            AstroResponse astroResponse = objectMapper.readValue(body, AstroResponse.class);
            Map<String, List<String>> astronautsByCraft = groupAstronautsByCraft(astroResponse);
            return new Result.Success(astronautsByCraft);
        }catch (Exception e){
            return new Result.Failure("Error processing data: " +e.getMessage());
        }
    }

    private Map<String, List<String>> groupAstronautsByCraft(AstroResponse astroResponse) {
        return astroResponse.people.stream().collect(Collectors.groupingBy(Astronaut::craft,
                Collectors.mapping(Astronaut::name, Collectors.toList())));
    }

}
