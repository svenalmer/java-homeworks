package hr.fer.oprpp1.custom.scripting.elems;

/**
 * The Class ElementConstantInteger.
 */
public class ElementConstantInteger extends Element {
	
	private int value;
	
	/**
	 * Instantiates a new ElementConstantInteger.
	 *
	 * @param value the value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
	/**
	 * Gets the ElementConstantInteger's value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
}
