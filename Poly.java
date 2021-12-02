import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class to represent a polynomial, e.g. 3.5x^4 + 3x^2 - 4.
 * 
 * Polynomials can be added, subtracted, multiplied, and divided.
 * 
 * This class is a skeleton. You need to provide implementations
 * for the methods here defined. Feel free however, to add additional
 * methods as you see fit.
 *
 * @author Riley Olds
 * @version 1.0
 */
public class Poly{

    public ArrayList<Monomial> polyList;
    Monomial first;

    /**
     * Empty constructor.
     */
    public Poly() {
        this.polyList = new ArrayList<Monomial>();
    }

    /**
     * Constructor for a polynomial as a list of Monomial terms
     * @param polyList the polynomial passed in as a list
     */
    public Poly(ArrayList<Monomial> polyList) {
        this.polyList = polyList;
    }

    /**
     * Creates a new polynomial containing a single term with the coefficient
     * and exponent passed in as arguments. E.g. when called with coefficient
     * 3.5 and exponent 2, it should create a polynomial 3.5x^2.
     * 
     * You can create additional constructors if you'd like, but it's 
     * imperative that this one exists.
     * 
     * @param coef the single term's coefficient.
     * @param exp the single term's exponent.
     * @return the polynomial created.
     */
    public Poly(double coef, int exp){
        polyList = new ArrayList<Monomial>();
        first = new Monomial(coef, exp);
        polyList.add(first);
    }

    /**
     * Returns the highest exponent found in the given polynomial
     * @param p the polynomial
     * @return the value of the highest exponent
     */
    private static int getHExp(Poly p) {
        int exp = 0;
        for(Monomial mon : p.polyList) {
            if(mon.exp > exp)
                exp = mon.exp;
        }
        return exp;
    }

    /**
     * Returns the lowest exponent found in the given polynomial
     * @param p the polynomial
     * @return the value of the lowest exponent
     */
    private static int getLExp(Poly p) {
        // make sure list has terms, if not just return 0
        if(p.polyList.size() == 0) return 0;
        // set default to the exponent of the first term
        int exp = p.polyList.get(0).exp;
        // if we find an exponent lower than the saved one, we lower to it
        for(Monomial mon : p.polyList) {
            if(mon.exp < exp)
                exp = mon.exp;
        }
        // return lowest exponent
        return exp;
    }

    /**
     * Condenses the terms in the polynomial, getting rid of all 0 coefficients
     * @param p the polynomial being condensed
     * @return the condensed polynomial
     */
    public static Poly condenseTerms(Poly p) {
            
        ArrayList<Monomial> condensed = new ArrayList<Monomial>();

        int h = getHExp(p);
        int l = getLExp(p);
        while(h >= l) {
            double nCoef = 0;
            for(Monomial mon : p.polyList) {
                if(mon.exp == h)
                    nCoef += mon.coef;
            }
            if(nCoef != 0) {
                condensed.add(new Monomial(nCoef, h));
            }
            h--;
        }
        Poly condensedPoly = new Poly(condensed);
        return condensedPoly;
    }
    
    /**
     * Makes sure the polynomial has a term at each power at and bellow the highest one present
     * @param p the polynomial being filled
     * @return the filled polynomial
     */
    public static Poly fillPoly(Poly p) {
        ArrayList<Monomial> filled = new ArrayList<Monomial>();
        
        int h = getHExp(p);
        int highest = h;
        
        while(h >= 0) {
            double nCoef = 0;
            int nExp = 0;
            for(Monomial mon : p.polyList) {
                if(mon.exp == highest) {
                    nCoef = mon.coef;
                    nExp = mon.exp;
                    highest--;
                    break;
                } else if(mon.exp == 0 && h == 0) {
                    nCoef = mon.coef;
                    nExp = mon.exp;
                    break;
                } else if(h != 0 && mon.exp <= h){
                    nCoef = 0;
                    nExp = h;
                    highest--;
                    break;
                }
            }   
            filled.add(new Monomial(nCoef, nExp));
            h--;
        }
        Poly filledPoly = new Poly(filled);
        return filledPoly;
    }

