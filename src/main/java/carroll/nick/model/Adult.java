package carroll.nick.model;

import carroll.nick.DateUtil;
import carroll.nick.Family;
import carroll.nick.model.FamilyMember;
import carroll.nick.model.Person;

import java.time.LocalDate;


public record Adult(Family family, String name, LocalDate dateOfBirth) implements Person, FamilyMember {
    @Override
    public void introduce() {
        System.out.printf((introduction) + "%n", name, name, family.name(), DateUtil.getLocalDateAsString(dateOfBirth));
    }
}
