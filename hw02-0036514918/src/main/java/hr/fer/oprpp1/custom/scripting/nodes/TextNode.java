package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * The Class TextNode.
 */
public class TextNode extends Node {

	private String text;
	
	/**
	 * Instantiates a new text node.
	 *
	 * @param text the text
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Gets the text of the node.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}
}