    /**
     * Adds the polynomial passed in as an argument, p, to the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this + p".
     * 
     * @param p the polynomial to add onto the polynomial on which the method is called on.
     * @return a polynomial representing the result of the addition.
     */
    public Poly add(Poly p){
        // array list to hold new poly
        ArrayList<Monomial> added = new ArrayList<Monomial>();

        // figure out the highest and lowest exponents in the two polynomials
        int highest;
        int lowest;
        int pH = getHExp(p);
        int tH = getHExp(this);
        int pL = getLExp(p);
        int tL = getLExp(this);
        if(pH < tH)
            highest = tH;
        else highest = pH;
        if(pL < tL)
            lowest = pL;
        else lowest = tL;

        // loop through and add them
        while(highest >= lowest) {
            double nCoef = 0;
            // find any monomials with highest coefficient in 'this' polynomial
            for(Monomial mon : this.polyList) {
                if(mon.exp == highest)
                    nCoef += mon.coef;
            }
            // find any monomials with highest coefficient in p
            for(Monomial mon : p.polyList) {
                if(mon.exp == highest)
                    nCoef += mon.coef;
            }
            // add new monomial to new poly
            if(nCoef != 0) {
                added.add(new Monomial(nCoef, highest));
            }
            highest--;
        }
        if(added.size() == 0)
            added.add(new Monomial(0, 0));
        Poly addedPoly = new Poly(added);
        return addedPoly;
    }

    /**
     * Subtracts the polynomial passed in as an argument, p, from the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this - p".
     * 
     * @param p the polynomial to be subtracted from the polynomial on which the method is called on.
     * @return a polynomial representing the result of the subtraction.
     */
    public Poly subtract(Poly p){
        // array list for flipped signs
        ArrayList<Monomial> neg = new ArrayList<Monomial>();

        // make every monomial in p flip signs
        double negCoef;
        Monomial negMon;
        for(Monomial mon : p.polyList) {
            negCoef = mon.coef * -1;
            negMon = new Monomial(negCoef, mon.exp);
            neg.add(negMon);
        }
        Poly negPoly = new Poly(neg);
        Poly subed = this.add(negPoly);
        return subed;
    }

    /**
     * Multiplies the polynomial passed in as an argument, p, by the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this * p".
     * 
     * @param p the polynomial to be multiplied by the polynomial on which the method is called on.
     * @return a polynomial representing the result of the multiplication.
     */
    public Poly multiply(Poly p){
        // list to hold new monomials
        ArrayList<Monomial> multPoly = new ArrayList<Monomial>();

        double mCoef;
        int mExp;
        Monomial multMon;
        for(Monomial tMon : this.polyList) {
            for(Monomial pMon : p.polyList) {
                mCoef = tMon.coef * pMon.coef;
                mExp = tMon.exp + pMon.exp;
                multMon = new Monomial(mCoef, mExp);
                multPoly.add(multMon);
            }
        }
        Poly mult = new Poly(multPoly);
        Poly multC = condenseTerms(mult);
        if(multC.polyList.size() == 0)
            multC.polyList.add(new Monomial(0, 0));
        //Collections.sort(multC.polyList, Collections.reverseOrder());
        return multC;
    }

    /**
     * Divides the polynomial on which the method is called on (the "this" polynomial), by
     * the polynomial passed in as an argument, p, and returns a new polynomial
     * with the resulting quotient. I.e., it returns "this / p".
     * 
     * The division should be performed according to the polynomial long division algorithm
     * ( https://en.wikipedia.org/wiki/Polynomial_long_division ).
     * 
     * Polynomial division may end with a non-zero remainder, which means the polynomials are
     * indivisible. In this case the method should return null. A division by zero should also
     * yield a null return value.
     * 
     * @param p the polynomial to be multiplied by the polynomial on which the method is called on.
     * @return a polynomial representing the quotient of the division, or null if they're indivisible.
     */    
    public Poly divide(Poly p){
        if(this.polyList.get(0).coef == 0)
            return this;
        if(p.polyList.get(0).coef == 0)
            return null;
        if(p.polyList.get(0).exp == 0)
            return this;

        // fill out all exponent terms
        Poly dividen = fillPoly(this);
        Poly constantDividen = dividen;
        Monomial dividenM;
        
        ArrayList<Monomial> newPolyList = new ArrayList<Monomial>();
        Poly newPoly = new Poly();
        Monomial divisorTerm = p.polyList.get(0);
        double dCoef;
        int dExp;
        Monomial newTerm;
        
        Poly mDivisor;
        Poly subTerm;
        
        
        for(Monomial dTerm : constantDividen.polyList) {
            if(dTerm.coef == 0 && dTerm.exp != 0) {
                newPolyList.add(dTerm);
                continue;
            }
            dividenM = dividen.polyList.get(0);
            dCoef = dividenM.coef / divisorTerm.coef;
            dExp = dividenM.exp - divisorTerm.exp;
            newTerm = new Monomial(dCoef, dExp);
            newPolyList.add(newTerm);
            newPoly.polyList = newPolyList;
            mDivisor = p.multiply(new Poly(newTerm.coef, newTerm.exp));
            subTerm = dividen.subtract(mDivisor);
            if(subTerm.polyList.get(0).exp == 0) {
                if(subTerm.polyList.get(0).coef != 0)
                    return null;
                else break;
            }
            dividen = subTerm;
        }
        
        Poly divided = condenseTerms(newPoly);
        Collections.sort(divided.polyList, Collections.reverseOrder());

        return divided;
    }

