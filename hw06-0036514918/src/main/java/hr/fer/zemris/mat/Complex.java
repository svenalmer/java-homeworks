package hr.fer.zemris.mat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Complex {
	
	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);
	
	private double re;
	private double im;
	
	/**
	 * Instantiates a new complex number with real and imaginary values of 0.
	 */
	public Complex() {
		this.re = 0;
		this.im = 0;
	}
	
	
	/**
	 * Instantiates a new complex number with the given real and imaginary value.
	 *
	 * @param re the real value
	 * @param im the imaginary value
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Calculates the module of the <code>Complex</code>
	 * 
	 * @return double module
	 */
	public double module() {
		return Math.sqrt(Math.pow(this.re, 2) + Math.pow(this.im, 2));
	}
	
	/**
	 * Adds two complex numbers and returns a the new instance of <code>Complex</code>.
	 *
	 * @param c the other operand
	 * @return new instance of <code>Complex</code>
	 */
	public Complex add(Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}
	
	/**
	 * Subtracts c from the <code>Complex</code> and returns a the new instance of <code>Complex</code>.
	 *
	 * @param c the other operand
	 * @return new instance of <code>Complex</code>
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re - c.re, this.im - c.im);
	}
	
	/**
	 * Multiplies two complex numbers and returns a the new instance of <code>Complex</code>.
	 *
	 * @param c the other operand
	 * @return new instance of <code>Complex</code>
	 */
	public Complex mul(Complex c) {
		double newReal = this.re * c.re - this.im * c.im;
		double newImaginary = this.re * c.im + this.im * c.re;
		return new Complex(newReal, newImaginary);
	}
	
	/**
	 * Divides the <code>Complex</code> by the operand c and return the result as a new instance of <code>Complex</code>.
	 *
	 * @param c the other operand
	 * @return new instance of <code>Complex</code>
	 */
	public Complex div(Complex c) {
		double newReal = (this.re * c.re + this.im * c.im) / (c.im * c.im + c.re * c.re);
		double newImaginary = (c.re * this.im - this.re * c.im) / (c.im * c.im + c.re * c.re);
		return new Complex(newReal, newImaginary);
	}
	
	/**
	 * Raises the <code>Complex</code> to the power n and returns the result as a new instance of <code>Complex</code>.
	 *
	 * @param n the n
	 * @return new instance of <code>Complex</code>
	 */
	public Complex power(int n) {
		double newReal = Math.pow(module(), n) * Math.cos(getAngle() * n);
		double newImaginary = Math.pow(module(), n) * Math.sin(getAngle() * n);
		return new Complex(newReal, newImaginary);
	}
	
	
	/**
	 * Negates the <code>Complex</code> and returns the result as a new instance of <code>Complex</code>.
	 * 
	 * @return new instance of <code>Complex</code>
	 */
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}
	/**
	 * Calculates the n-th roots of the <code>Complex</code> and fills a new list with the results.
	 *
	 * @param n the n
	 * @return new list of <code>Complex</code>
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("N must be a positive integer!");
		}
		
		List<Complex> result = new ArrayList<>();
		
		double arg;
		double newReal;
		double newImaginary;
		
		for (int i = 0; i < n; i++) {
			arg = (getAngle() + 2 * i * Math.PI) / n;
			newReal =  Math.pow(module(), (double)1/n) * Math.cos(arg);
			newImaginary =  Math.pow(module(), (double)1/n) * Math.sin(arg);
			result.add(new Complex(newReal, newImaginary));
		}
		
		return result;
	}
	
	/**
	 * Returns the distance from given <code>Complex</code> number in a 2D plane.
	 *
	 * @param c the other <code>Complex</code>
	 * @return the distance
	 */
	public double distanceFrom(Complex c) {
		return this.sub(c).module();
	}
	
	/**
	 * Transforms the <code>Complex</code> into a new string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(re);
		if (im < 0) {
			sb.append("-i");
			sb.append(-im);
		}
		else {
			sb.append("+i");
			sb.append(im);
		}

		return sb.toString();
	}
	
	/**
	 * Parses the given string and returns a new instance of <code>Complex</code> with the given values.
	 * Appropriate formats are: ±a±ib, ±a, ±ib, ±i.
	 * Leading pluses and parts that are zero can be omitted, but empty strings are not allowed.
	 *
	 * @param s the string being parsed
	 * @return new instance of <code>Complex</code>
	 */
	public static Complex parse(String s) {
		double newReal = 0;
		double newImaginary = 0;
		
		String format1 = "[+-]?\\d+\\.?\\d*[+-]i\\d*\\.?\\d*";
		String format2 = "[+-]?i\\d+\\.?\\d*";
		String format3 = "[+-]?\\d+\\.?\\d*";
		String format4 = "[+-]?i";
		
		Pattern p1 = Pattern.compile(format1);
		Matcher m1 = p1.matcher(s);
		if (m1.find()) {
			int i;
			for (i = 1; i < s.length(); i++) {
				if (s.charAt(i) == '+' || s.charAt(i) == '-') {
					break;
				}
			}
			
			newReal = Double.parseDouble(s.substring(0, i));
			
			if (s.charAt(i) == '-') {
				if (s.length() == i + 2) newImaginary = -1;
				else newImaginary = -Double.parseDouble(s.substring(i + 2));
			} else {
				if (s.length() == i + 2) newImaginary = 1;
				else newImaginary = Double.parseDouble(s.substring(i + 2));
			}
			
			return new Complex(newReal, newImaginary);
		}
		

		Pattern p2 = Pattern.compile(format2);
		Matcher m2 = p2.matcher(s);
		if (m2.find()) {
			if (s.charAt(0) == '-') {
				String s1 = s.substring(2);
				newImaginary = -Double.parseDouble(s1);
			} else if (s.charAt(0) == '+') {
				String s1 = s.substring(2);
				newImaginary = Double.parseDouble(s1);
			} else {
				String s1 = s.substring(1);
				newImaginary = Double.parseDouble(s1);
			}
			
			return new Complex(0, newImaginary);
		}
		
		Pattern p3 = Pattern.compile(format3);
		Matcher m3 = p3.matcher(s);
		if (m3.find()) {
			newReal = Double.parseDouble(s);
			return new Complex(newReal, 0);
		}
		
		Pattern p4 = Pattern.compile(format4);
		Matcher m4 = p4.matcher(s);
		if (m4.find()) {
			if (s.charAt(0) == '-') {
				return new Complex(0, -1);
			} else {
				return new Complex(0, 1);
			}
		}
		
		throw new IllegalArgumentException("Invalid complex number format!");
	}

	
	/**
	 * Gets the angle of the <code>Complex</code>.
	 *
	 * @return the angle
	 */
	private double getAngle() {
		double angle = Math.atan2(this.im, this.re);
		if (angle < 0) angle += 2 * Math.PI;
		return angle;
	}
}
