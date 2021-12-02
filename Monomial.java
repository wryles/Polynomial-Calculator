
/**
 * Class to represent a single monomial.
 *
 * @author Riley Olds
 * @version 1.0
 */
public class Monomial implements Comparable<Monomial>{
    
    double coef;
    int exp;
    
    /**
     * Constructor for Monomial objects with the desired coefficient and exponent.
     * 
     * @param coef the coefficient
     * @param exp the exponent
     * @return the Monomial 
     */
    public Monomial(double coef, int exp) {
        this.coef = coef;
        this.exp = exp;
    }

    public int compareTo(Monomial other) {
        Integer exp = this.exp;
        return exp.compareTo(other.exp);
    }
}