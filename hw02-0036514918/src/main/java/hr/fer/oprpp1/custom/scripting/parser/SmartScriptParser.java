package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.Lexer;
import hr.fer.oprpp1.custom.scripting.lexer.Token;
import hr.fer.oprpp1.custom.scripting.lexer.TokenType;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;

/**
 * The Class SmartScriptParser.
 */
public class SmartScriptParser {
	
	private Lexer l;
	private ObjectStack stack;
	
	/**
	 * Instantiates a new smart script parser and starts the parsing of given text.
	 *
	 * @param s the s
	 */
	public SmartScriptParser(String s) {
		l = new Lexer(s);
		parse();
	}
	
	/**
	 * Parses the text. First tokenizes the text using a lexer, and then creates a document tree model.
	 */
	private void parse() {
		ArrayIndexedCollection tokens = l.tokenizeText();
		
		stack.push(new DocumentNode());
		
		ElementsGetter getter = tokens.createElementsGetter();
		while (getter.hasNextElement()) {
			Token t = (Token)getter.getNextElement();
			
			if (t.getType() == TokenType.BEGINTAG) {
				t = (Token)getter.getNextElement();
				if (t.getType() == TokenType.TAGNAME && ((String)t.getValue()).equals("FOR")) {
					addForLoopNode(getter);
				} else if (t.getType() == TokenType.TAGNAME && ((String)t.getValue()).equals("=")) {
					addEchoNode(getter);
				}
			}
			
			
		}
	}
	
	/**
	 * Adds the for loop node.
	 *
	 * @param getter the getter of tokens
	 */
	private void addForLoopNode(ElementsGetter getter) {
		ForLoopNode node;
		ElementVariable var;
		
		
	}
	
	/**
	 * Adds the echo node.
	 *
	 * @param getter the getter of tokens
	 */
	private void addEchoNode(ElementsGetter getter) {
		
	}
	
}
