import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * FarmLand class is used to describe farm land of LENGTH x WIDTH. FarmLand size is specified with two
 * available constructors, and a count of fertile land area in increasing size can be determined with method
 * evaluateFertileArea().
 *
 * @author  Sunny Zheng
 * @since   2020-03-31
 */
public class FarmLand {

    private final int WIDTH, LENGTH;
    private LandCell[][] landMatrix;
    private ArrayList<RectangleInput> rectangleInputList = new ArrayList<RectangleInput>();
    private String evaluationString;

    /**
     * Constructor: Defaults LENGTH to 400 and WIDTH to 600.
     */
    public FarmLand() {
        this.WIDTH = 600;
        this.LENGTH = 400;
        this.landMatrix = initMatrix();
    }

    /**
     * Constructor: Allows custom FarmLand size.
     * @param   LENGTH Custom int length of farm land.
     * @param   WIDTH Custom int width of farm land.
     */
    public FarmLand(int LENGTH, int WIDTH) {
        if (LENGTH < 1) {
            throw new IllegalArgumentException("LENGTH cannot be < 1.");
        } else {
            this.LENGTH = LENGTH;
        }
        if (WIDTH < 1) {
            throw new IllegalArgumentException("WIDTH cannot be < 0.");
        } else {
            this.WIDTH = WIDTH;
        }
        this.landMatrix = initMatrix();
    }

    /**
     * LandCell class represents each square meter of farm land (FarmLand). Property isBarren specifies whether square
     * meter contains barren or fertile land. Property fertileLandTag is the Integer key representing the group of
     * fertile area that the LandCell is a part of.
     */
    private class LandCell {
        private Boolean isBarren;
        private Integer fertileLandTag;
        private LandCell() {
            this.isBarren = false;
            this.fertileLandTag = null;
        }
    }

    /**
     * RectangleInput class represents the coordinates describing a barren land rectangle of farm land.
     */
    private class RectangleInput {
        private Coordinate bottomLeft;
        private Coordinate topRight;
        private RectangleInput(Coordinate bottomLeft, Coordinate topRight) {
            this.bottomLeft = bottomLeft;
            this.topRight = topRight;
        }
    }

    /**
     * Coordinate class is a wrapper class for coordinate inputs. Used in RectangleInput.
     */
    private class Coordinate {
        private int x;
        private int y;
        private Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Method receives barren land coordinates for a FarmLand to evaluate fertile land area.
     * @param   inputString String of barren land coordinates.
     * @return  String Fertile land area in square meters, sorted from smallest area to greatest, separated by a space.
     */
    public String evaluateFertileArea(String inputString) {
        try {
            if (validateInput(inputString)) {
                populateBarrenLand();
                countFertileLandArea();
            }
        } catch (Exception e) {
            evaluationString = "Exception: " + e.getMessage() + " - " + e.fillInStackTrace();
        } finally {
            return evaluationString;
        }
    }

    /**
     * Method receives barren land coordinates for a FarmLand and confirms whether coordinate input is valid. If valid,
     * rectangleInputList has been generated from barren land coordinates and method returns true. If invalid,
     * evaluationString is updated with invalid input comment and returns false.
     * @param   inputString String of barren land coordinates.
     * @return  Boolean Whether inputString is of correct formatting and of valid rectangle coordinates.
     */
    private Boolean validateInput(String inputString) {
        Boolean isValid = true;
        if (!Pattern.matches("^\\{.*\\}$", inputString)) {
            isValid = false;
            evaluationString = "Invalid input: Must be contained within brackets { ... }.";
        } else {
            String inputStringStripped = inputString.replaceAll("^\\{|\\}$", "");
            if (!inputStringStripped.isBlank()) {
                long commaCount = inputStringStripped.chars().filter(ch -> ch == ',').count();
                String[] stringList = inputStringStripped.split(",");
                if ((int) commaCount != (stringList.length - 1)) {
                    isValid = false;
                    evaluationString = "Invalid input: Cannot have null rectangle input.";
                } else {
                    rectangleInputList = new ArrayList<RectangleInput>();
                    for (String str : stringList) {
                        if (!Pattern.matches("^\\s*\"\\d+(?:\\s+\\d+){3}(,\\s\"\\d+(?:\\s+\\d+){3})*\"$", str)) {
                            isValid = false;
                            evaluationString = "Invalid input: Rectangle(s) do not match required format.";
                            break;
                        } else if (!buildRectangle(str)) {
                            isValid = false;
                            break;
                        }
                    }
                }
            }
        }
        return isValid;
    }

