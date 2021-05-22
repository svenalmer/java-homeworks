package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.hw02.prob1.LexerException;

/**
 * The Class Lexer.
 */
public class Lexer {
	
	private char[] data;
	private Token token;		//trenutni token
	private int currentIndex;	//indeks prvog neobradenog znaka
	private LexerState state;
	
	/**
	 * Instantiates a new lexer with the given document.
	 *
	 * @param text the text being analyzed
	 */
	public Lexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
	}
	
	/**
	 * Divides the whole given text into tokens and returns a collection of Tokens.
	 *
	 * @return the ArrayIndexedCollection of Tokens.
	 */
	public ArrayIndexedCollection tokenizeText() {
		ArrayIndexedCollection tokens = new ArrayIndexedCollection();
		while (nextToken().getType() != TokenType.EOF) {
			tokens.add(getToken());
		}
		return tokens;
	}
	
	/**
	 * Returns the next token. If there is no new token or the input is invalid, throws an exception.
	 *
	 * @return the token
	 * @throws LexerException
	 */
	public Token nextToken() {
		if(token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("Trying to get new token after EOF!");
		}
		
		skipEmptySpaces();
		
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		if (state == LexerState.BASIC) {
			if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
				token = getBeginTagToken();
			} else {
				token = getWordToken();
			}
		} else {
			if (token.getType() == TokenType.BEGINTAG) {
				token = getTagNameToken();
			} else if (Character.isLetter(data[currentIndex])) {
				token = getVariableToken();
			} else if (Character.isDigit(data[currentIndex]) || (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1]))) {
				token = getNumberToken();
			} else if (data[currentIndex] == '@') {
				token = getFunctionToken();
			} else if (data[currentIndex] == '\"') {
				token = getWholeStringToken();
			} else if (data[currentIndex] == '-' ||
					   data[currentIndex] == '+' ||
					   data[currentIndex] == '/' ||
					   data[currentIndex] == '*' ||
					   data[currentIndex] == '^') {
				token = getOperatorToken();
			} else if (data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
				token = getEndTagToken();
			} else {
				throw new LexerException("Invalid input");
			}
		} 
		
		return token;
	}
	
	/**
	 * Gets the latest token without looking for the next one.
	 *
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Sets the state of the lexer. Can be BASIC or TAG.
	 *
	 * @param state the new state
	 */
	public void setState(LexerState state) {
		if (state == null)
			throw new NullPointerException("Cannot set lexer state to null!");
		this.state = state;
	}
	
	/**
	 * Skips empty spaces between tokens.
	 */
	private void skipEmptySpaces() {
		if (currentIndex == data.length) return;
		while (data[currentIndex]== ' ' ||
				data[currentIndex]== '\r'||
				data[currentIndex]== '\n'||
				data[currentIndex]== '\t'){
			currentIndex++;
			if(currentIndex >= data.length) {
				break;
			}
		}
	}
	
	/**
	 * Gets the token of type WORD.
	 *
	 * @return the word token
	 */
	private Token getWordToken() {
		StringBuilder sb = new StringBuilder();
		if (state == LexerState.BASIC) {
			while (currentIndex < data.length && data[currentIndex]!= '{') {
				
				if (data[currentIndex] == '\\') {
					if (currentIndex == data.length - 1 || (data[currentIndex + 1] != '\\' && data[currentIndex] != '{')) {
						throw new LexerException("Invalid escape!");
					}
					else {
						sb.append(data[currentIndex + 1]);
						currentIndex += 2;
					}
				} else if (currentIndex < data.length - 1 && data[currentIndex] == '{' && data[currentIndex] == '$') {
					break;
				} else {
					sb.append(data[currentIndex]);
					currentIndex++;
				}
			}
		}
		
		return new Token(TokenType.TEXT, sb.toString());
	}
	
	/**
	 * Gets the token of type TAGNAME.
	 *
	 * @return the tag name token
	 */
	private Token getTagNameToken() {
		StringBuilder sb = new StringBuilder();
		String res;
		
		if (Character.isLetter(data[currentIndex])) {
			while (Character.isLetter(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
			res = sb.toString().toUpperCase();
			
		} else if (data[currentIndex] == '=') {
			sb.append(data[currentIndex]);
			currentIndex++;
			res = sb.toString();
			
		} else
			throw new LexerException("Invalid tag name!");
		
		return new Token(TokenType.TAGNAME, res);
	}
	
	/**
	 * Gets the token of type VARIABLE.
	 *
	 * @return the variable token
	 */
	private Token getVariableToken() {
		StringBuilder sb = new StringBuilder();
		
		while (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex]) || data[currentIndex] == '_') {
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		
		return new Token(TokenType.VARIABLE, sb.toString());
	}
	
	/**
	 * Gets the token of type NUMBER.
	 *
	 * @return the number token
	 */
	private Token getNumberToken() {
		StringBuilder sb = new StringBuilder();
		sb.append(data[currentIndex]);
		currentIndex++;
		
		while (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.') {
			if (data[currentIndex] == '.' && !Character.isDigit(data[currentIndex + 1])) {
				throw new LexerException("Invalid number format!");
			} else {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
		}
		
		return new Token(TokenType.NUMBER, sb.toString());
	}
	
	/**
	 * Gets the next token of type FUNCTION.
	 *
	 * @return the function token
	 */
	private Token getFunctionToken() {
		StringBuilder sb = new StringBuilder();
		sb.append(data[currentIndex]);
		currentIndex++;
		if (!Character.isLetter(data[currentIndex])) {
			throw new LexerException("Invalid function name");
		}
		
		while (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex]) || data[currentIndex] == '_') {
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		
		return new Token(TokenType.FUNCTION, sb.toString());
	}
	
	/**
	 * Gets the next token of type OPERATOR.
	 *
	 * @return the operator token
	 */
	private Token getOperatorToken() {
		return new Token(TokenType.OPERATOR, data[currentIndex++]);
	}
	
	/**
	 * Gets the next token of type BEGINTAG.
	 *
	 * @return the begin tag token
	 */
	private Token getBeginTagToken() {
		currentIndex += 2;
		setState(LexerState.TAG);
		return new Token(TokenType.BEGINTAG, "{$");
	}
	
	/**
	 * Gets the next token of type ENDTAG.
	 *
	 * @return the end tag token
	 */
	private Token getEndTagToken() {
		currentIndex += 2;
		setState(LexerState.TAG);
		return new Token(TokenType.BEGINTAG, "$}");
	}
	
	/**
	 * Gets the next token of type WORD. This method also puts the empty spaces in one token.
	 * The string gets parsed and put into one token until it reaches the " sign.
	 *
	 * @return the word token
	 */
	private Token getWholeStringToken() {
		StringBuilder sb = new StringBuilder();
		currentIndex++; //skipping character "
		while (currentIndex < data.length && data[currentIndex] != '\"') {
			if (data[currentIndex] == '\\') {
				if (currentIndex < data.length - 1 && (data[currentIndex + 1] == '\"' || data[currentIndex + 1] == '\"')) {
					sb.append(data[currentIndex + 1]);
					currentIndex += 2;
				} else {
					throw new LexerException("Invalid escape!");
				}
			} else {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
		}
		
		return new Token(TokenType.TEXT, sb.toString());
	}
}
