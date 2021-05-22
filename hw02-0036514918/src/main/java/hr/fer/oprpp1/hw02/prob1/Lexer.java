package hr.fer.oprpp1.hw02.prob1;

public class Lexer {
	private char[] data;		//ulazni tekst
	private Token token;		//trenutni token
	private int currentIndex;	//indeks prvog neobradenog znaka
	private LexerState state;
	
	//konstruktor prima ulazni tekst koji se tokenizira
	public Lexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
	}
	
	//generira i vraca sljedeci token
	//baca LexerException ako dode do pogreske
	public Token nextToken() {
		if(token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("Trying to get new token after EOF!");
		}
		
		skipEmptySpaces();
		
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		boolean ignoreType = false;
		StringBuilder sb = new StringBuilder();
		
		if ((Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') || 
			(state == LexerState.EXTENDED && data[currentIndex] != '#')) {
			
			while (currentIndex < data.length && 
				  (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\' 	|| 
				  ignoreType || (state == LexerState.EXTENDED) && data[currentIndex] != '#')) {
				
				if (ignoreType || state == LexerState.EXTENDED) {
					if (Character.isLetter(data[currentIndex]) && state == LexerState.BASIC)
						throw new LexerException("Invalid Escape!");
					else if(data[currentIndex]== ' ' ||
							data[currentIndex]== '\r'||
							data[currentIndex]== '\n'||
							data[currentIndex]== '\t') {
						break;
					} else {
						sb.append(data[currentIndex]);
						ignoreType = false;
					}
				} else {
					if (data[currentIndex] == '\\') 
						ignoreType = true;
					else 
						sb.append(data[currentIndex]);
				}
				
				currentIndex++;
			}
			
			if (ignoreType && currentIndex >= data.length) 
				throw new LexerException("Invalid escaped ending!");
			
			token = new Token(TokenType.WORD, sb.toString());
			
		} else if (Character.isDigit(data[currentIndex])) {
			
			while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
			
			try {
				token = new Token(TokenType.NUMBER, Long.parseLong(sb.toString()));
			} catch (NumberFormatException e) {
				throw new LexerException("Invalid input!");
			}
		} else {
			if (data[currentIndex] == '#') {
				if (state == LexerState.BASIC) state = LexerState.EXTENDED;
				else state = LexerState.BASIC;
			}
			token = new Token(TokenType.SYMBOL, data[currentIndex++]);
		}
		
		return token;
	}
	
	//vraca zadnji generirani token; moze se pozivati
	//vise puta; ne pokrece generiranje sljedeceg tokena
	public Token getToken() {
		return token;
	}
	
	public void setState(LexerState state) {
		if (state == null)
			throw new NullPointerException("Lexer state cannot be null!");
		this.state = state;
	}
	
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
 }
