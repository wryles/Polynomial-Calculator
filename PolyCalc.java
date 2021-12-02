import java.util.Scanner;
import java.util.ArrayList;

/**
 * Class to run the Polynomial Calculator
 * 
 * @author Riley Olds
 * @version 1.0
 */

public class PolyCalc {

    /**
     * Empty Constructor
     */
    public PolyCalc() {
    }

    /**
     * The main function runs the text based interface for the polynomial calculator.
     * 
     * @param args[] arguments entered when main is ran.
     */
    public static void main(String args[]) {
        System.out.println("Welcome to the Polynomial Calculator by Riley Olds.\nIf at any point you would like to quit, simply enter 'quit.'\n");
        while(true) {
            Scanner scan = new Scanner(System.in);
            Poly one;
            Poly two;

            // get first poly
            while(true) {
                System.out.println("Please enter your first polynomial using space seperated pairs.\nFor example, the polynomial '3x^2 + 5' should be entered as '3 2 5'.\n");
                String polyOne = scan.nextLine();
                one = getPoly(polyOne);
                if(one == null)
                    break;
                if(one.polyList.size() == 0) 
                    continue;
                else if(one != null)
                    System.out.println("\nUser entered the valid polynomial: " + one.toString());
                break;
            }
            if(one == null)
                break;

            // get opperator
            System.out.println("Please enter the opperator of your choice (+, -, *, /).\n");
            String op = "";
            while(true) {
                op = scan.nextLine();
                op = op.toUpperCase();
                if(op.equals("QUIT")) {
                    op = null;
                    break;
                }
                if(validOperator(op))
                    break;
                else {
                    System.out.println("\nInvalid operator. Please enter a new one.");
                    continue;
                }
            }
            if(op == null)
                break;
            else System.out.println("\nUser entered the valid opperator: " + op);

            // get second poly
            while(true) {
                System.out.println("Please enter a second polynomial.\n");
                String polyTwo = scan.nextLine();
                two = getPoly(polyTwo);
                if(two == null)
                    break;
                if(two.polyList.size() == 0) 
                    continue;
                else if(two != null)
                    System.out.println("\nUser entered the valid polynomial: " + two.toString());
                break;
            }
            if(two == null)
                break;

            // execute the opperation and print the result.
            Poly result = execute(one, two, op);
            if(result == null) {
                System.out.println("\nOpperation failed. Resetting calculator.\n");
                continue;
            } else {
                System.out.println("\nThe result of " + one.toString() + " " + op + " " + two.toString() + " = " + result.toString());
                System.out.println("Resetting the calculator.\n");
                continue;
            }
        }
    }

    private static Poly getPoly(String input) {
        String invalidP = "User did not input a valid polynomial.\n";

        ArrayList<Monomial> polyList = new ArrayList<Monomial>();

        while(true) {
            input = input.toUpperCase();
            if(input.equals("QUIT"))
                return null;
            Scanner lineScan = new Scanner(input);
            double coef;
            int exp;

            // get the first polynomial
            while(lineScan.hasNext()) {
                if(lineScan.hasNextDouble()) {
                    coef = lineScan.nextDouble();
                    if(lineScan.hasNextInt()) {
                        exp = lineScan.nextInt();
                        if(exp < 0) {
                            System.out.println("Exponents cannot be negative.\n");
                            polyList = new ArrayList<Monomial>();
                            break;
                        }
                            
                        polyList.add(new Monomial(coef, exp));
                    }
                    else
                        polyList.add(new Monomial(coef, 0));

                }else if(polyList.size() == 0) { 
                    System.out.println(invalidP);
                    break;
                }
            }
            break;
        }
        Poly poly = new Poly(polyList);
        return poly;
    }

    private static boolean validOperator(String op) {
        boolean valid;
        switch (op) {
            case "+":
            valid = true;
            break;
            case "-":
            valid = true;
            break;
            case "*":
            valid = true;
            break;
            case "/":
            valid = true;
            break;
            default:
            valid = false;
        }
        return valid;
    }

    private static Poly execute(Poly one, Poly two, String op) {
        Poly result;
        switch(op) {
            case "+":
            result = one.add(two);
            break;
            case "-":
            result = one.subtract(two);
            break;
            case "*":
            result = one.multiply(two);
            break;
            case "/":
            result = one.divide(two);
            break;
            default:
            result = null;
        }
        return result;
    }
}
