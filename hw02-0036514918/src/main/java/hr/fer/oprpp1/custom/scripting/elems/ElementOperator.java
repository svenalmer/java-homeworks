package hr.fer.oprpp1.custom.scripting.elems;

/**
 * The Class ElementOperator.
 */
public class ElementOperator extends Element {
	
	private String symbol;

	@Override
	public String asText() {
		return symbol;
	}
	
	/**
	 * Gets the operator symbol.
	 *
	 * @return the String symbol
	 */
	public String getSymbol() {
		return symbol;
	}
}
