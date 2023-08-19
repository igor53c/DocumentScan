import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentScanJavaTest {

    @Test
    public void testValidDocumentExtraction_Scenario1() {
        ArrayList<Character> characters = new ArrayList<>(Arrays.asList(
                new Character('M', new Point(10, 10), new Point(20, 10), new Point(10, 20), new Point(20, 20)),
                new Character('a', new Point(21, 10), new Point(31, 10), new Point(21, 20), new Point(31, 20)),
                new Character('M', new Point(43, 10), new Point(53, 10), new Point(43, 20), new Point(53, 20)),
                new Character('ü', new Point(54, 10), new Point(64, 10), new Point(54, 20), new Point(64, 20)),

                new Character('K', new Point(10, 21), new Point(20, 21), new Point(10, 31), new Point(20, 31)),
                new Character('a', new Point(21, 21), new Point(31, 21), new Point(21, 31), new Point(31, 31)),
                new Character('4', new Point(43, 21), new Point(53, 21), new Point(43, 31), new Point(53, 31)),
                new Character('5', new Point(54, 21), new Point(64, 21), new Point(54, 31), new Point(64, 31)),

                new Character('1', new Point(10, 32), new Point(20, 32), new Point(10, 42), new Point(20, 42)),
                new Character('0', new Point(21, 32), new Point(31, 32), new Point(21, 42), new Point(31, 42)),
                new Character('B', new Point(43, 32), new Point(53, 32), new Point(43, 42), new Point(53, 42)),
                new Character('e', new Point(54, 32), new Point(64, 32), new Point(54, 42), new Point(64, 42)),

                new Character('D', new Point(43, 43), new Point(53, 43), new Point(43, 53), new Point(53, 53)),
                new Character('e', new Point(54, 43), new Point(64, 43), new Point(54, 53), new Point(64, 53))
        ));

        Document document = new Document(characters);

        Address expectedAddress = new Address("Ma Mü", "Ka", "45", "10", "Be");
        Address extractedAddress = DocumentScanJava.findAddress(document);

        assertEquals(expectedAddress, extractedAddress);
    }
}
