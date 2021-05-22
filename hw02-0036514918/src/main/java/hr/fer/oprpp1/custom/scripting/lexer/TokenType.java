package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * The Enum TokenType. Contains possible types of a token.
 */
public enum TokenType {
	TEXT,
	NUMBER,
	BEGINTAG,
	ENDTAG,
	FUNCTION,
	VARIABLE,
	TAGNAME,
	OPERATOR,
	EOF;
}
