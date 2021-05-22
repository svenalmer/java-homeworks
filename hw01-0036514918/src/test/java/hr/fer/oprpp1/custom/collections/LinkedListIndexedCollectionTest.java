package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {

	@Test
	final void testAdd() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		c.add(Integer.valueOf(20));
		assertEquals(Integer.valueOf(20), c.get(0));
	}
	
	@Test
	final void testAddException() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> c.add(null));
	}

	@Test
	final void testClear() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		for(int i = 0; i < 10; i++) {
			c.add(Integer.valueOf(i));
		}
		
		c.clear();
		assertEquals(0, c.size());
	}


	@Test
	final void testLinkedListIndexedCollection() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		assertEquals(0, c.size());
	}

	@Test
	final void testLinkedListIndexedCollectionCollection() {
		LinkedListIndexedCollection testCol = new LinkedListIndexedCollection();
		for (int i = 0; i < 10; i++) {
			testCol.add(Integer.valueOf(i));
		}
		
		LinkedListIndexedCollection c = new LinkedListIndexedCollection(testCol);
		assertEquals(10, c.size());
	}

	@Test
	final void testGet() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		c.add(Integer.valueOf(20));
		assertEquals(Integer.valueOf(20), c.get(0));
	}

	@Test
	final void testInsert() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		for(int i = 0; i < 10; i++) {
			c.add(Integer.valueOf(i));
		}
		
		assertThrows(IndexOutOfBoundsException.class, () -> c.insert(Integer.valueOf(20), 14));
	}

	@Test
	final void testIndexOf() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		for(int i = 0; i < 10; i++) {
			c.add(Integer.valueOf(i));
		}
		
		assertEquals(4, c.indexOf(Integer.valueOf(4)));
	}
	
	final void testIndexOfException() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		for(int i = 0; i < 10; i++) {
			c.add(Integer.valueOf(i));
		}
		
		assertThrows(IndexOutOfBoundsException.class, () -> c.indexOf(Integer.valueOf(15)));
	}

	@Test
	final void testRemoveInt() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		for(int i = 0; i < 10; i++) {
			c.add(Integer.valueOf(i));
		}
		assertEquals(10, c.size());
		c.remove(4);
		assertEquals(9, c.size());
	}

}
