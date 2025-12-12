import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.liamb.Address;
import org.liamb.util.Util;

public class TestUtil {
    @Test
    @DisplayName("\"\" -> \"\"")
    void testToTitleCase() {
        String str = "";
        String expected = "";
        String actual = Util.toTitleCase(str);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("null -> null")
    void testToTitleCase2() {
        String str = null;
        String expected = null;
        String actual = Util.toTitleCase(str);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("\"   \" -> \"   \"")
    void testToTitleCase3() {
        String str = "   ";
        String expected = "   ";
        String actual = Util.toTitleCase(str);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("   social  science-> Social Science")
    void testToTitleCase4() {
        String str = "   social science";
        String expected = "Social Science";
        String actual = Util.toTitleCase(str);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("$ocial 5cience -> $ocial 5cience")
    void testToTitleCase5() {
        String str = "$ocial 5cience";
        String expected = "$ocial 5cience";
        String actual = Util.toTitleCase(str);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("DISCRETE MATH -> Discrete Math")
    void testToTitleCase6() {
        String str = "DISCRETE MATH";
        String expected = "Discrete Math";
        String actual = Util.toTitleCase(str);

        Assertions.assertEquals(expected, actual);
    }
}
