package carroll.nick.model;

sealed public interface FamilyMember permits Adult, Child, Animal {
    String introduction = "%s: Hello, I am %s %s. I was born on %s.";
    default void introduce(){};
}
