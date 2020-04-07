import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BarrenLandAnalysisTest {

    private String introString = "==== Welcome to Barren Land Analysis ====" +
            "\nRectangles are defined in a string, which consists of four integers separated " +
            "\n\tby single spaces, with no additional spaces in the string. The first two integers " +
            "\n\tare the coordinates of the bottom left corner in the given rectangle, and the last " +
            "\n\ttwo integers are the coordinates of the top right corner." +
            "\nExample: {\"48 192 351 207\", \"48 392 351 407\", \"120 52 135 547\", \"260 52 275 547\"}" +
            "\nProvide input on new line or \"Exit\" to leave program.";
    private String terminateString = "\n========== Analysis terminated ==========\n";

    @Test
    @DisplayName("System test: stdInAnalysis with STDIN {}")
    public void testStdInInput() {
        System.setIn(new ByteArrayInputStream("{}".getBytes()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        BarrenLandAnalysis.stdInAnalysis();

        String expectedOutput = introString + "\n240000" + terminateString;
        Assert.assertEquals(expectedOutput, baos.toString());
    }

    @Test
    @DisplayName("System test: stdInAnalysis with STDIN exit")
    public void testStdInExit() {
        System.setIn(new ByteArrayInputStream("Exit".getBytes()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        BarrenLandAnalysis.stdInAnalysis();

        String expectedOutput = introString + terminateString;
        Assert.assertEquals(expectedOutput, baos.toString());
    }

    @Test
    @DisplayName("System test: compile main with STDIN {}")
    public void testMain() {
        System.setIn(new ByteArrayInputStream("Exit".getBytes()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        BarrenLandAnalysis.main(new String[0]);

        String expectedOutput = introString + terminateString;
        Assert.assertEquals(expectedOutput, baos.toString());
    }

}
