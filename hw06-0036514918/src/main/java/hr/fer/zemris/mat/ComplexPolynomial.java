package hr.fer.zemris.mat;

import java.util.Arrays;

/**
 * The Class ComplexPolynomial.
 */
public class ComplexPolynomial {
	
	/** The factors. */
	private Complex[] factors;
	
	/**
	 * Instantiates a new complex polynomial.
	 *
	 * @param factors the factors
	 */
	public ComplexPolynomial(Complex ... factors) {
		this.factors = factors;
	}
	
	/**
	 * Returns the order of the polynomial.
	 *
	 * @return the order
	 */
	// returns order of this polynom; e.g. for (7+2i)z^3+2z^2+5y+1 returns 3
	public short order() {
		return (short) (factors.length - 1);
	}
	
	/**
	 * Multiplies the <code>ComplexPolynomial</code> with the given <code>ComplexPolynomial</code>.
	 * Returns the result as a new instance of <code>ComplexPolynomial</code>.
	 *
	 * @param p the polynomial
	 * @return the resulting complex polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[this.order() + p.order() + 1];
		Arrays.fill(newFactors, Complex.ZERO);
		
		for (int i = 0; i <= this.order(); i++) {
			for (int j = 0; j <= p.order(); j++) {
				newFactors[i + j] = newFactors[i+j].add(this.factors[i].mul(p.factors[j]));
			}
		}

		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * Computes the first derivative of this polynomial.
	 *
	 * @return the complex polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[factors.length - 1];
		for (int i = 1; i < factors.length; i++) {
			newFactors[i - 1] = factors[i].mul(new Complex(i, 0));
		}
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * Computes polynomial value at given point z
	 *
	 * @param z the complex number z
	 * @return the result
	 */
	//computes polynomial value at given point z
	public Complex apply(Complex z) {
		Complex result = new Complex(0, 0);
		for (int i = 0; i < factors.length; i++) {
			result = result.add(z.power(i).mul(factors[i]));
		}
		return result;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = factors.length - 1; i >= 0; i--) {
			sb.append("(" + factors[i] + ")");
			if (i != 0) sb.append("*z^" + i + "+");
		}
		return sb.toString();
	}
}