    /**
     * Compares the polynomial on which the method is called (the "this" polynomial), 
     * to the object passed in as argument, o. o is to be considered equal to the "this"
     * polynomial if they both represent equivalent polynomials.
     * 
     * E.g., "3.0x^4 + 0.0x^2 + 5.0" and "3.0x^4 + 5.0" should be considered equivalent.
     * "3.0x^4 + 5.0" and "3.0x^4 + 3.0" should not.
     * 
     * @param o the object to be compared against the polynomial the method is called on.
     * @return true if o is a polynomial equivalent to the one the method was called on,
     * and false otherwise.
     */
    public boolean equals(Object o){
        // make sure o is a poly object
        if(!(o instanceof Poly))
            return false;

        // cast o into a poly object
        Poly p = (Poly) o;
        
        // sort the two lists
        Collections.sort(this.polyList, Collections.reverseOrder());
        Collections.sort(p.polyList, Collections.reverseOrder());

        // if they aren't the same size they can't be equal
        if(this.polyList.size() != p.polyList.size())
            return false;

        // iterate through and check that each list has the same objects at the same index
        Monomial tM;
        Monomial pM;
        for(int i = 0; i < this.polyList.size(); i++) {
            tM = this.polyList.get(i);
            pM = p.polyList.get(i);
            if(tM.coef == 0 && pM.coef == 0)
                continue;
            if(tM.coef != pM.coef || tM.exp != pM.exp)
                return false;
        }
        return true;
    }

    /**
     * Returns a textual representation of the polynomial the method is called on.
     * The textual representation should be a sum of monomials, with each monomial 
     * being defined by a double coefficient, the letters "x^", and an integer exponent.
     * Exceptions to this rule: coefficients of 1.0 should be omitted, as should "^1",
     * and "x^0".
     * 
     * Terms should be listed in decreasing-exponent order. Terms with zero-coefficient
     * should be omitted. Each exponent can only appear once. 
     * 
     * Rules for separating terms, applicable to all terms other that the largest exponent one:
     *   - Terms with positive coefficients should be preceeded by " + ".
     *   - Terms with negative coefficients should be preceeded by " - ".
     * 
     * Rules for the highest exponent term (i.e., the first):
     *   - If the coefficient is negative it should be preceeded by "-". E.g. "-3.0x^5".
     *   - If the coefficient is positive it should not preceeded by anything. E.g. "3.0x^5".
     * 
     * The zero/empty polynomial should be represented as "0.0".
     * 
     * Examples of valid representations: 
     *   - "2.0x^2 + 3.0"
     *   - "3.5x + 3.0"
     *   - "4.0"
     *   - "-2.0x"
     *   - "4.0x - 3.0"
     *   - "0.0"
     *   
     * Examples of invalid representations: 
     *   - "+2.0x^2+3.0x^0"
     *   - "3.5x -3.0"
     *   - "- 4.0 + x"
     *   - "-4.0 + x^7"
     *   - ""
     * 
     * @return a textual representation of the polynomial the method was called on.
     */
    public String toString(){
        String s = "";
        
        // sort and condense the poly
        Collections.sort(this.polyList, Collections.reverseOrder());
        condenseTerms(this);
        ArrayList<Monomial> list = this.polyList;
        // check if it's an empty poly
        if(list.size() == 0)
            return "0.0";
            
        // add the first monomial
        Monomial first = list.get(0);
        double posFirst = Math.abs(first.coef);
        if(first.coef < 0) {
            s += "-";
        }
        if(first.exp == 0) {
            s += posFirst;
            return s;
        }
        if(posFirst != 1)
            s += posFirst;
        if(first.exp != 0)
            s += "x";
        if(first.exp != 1 && first.exp != 0)
            s += "^" + first.exp;

        // add the rest of the poly
        Monomial mon;
        double posMon;
        for(int i = 1; i < list.size(); i++) {
            mon = list.get(i);
            s += " ";
            posMon = Math.abs(mon.coef);
            if(mon.coef < 0) 
                s += "- ";
            else s += "+ ";
            if(mon.exp == 0 || mon.coef != 1)
                s += posMon;
            if(mon.exp != 0)
                s += "x";  
            if(mon.exp != 1 && mon.exp != 0)
                s += "^" + mon.exp;

        }
        return s;
    }

}
