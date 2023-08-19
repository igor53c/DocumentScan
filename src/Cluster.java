import java.util.*;

public class Cluster {
    private final List<Character> characters;

    public Cluster(Character initialCharacter) {
        characters = new ArrayList<>();
        characters.add(initialCharacter);
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public List<Character> getCharacters() {
        return characters;
    }
}
