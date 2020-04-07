# Technical Assessment Case Study

Interviewee: Sunny Zheng
Selected Case Study: Barren Land Analysis

## Case Study Overview
Per "Java BE - Redsky Updated 7_05_2017 (3)":

You have a farm of 400m by 600m where coordinates of the field are from (0, 0) to (399, 599). A portion of the farm is barren, and all the barren land is in the form of rectangles. Due to these rectangles of barren land, the remaining area of fertile land is in no particular shape. An area of fertile land is defined as the largest area of land that is not covered by any of the rectangles of barren land. Read input from STDIN. Print output to STDOUT.

Input: You are given a set of rectangles that contain the barren land. These rectangles are defined in a string, which consists of four integers separated by single spaces, with no additional spaces in the string. The first two integers are the coordinates of the bottom left corner in the given rectangle, and the last two integers are the coordinates of the top right corner. 

Output: Output all the fertile land area in square meters, sorted from smallest area to greatest, separated by a space. 

## Usage
Barren Land Analysis can be utilized via command line after compilation of view class `BarrenLandAnalysis`.
```
> javac BarrenLandAnalysis.java 
> java BarrenLandAnalysis
==== Welcome to Barren Land Analysis ====
Rectangles are defined in a string, which consists of four integers separated
	by single spaces, with no additional spaces in the string. The first two integers
	are the coordinates of the bottom left corner in the given rectangle, and the last
	two integers are the coordinates of the top right corner.
Example: {"48 192 351 207", "48 392 351 407", "120 52 135 547", "260 52 275 547"}
Provide input on new line or "Exit" to leave program.
{}
240000
{"48 192 351 207", "48 392 351 407", "120 52 135 547", "260 52 275 547"}
22816 192608
Exit
========== Analysis terminated ==========
```

Additionally, it can be completed without STDIN/STDOUT via instantiation of controller class `FarmLand`. Note that the constructor for FarmLand can take zero arguments for default farm land size of 400m by 600m or `FarmLand(int length, int width)` for a different farm land size.
```
FarmLand farmLandDefault = new FarmLand();
String resultDefault = farmLandDefault.evaluateFertileArea("{}");
Assert.assertEquals("240000", resultDefault);

FarmLand farmLandConstr = new FarmLand(10, 10);
String resultConstr = farmLandConstr.evaluateFertileArea("{}");
Assert.assertEquals("100", resultConstr);
```
## Testing Clarifications
1. If there is no fertile area, the output is empty string `""`. 
   1. Test location: `src/test/FarmLand.java`
   1. Test name: `testAllBarrenPositive`
1. Invalid test cases include but are not limited to the following:
   1. Test case input not surrounded by `{...}`
   1. Rectangle coordinates not surrounded by `"..."`
   1. Top-right coordinate being to the left or below the bottom-left coordinate input
   1. Coordinate indices existing outside the specified farm land size

## Requirements
* Complete the exercise in the technical stack of your choice
   * Development: Java SE Development Kit 13
   * `BarrenLandAnalysis/src`: `BarrenLandAnalysis.java`, `FarmLand.java`
* Provide evidence of the result to the interviewers
   * Testing: JUnit 5.4
   * `BarrenLandAnalysis/test`: `BarrenLandAnalysisTest.java`, `FarmLandTest.java`

## Conclusion
Thank you for taking the time to review my approach! I look forward to hearing any input and advice others may have.

Sunny Zheng
[zhng.snny@gmail.com](mailto:zhng.snny@gmail.com)
[LinkedIn Profile](https://www.linkedin.com/in/zhng-snny)


