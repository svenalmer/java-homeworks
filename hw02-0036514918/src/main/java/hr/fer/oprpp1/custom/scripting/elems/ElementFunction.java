package hr.fer.oprpp1.custom.scripting.elems;

/**
 * The Class ElementFunction.
 */
public class ElementFunction extends Element {
	
	private String name;
	
	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * Gets the name of ElementFunction.
	 *
	 * @return the String name
	 */
	public String getName() {
		return name;
	}
}
