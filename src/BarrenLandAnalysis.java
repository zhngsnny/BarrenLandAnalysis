import java.util.Scanner;

/**
 * BarrenLandAnalysis class provides a CLI for interacting with FarmLand's evaluateFertileArea.
 * Main() upon compilation generates the interface immediately. Otherwise, it can be invoked with
 * call to BarrenLandAnalysis.stdInAnalysis().
 *
 * @author  Sunny Zheng
 * @since   2020-03-31
 */
public class BarrenLandAnalysis {

    /**
     * Generates CLI for interacting with FarmLand. Per case study requirements, instantiates FarmLand of size
     * 400m x 600m. Includes validation of STDIN and prints results to STDOUT.
     */
    public static void stdInAnalysis() {
        try {
            Scanner scanner = new Scanner(System.in);
            String instructionString = "==== Welcome to Barren Land Analysis ====";
            instructionString += "\nRectangles are defined in a string, which consists of four integers separated ";
            instructionString += "\n\tby single spaces, with no additional spaces in the string. The first two integers ";
            instructionString += "\n\tare the coordinates of the bottom left corner in the given rectangle, and the last ";
            instructionString += "\n\ttwo integers are the coordinates of the top right corner.";
            instructionString += "\nExample: {\"48 192 351 207\", \"48 392 351 407\", \"120 52 135 547\", \"260 52 275 547\"}";
            instructionString += "\nProvide input on new line or \"Exit\" to leave program.";
            System.out.println(instructionString);

            FarmLand farmLand = new FarmLand();
            while (scanner.hasNextLine()) {
                String inputString = scanner.nextLine();
                if (inputString.equalsIgnoreCase("Exit")) {
                    break;
                } else {
                    System.out.println(farmLand.evaluateFertileArea(inputString));
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage() + " - " + e.fillInStackTrace());
        } finally {
            System.out.println("========== Analysis terminated ==========");
        }
    }

    /**
     * Main method that invokes stdInAnalysis() upon compilation.
     * @param   args Unused.
     */
    public static void main(String[] args) {
        stdInAnalysis();
    }
}
