package hr.fer.oprpp1.custom.scripting.lexer;


/**
 * The Class Token.
 */
public class Token {
	
	private TokenType type;
	private Object value;
	
	/**
	 * Instantiates a new token.
	 *
	 * @param type the type of token
	 * @param value the value of token
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Gets the token value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Gets the token type.
	 *
	 * @return the type
	 */
	public TokenType getType() {
		return type;
	}
}
