import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class FarmLandTest {

    private FarmLand farmLand;

    @Before
    public void testSetup() {
        farmLand = new FarmLand(10, 10);
    }

    @Test
    @DisplayName("Unit test: evaluateFertileArea of no barren rectangles")
    public void testNoBarrenPositive() {
        String returnString = farmLand.evaluateFertileArea("{}");
        Assert.assertEquals("100", returnString);
    }

    @Test
    @DisplayName("Unit test: evaluateFertileArea of all barren farmland for no fertile land")
    public void testAllBarrenPositive() {
        String returnString = farmLand.evaluateFertileArea("{\"0 0 9 9\"}");
        Assert.assertEquals("", returnString);
    }

    @Test
    @DisplayName("Unit test: evaluateFertileArea of single meter square of barren land")
    public void testSingleBarrenPositive() {
        String returnString = farmLand.evaluateFertileArea("{\"1 1 1 1\"}");
        Assert.assertEquals("99", returnString);
    }

    @Test
    @DisplayName("Unit test: evaluateFertileArea of a row of barren land")
    public void testBarrenRowPositive() {
        String returnString = farmLand.evaluateFertileArea("{\"0 1 9 1\"}");
        Assert.assertEquals("10 80", returnString);
    }

    @Test
    @DisplayName("Unit test: evaluateFertileArea of a column of barren land")
    public void testBarrenColPositive() {
        String returnString = farmLand.evaluateFertileArea("{\"1 1 1 9\"}");
        Assert.assertEquals("91", returnString);
    }

    @Test
    @DisplayName("Unit test: evaluateFertileArea with overlapping barren land")
    public void testBarrenOverlapPositive() {
        String returnString = farmLand.evaluateFertileArea("{\"0 0 0 9\", \"0 0 9 0\"}");
        Assert.assertEquals("81", returnString);
    }

    @Test
    @DisplayName("Unit test: evaluateFertileArea of kiddy corner barren land = kiddy corner fertile land")
    public void testKiddyCornerPositive() {
        String returnString = farmLand.evaluateFertileArea("{\"0 0 3 3\", \"4 4 9 9\"}");
        Assert.assertEquals("24 24", returnString);
    }

    @Test
    @DisplayName("Unit test: evaluateFertileArea Target provided test case of {“0 292 399 307”} => 116800 116800")
    public void testTargetCase1Positive() {
        farmLand = new FarmLand();
        String returnString = farmLand.evaluateFertileArea("{\"0 292 399 307\"}");
        Assert.assertEquals("116800 116800", returnString);
    }

    @Test
    @DisplayName("Unit test: evaluateFertileArea Target provided test case of {“48 192 351 207”, “48 392 351 407”, “120 52 135 547”, " +
            "“260 52 275 547”} => 22816 192608. Covers branch of two initially unconnected groups becoming joined.")
    public void testTargetCase2Positive() {
        farmLand = new FarmLand();
        String returnString = farmLand.evaluateFertileArea("{\"48 192 351 207\", \"48 392 351 407\"," +
                " \"120 52 135 547\", \"260 52 275 547\"}");
        Assert.assertEquals("22816 192608", returnString);
    }

    @Test
    @DisplayName("Unit test: evaluateFertileArea with no brackets")
    public void testNoBracketNegative() {
        String returnString = farmLand.evaluateFertileArea("\"1 2 3 4\"");
        Assert.assertEquals("Invalid input: Must be contained within brackets { ... }.", returnString);
    }

    @Test
    @DisplayName("Unit test: evaluateFertileArea with null rectangle")
    public void testNullRectangleNegative() {
        String returnString = farmLand.evaluateFertileArea("{\"1 2 3 4\",}");
        Assert.assertEquals("Invalid input: Cannot have null rectangle input.", returnString);
    }

    @Test
    @DisplayName("Unit test: evaluateFertileArea of rectangles with no quotes")
    public void testInvalidMatchingQuotesNegative() {
        String returnString = farmLand.evaluateFertileArea("{1 2 3 4}");
        Assert.assertEquals("Invalid input: Rectangle(s) do not match required format.", returnString);
    }

    @Test
    @DisplayName("Unit test: evaluateFertileArea of rectangles with only three integers")
    public void testInvalidMatchingIntegerNegative() {
        String returnString = farmLand.evaluateFertileArea("{\"1 2 3\"}");
        Assert.assertEquals("Invalid input: Rectangle(s) do not match required format.", returnString);
    }

    @Test
    @DisplayName("Unit test: evaluateFertileArea of rectangles with non-integer values")
    public void testInvalidMatchingCharNegative() {
        String returnString = farmLand.evaluateFertileArea("{\"inv alid inp ut\"}");
        Assert.assertEquals("Invalid input: Rectangle(s) do not match required format.", returnString);
    }

    @Test
    @DisplayName("Unit test: evaluateFertileArea of rectangles with top-right coordinate to the left of bottom-left coordinate")
    public void testInvalidRectangleNegative() {
        String returnString = farmLand.evaluateFertileArea("{\"1 9 0 9\"}");
        Assert.assertEquals("Invalid input: Incompatible bottom-left + top-right rectangle coordinates.", returnString);
    }

    @Test
    @DisplayName("Unit test: evaluateFertileArea of rectangles with coordinates outside of farm land coordinates")
    public void testOutsideFarmLandNegative() {
        String returnString = farmLand.evaluateFertileArea("{\"0 9 1 10\"}");
        Assert.assertEquals("Invalid input: Rectangles lie outside dimensions 10m x 10m.", returnString);
    }

    @Test(expected = IllegalArgumentException.class)
    @DisplayName("Unit test: FarmLand constructor of length = 0")
    public void testInvalidLengthNegative() {
        farmLand = new FarmLand(0, 9);
    }

    @Test(expected = IllegalArgumentException.class)
    @DisplayName("Unit test: FarmLand constructor of width = 0")
    public void testInvalidWidthNegative() {
        farmLand = new FarmLand(9, 0);
    }
}