    /**
     * Method receives a single barren land's coordinates. If valid, adds RectangleInput object to rectangleInputList
     * and returns true. If invalid, updates evaluationString with invalid input comment and returns false.
     * @param   inputRectangle String of a single barren land rectangle's coordinates.
     * @return  Boolean Whether barren land rectangle is of valid coordinates.
     */
    private Boolean buildRectangle(String inputRectangle) {
        Boolean isValid = true;
        String inputStringStripped = inputRectangle.replaceAll("\"", "");
        if (!inputStringStripped.isBlank()) {
            String[] integerList = inputStringStripped.split(",");
            for (String s : integerList) {
                if (!s.isBlank()) {
                    String[] coordStringList = s.trim().split("\\s");
                    int bottomCol = Integer.parseInt(coordStringList[0]);
                    int bottomRow = Integer.parseInt(coordStringList[1]);
                    int topCol = Integer.parseInt(coordStringList[2]);
                    int topRow = Integer.parseInt(coordStringList[3]);

                    Boolean validRectangleLENGTH = bottomCol >= 0 && bottomCol < LENGTH && topCol >= 0 && topCol < LENGTH;
                    Boolean validRectangleWIDTH = bottomRow >= 0 && bottomRow < WIDTH && topRow >= 0 && topRow < WIDTH;
                    if (validRectangleLENGTH && validRectangleWIDTH) {
                        if (topCol >= bottomCol && topRow >= bottomRow) {
                            Coordinate bottomLeft = new Coordinate(bottomCol, bottomRow);
                            Coordinate topRight = new Coordinate(topCol, topRow);
                            rectangleInputList.add(new RectangleInput(bottomLeft, topRight));
                        } else {
                            isValid = false;
                            evaluationString = "Invalid input: Incompatible bottom-left + top-right rectangle coordinates.";
                            break;
                        }
                    } else {
                        isValid = false;
                        evaluationString = "Invalid input: Rectangles lie outside dimensions " + LENGTH + "m x " + WIDTH + "m.";
                        break;
                    }
                }
            }
        }
        return isValid;
    }

    /**
     * Method updates farmLand's LandCells with isBarren flags, when necessary. Dependent on rectangleInputList having
     * been populated.
     * @return  Nothing.
     */
    private void populateBarrenLand() {
        landMatrix = initMatrix();
        for (RectangleInput input : rectangleInputList) {
            for (int i = input.bottomLeft.y; i <= input.topRight.y; i++) {
                for (int j = input.bottomLeft.x; j <= input.topRight.x; j++) {
                    landMatrix[i][j].isBarren = true;
                }
            }
        }
    }

    /**
     * Method iterates through FarmLand to count the areas of fertile land groups. Output is saved to evaluationString and
     * nothing is returned. Dependent on input validation and appropriate barren land population.
     * @return  Nothing.
     */
    private void countFertileLandArea() {
        HashMap<Integer, Integer> tagCountMap = new HashMap<Integer, Integer>();
        Integer leftTag, belowTag, assignmentTag;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                if (!landMatrix[i][j].isBarren) {
                    leftTag = (j - 1 >= 0) ? landMatrix[i][j-1].fertileLandTag : null;
                    belowTag = (i - 1 >= 0) ? landMatrix[i-1][j].fertileLandTag : null;
                    if (leftTag == null && belowTag == null) {
                        // Left & above == null (barren or at [0, 0], not touching known fertile areas. Add to tagCountMap.
                        assignmentTag = tagCountMap.size() + 1;
                        tagCountMap.put(assignmentTag, 1);
                    } else if (leftTag == null || belowTag == null || leftTag == belowTag) {
                        // Touching only one known fertile area. Increment tag value by 1 in tagCountMap.
                        assignmentTag = (belowTag != null) ? belowTag : leftTag;
                        tagCountMap.put(assignmentTag, tagCountMap.get(assignmentTag) + 1);
                    } else {
                        // Touching two fertile areas = can combine areas of each into belowTag key of tagCountMap.
                        assignmentTag = belowTag;
                        tagCountMap.put(assignmentTag, tagCountMap.get(belowTag) + tagCountMap.get(leftTag) + 1);
                        // After updating belowTag key, update landMatrix indices still referencing outdated/leftTag
                        // This is for LandCells in same row and left of [i][j], and those in below row and right of [i][j]
                        for (int jUpdate = 0; jUpdate < LENGTH; jUpdate++) {
                            if (jUpdate < j && landMatrix[i][jUpdate].fertileLandTag == leftTag) {
                                landMatrix[i][jUpdate].fertileLandTag =  assignmentTag;
                            } else if (i-1 >= 0 && landMatrix[i-1][jUpdate].fertileLandTag == leftTag) {
                                landMatrix[i-1][jUpdate].fertileLandTag = assignmentTag;
                            }
                        }
                        tagCountMap.remove(leftTag);
                    }
                    landMatrix[i][j].fertileLandTag = assignmentTag;
                }
            }
        }
        ArrayList<Integer> countSortList = new ArrayList<Integer>(tagCountMap.values());
        Collections.sort(countSortList);
        String str = (Arrays.toString(countSortList.toArray()).replaceAll("^\\[|,|\\]$", ""));
        evaluationString = str;
    }

    /**
     * Method is used in FarmLand constructors and before invocation of evaluateFertileArea() to clear landMatrix of its
     * isBarren and fertileLandTag values.
     * @return  LandCell[][] Updated matrix with new LandCell values.
     */
    private LandCell[][] initMatrix() {
        LandCell[][] initMatrix = new LandCell[WIDTH][LENGTH];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                initMatrix[i][j] = new LandCell();
            }
        }
        return initMatrix;
    }
}
