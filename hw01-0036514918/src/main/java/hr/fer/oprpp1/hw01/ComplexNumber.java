package hr.fer.oprpp1.hw01;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc
/**
 * The Class representing a complex number, allowing the user to perform various operations.
 */
public class ComplexNumber {
	
	private double real;
	private double imaginary;
	
	/**
	 * Instantiates a new complex number.
	 *
	 * @param real value of the real component
	 * @param imaginary  value of the imaginary component
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Returns a new instance of <code>ComplexNumber</code> with the given real value and imaginary value of 0.
	 *
	 * @param real the real value
	 * @return new instance of <code>ComplexNumber</code>
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Returns a new instance of <code>ComplexNumber</code> with the given imaginary value and real value of 0.
	 *
	 * @param imaginary the imaginary value
	 * @return new instance of <code>ComplexNumber</code>
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Returns a new instance of <code>ComplexNumber</code> created from the given polar coordinates.
	 *
	 * @param magnitude the magnitude
	 * @param angle the angle
	 * @return new instance of <code>ComplexNumber</code>
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	/**
	 * Parses the given string and returns a new instance of <code>ComplexNumber</code>.
	 * Allowed formats are: "±a±bi", "±a", "±bi" (leading pluses are allowed, but not mandatory).
	 *
	 * @param s the string
	 * @return new instance of <code>ComplexNumber</code>
	 */
	public static ComplexNumber parse(String s) {
		double newReal = 0;
		double newImaginary = 0;
		
		String format1 = "[+-]?\\d+\\.?\\d*[+-]\\d+\\.?\\d*i";
		String format2 = "[+-]?\\d+\\.?\\d*i";
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
			newImaginary = Double.parseDouble(s.substring(i, s.length() - 1));
			return new ComplexNumber(newReal, newImaginary);
		}
		

		Pattern p2 = Pattern.compile(format2);
		Matcher m2 = p2.matcher(s);
		if (m2.find()) {
			String s1 = s.substring(0, s.length() - 1);
			newImaginary = Double.parseDouble(s1);
			return fromImaginary(newImaginary);
		}
		
		Pattern p3 = Pattern.compile(format3);
		Matcher m3 = p3.matcher(s);
		if (m3.find()) {
			newReal = Double.parseDouble(s);
			return fromReal(newReal);
		}
		
		Pattern p4 = Pattern.compile(format4);
		Matcher m4 = p4.matcher(s);
		if (m4.find()) {
			if (s.charAt(0) == '-') {
				return fromImaginary(-1);
			} else {
				return fromImaginary(1);
			}
		}
		
		throw new IllegalArgumentException("Invalid complex number format!");
	}
	
	/**
	 * Returns the real value.
	 *
	 * @return the real value
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Returns the imaginary value
	 *
	 * @return the imaginary value
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Calculates the magnitude of the complex number.
	 *
	 * @return the magnitude
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.imaginary, 2));
	}
	
	/**
	 * Calculates the angle of the complex number.
	 *
	 * @return the angle
	 */
	public double getAngle() {
		double angle = Math.atan2(this.imaginary, this.real);
		if (angle < 0) angle += 2 * Math.PI;
		return angle;
	}
	
	/**
	 * Adds two complex numbers and returns a the new instance of <code>ComplexNumber</code>.
	 *
	 * @param c the other operand
	 * @return new instance of <code>ComplexNumber</code>
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * Subtracts c from the <code>ComplexNumber</code> and returns a the new instance of <code>ComplexNumber</code>.
	 *
	 * @param c the other operand
	 * @return new instance of <code>ComplexNumber</code>
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	/**
	 * Multiplies two complex numbers and returns a the new instance of <code>ComplexNumber</code>.
	 *
	 * @param c the other operand
	 * @return new instance of <code>ComplexNumber</code>
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double newReal = this.real * c.real - this.imaginary * c.imaginary;
		double newImaginary = this.real * c.imaginary + this.imaginary * c.real;
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Divides the <code>ComplexNumber</code> by the operand c and return the result as a new instance of <code>ComplexNumber</code>.
	 *
	 * @param c the other operand
	 * @return new instance of <code>ComplexNumber</code>
	 */
	public ComplexNumber div(ComplexNumber c) {
		double newRadius = this.getMagnitude() / c.getMagnitude();
		double newReal = newRadius * Math.cos(this.getAngle() - c.getAngle());
		double newImaginary = newRadius * Math.sin(this.getAngle() - c.getAngle());
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Raises the <code>ComplexNumber</code> to the power n and returns the result as a new instance of <code>ComplexNumber</code>.
	 *
	 * @param n the n
	 * @return new instance of <code>ComplexNumber</code>
	 */
	public ComplexNumber power(int n) {
		double newReal = Math.pow(getMagnitude(), n) * Math.cos(getAngle() * n);
		double newImaginary = Math.pow(getMagnitude(), n) * Math.sin(getAngle() * n);
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Calculates the n-th roots of the <code>ComplexNumber</code> and fills a new array with the results.
	 *
	 * @param n the n
	 * @return new array of <code>ComplexNumber</code>
	 */
	public ComplexNumber[] root(int n) {
		
		ComplexNumber[] result = new ComplexNumber[n];
		
		double arg;
		double newReal;
		double newImaginary;
		
		for (int i = 0; i < n; i++) {
			arg = (getAngle() + 2 * i * Math.PI) / n;
			newReal =  Math.pow(getMagnitude(), (double)1/n) * Math.cos(arg);
			newImaginary =  Math.pow(getMagnitude(), (double)1/n) * Math.sin(arg);
			result[i] = new ComplexNumber(newReal, newImaginary);
		}
		
		return result;
	}
	
	/**
	 * Transforms the <code>ComplexNumber</code> into a new string.
	 *
	 * @return the string
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if (real != 0) {
			sb.append(real);
			if (imaginary > 0) sb.append("+" + imaginary + "i");
			else if (imaginary < 0) sb.append(imaginary + "i");
			
		} else {
			if (imaginary != 0 && imaginary != 1 && imaginary != -1) sb.append(imaginary + "i");
			else if (imaginary == -1) sb.append("-i");
			else if (imaginary == 1) sb.append("i");
			else sb.append(0);
		}
		
		return sb.toString();
	}
			
}
