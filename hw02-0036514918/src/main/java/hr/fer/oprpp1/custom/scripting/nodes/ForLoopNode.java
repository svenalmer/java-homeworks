package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * The Class ForLoopNode.
 */
public class ForLoopNode extends Node {
	
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression; //Can be null
	
	/**
	 * Instantiates a new for loop node.
	 */
	public ForLoopNode() {
		
	}
}
