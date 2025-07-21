package carroll.nick;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static String getLocalDateAsString(LocalDate localDate){
        return formatter.format(localDate);
    }
}
