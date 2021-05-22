package hr.fer.zemris.mat.demo;

import hr.fer.zemris.mat.Complex;
import hr.fer.zemris.mat.ComplexPolynomial;
import hr.fer.zemris.mat.ComplexRootedPolynomial;

public class ComplexDemo {
	public static void main(String[] args) {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(1, 0), new Complex(1, 0), new Complex(-1, 0), new Complex(0, 1), new Complex(0, -1));
		ComplexPolynomial cp = crp.toComplexPolynom(); 
		System.out.println(crp); 
		System.out.println(cp); 
		System.out.println(cp.derive());
		System.out.println(crp.indexOfClosestRootFor(new Complex(-1, 0.001), 0.002));
	}
}
