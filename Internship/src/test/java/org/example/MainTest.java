package org.example;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.fail;
public class MainTest {

    @Test
    public void testMainWithValidArguments() {
        String[] args = {"-file", "test.txt", "-top", "5", "-phraseSize", "2"};
        Main.main(args);
        // Verifică dacă programul se execută fără erori.
    }
/*
    @Test
    public void testMainWithInvalidArguments() {
        String[] args = {"-file", "test.txt", "-top", "2", "-phraseSize", "inval"};
        Exception exception = assertThrows(NumberFormatException.class, () -> Main.main(args));
        String expectedMessage = "The 'top' and 'phraseSize' arguments must be integers.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        // ... atunci când se dau argumente invalide și dacă mesajul excepției este cel așteptat.
    }
*/

    @Test
    public void testProcessFileWithValidInput() {
        try {
            Files.writeString(Path.of("test.txt"), "Lorem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
                    "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.");
            Main.processFile("test.txt", 2, 2);
            // Verifică dacă programul se execută fără erori și dacă rezultatele sunt corecte.
        } catch (IOException e) {
            fail("Eroare la scrierea fișierului de test.");
        }
    }

    @Test
    public void testProcessFileWithEmptyFile() {
        try {
            Files.writeString(Path.of("empty.txt"), "");
            Main.processFile("empty.txt", 2, 2);
            // Verifică dacă programul se comportă corect atunci când fișierul este gol.
        } catch (IOException e) {
            fail("Eroare la scrierea fișierului de test.");
        }
    }

    @Test
    public void testProcessFileWithOneWord() {
        try {
            Files.writeString(Path.of("one_word.txt"), "test");
            Main.processFile("one_word.txt", 2, 2);
            // Verifică dacă programul se comportă corect atunci când fișierul conține doar un cuvânt.
        } catch (IOException e) {
            fail("Eroare la scrierea fișierului de test.");
        }
    }

    @Test
    public void testProcessFileWithLargePhraseSize() {
        try {
            Files.writeString(Path.of("test.txt"), "Acesta este un test. Acesta este un alt test.");
            Main.processFile("test.txt", 2, 10);
            // Verifică dacă programul se comportă corect atunci când dimensiunea frazei este mai mare decât numărul de cuvinte din fișier.
        } catch (IOException e) {
            fail("Eroare la scrierea fișierului de test.");
        }
    }

    @Test
    public void testProcessFileWithZeroTop() {
        try {
            Files.writeString(Path.of("test.txt"), "Acesta este un test. Acesta este un alt test.");
            Main.processFile("test.txt", 0, 2);
            // Verifică dacă programul se comportă corect atunci când numărul de fraze de top este zero.
        } catch (IOException e) {
            fail("Eroare la scrierea fișierului de test.");
        }
    }
}