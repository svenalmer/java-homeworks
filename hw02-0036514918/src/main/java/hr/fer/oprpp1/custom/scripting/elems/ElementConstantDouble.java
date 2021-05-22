package hr.fer.oprpp1.custom.scripting.elems;

/**
 * The Class ElementConstantDouble.
 */
public class ElementConstantDouble extends Element {
	
	private double value;
	
	/**
	 * Instantiates a new ElementConstantDouble.
	 *
	 * @param value the value
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	

	@Override
	public String asText() {
		return Double.toString(value);
	}
	
	/**
	 * Gets the value of ElementDoubleConstant
	 *
	 * @return double value
	 */
	public double getValue() {
		return value;
	}
}
