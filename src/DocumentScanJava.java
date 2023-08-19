import java.util.*;

public class DocumentScanJava {

    // Set this to false if you want to skip the second scenario
    public static boolean hardMode = false;

    public static Address findAddress(Document document) {
        List<Character> characters = document.getCharacters().stream()
                .sorted(Comparator.comparingDouble(charObj -> ((Character) charObj).getA().y())
                        .thenComparingDouble(charObj -> ((Character) charObj).getA().x()))
                .toList();

        List<String> lines = new ArrayList<>();
        List<Character> currentLine = new ArrayList<>();
        double totalHeight = characters.stream().mapToDouble(charObj -> charObj.getD().y() - charObj.getA().y()).sum();
        double lineHeightThreshold = 0.1 * totalHeight / characters.size();
        double currentY = characters.get(0).getA().y();

        for (Character character : characters) {
            if (Math.abs(character.getA().y() - currentY) > lineHeightThreshold) {
                String line = processLine(currentLine);
                lines.add(line);
                currentLine.clear();
                currentY = character.getA().y();
            }
            currentLine.add(character);
        }
        String line = processLine(currentLine);
        lines.add(line);

        if (lines.size() < 3) {
            throw new IllegalArgumentException("Document doesn't contain enough lines for a valid address.");
        }

        String recipient = lines.get(0);

        String[] streetHouseNumber = lines.get(1).split(" ");
        if (streetHouseNumber.length < 1) {
            throw new IllegalArgumentException("Document doesn't contain a valid street name and house number.");
        }
        String street = String.join(" ", Arrays.copyOf(streetHouseNumber, streetHouseNumber.length - 1));
        String houseNumber = streetHouseNumber[streetHouseNumber.length - 1];

        String zipCityText = lines.get(2);

        // Corrected part: Split zipCityText into zipCode and city
        String[] zipCity = zipCityText.split(" ", 2);
        if (zipCity.length < 2) {
            throw new IllegalArgumentException("Document doesn't contain a valid ZIP code and city for ZIP code: " + zipCityText);
        }
        String zipCode = zipCity[0];
        String city = zipCity[1];

        if (street.isBlank() || houseNumber == null || houseNumber.isBlank()) {
            System.out.println("Failed to extract address from the following characters:");
            for (Character character : document.getCharacters()) {
                System.out.println(character.toString());
            }
            throw new IllegalArgumentException("Document doesn't contain a valid street name and house number.");
        }

        return new Address(recipient, street, houseNumber, zipCode, city);
    }

    private static String processLine(List<Character> lineCharacters) {
        StringBuilder processedLine = new StringBuilder();

        if (!lineCharacters.isEmpty()) {
            double previousEndX = lineCharacters.get(0).getD().x();

            for (Character character : lineCharacters) {
                double startX = character.getA().x();
                double endX = character.getD().x();
                double characterWidth = endX - startX;

                if (startX - previousEndX > characterWidth) {
                    // Add a space if the gap between characters is larger than a character's width
                    processedLine.append(' ');
                }

                processedLine.append(character.getCharacter());
                previousEndX = endX;
            }
        }

        return processedLine.toString();
    }
}