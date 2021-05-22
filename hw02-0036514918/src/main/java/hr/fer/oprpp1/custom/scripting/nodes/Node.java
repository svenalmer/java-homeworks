package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * The Class Node.
 */
public abstract class Node {

	private ArrayIndexedCollection children;
	
	/**
	 * Adds the given node to the collection of children of a node.
	 *
	 * @param child the child
	 */
	public void addChildNode(Node child) {
		if (children == null) {
			children = new ArrayIndexedCollection();
		}
		children.add(child);
	}
	
	/**
	 * Number of node's children.
	 *
	 * @return the int
	 */
	public int numberOfChildren() {
		if(children == null) return 0;
		else return children.size();
	}
	
	/**
	 * Gets node's child at the given index.
	 *
	 * @param index the index
	 * @return the child
	 */
	public Node getChild(int index) {
		return (Node)children.get(index);
	}
}
