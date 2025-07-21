package carroll.nick.model;

import carroll.nick.DateUtil;
import carroll.nick.Family;
import carroll.nick.model.FamilyMember;

import java.time.LocalDate;

public record Animal(Family family, String name, LocalDate dateOfBirth, String species) implements FamilyMember {
    static final String animalIntroduction = introduction + " I am a %s! %ss are the best! %n";
    @Override
    public void introduce() {
        System.out.printf((animalIntroduction), name, name, family.name(), DateUtil.getLocalDateAsString(dateOfBirth), species,species);
    }
}
