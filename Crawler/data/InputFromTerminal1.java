21
https://raw.githubusercontent.com/Suranchiyev/java-sdet-2020/master/src/day5/InputFromTerminal1.java
package day5;

public class InputFromTerminal1 {
	public static void main(String[] args) {
		String strNumOne = args[0]; 
		String strNumTwo = args[1];
 //                                 5.6  <- "5.6"
		double numberOne = Double.parseDouble(strNumOne); 
		double numberTwo = Double.parseDouble(strNumTwo);
		double result = numberOne - numberTwo;

		System.out.println(numberOne + " - " + numberTwo + " = " + result);

	}
}