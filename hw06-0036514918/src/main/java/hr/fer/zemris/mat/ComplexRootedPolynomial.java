package hr.fer.zemris.mat;

/**
 * The Class ComplexRootedPolynomial with form: constant * (z - root1) * (z - root2) *...* (z - rootN).
 */
public class ComplexRootedPolynomial {
	
	/** The constant. */
	private Complex constant;

	/** The roots. */
	private Complex[] roots;
	
	/**
	 * Instantiates a new complex rooted polynomial.
	 * The form is: constant * (z - root1) * (z - root2) *...* (z - rootN)
	 *
	 * @param constant the constant
	 * @param roots the roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = roots;
	}
	
	/**
	 * Computes polynomial value at given point z.
	 *
	 * @param z the point
	 * @return the value as a new instance of <code>Complex</code>
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
		for (Complex c : roots) {
			Complex arg = z.sub(c);
			result = result.mul(arg);
		}
		return result;
	}
	
	/**
	 * Converts the polynomial from this representation to type <code>ComplexPolynomial</code>.
	 *
	 * @return the complex polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(constant);
		
		for (int i = 0; i < roots.length; i++) {
			ComplexPolynomial nextPol = new ComplexPolynomial(roots[i].negate(), new Complex(1, 0));
			result = result.multiply(nextPol);
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
		sb.append("(" + constant + ")");
		for(Complex c : roots) {
			sb.append("*(z-(" + c + "))");
		}
		
		return sb.toString();
	}
	
	/**
	 * Finds the index of closest root to given complex number z that is within treshold.
	 * If there is no such root, returns -1.
	 *
	 * @param z the complex number z
	 * @param treshold the treshold
	 * @return the index if it exists, -1 otherwise.
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		double minDistance = Double.MAX_VALUE;
		int indexOfMin = -1;
		for(int i = 0; i < roots.length; i++) {
			double distance = z.distanceFrom(roots[i]);
			if (distance < minDistance && distance < treshold) {
				minDistance = distance;
				indexOfMin = i;
			}
		}
		
		return indexOfMin;
	}
	
}
