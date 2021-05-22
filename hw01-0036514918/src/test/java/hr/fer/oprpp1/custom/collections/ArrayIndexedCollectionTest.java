package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {
	
	@Test
	final void testAdd() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		c.add(Integer.valueOf(20));
		assertEquals(Integer.valueOf(20), c.get(0));
	}
	
	@Test
	final void testAddException() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> c.add(null));
	}

	@Test
	final void testClear() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		for(int i = 0; i < 10; i++) {
			c.add(Integer.valueOf(i));
		}
		
		c.clear();
		assertEquals(0, c.size());
	}

	@Test
	final void testArrayIndexedCollectionInt() {
		assertThrows(IllegalArgumentException.class, () -> { ArrayIndexedCollection c = new ArrayIndexedCollection(-1); });
	}

	@Test
	final void testArrayIndexedCollection() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		assertEquals(0, c.size());
	}

	@Test
	final void testArrayIndexedCollectionCollectionInt() {
		ArrayIndexedCollection testCol = new ArrayIndexedCollection();
		for (int i = 0; i < 20; i++) {
			testCol.add(Integer.valueOf(i));
		}
		
		ArrayIndexedCollection c = new ArrayIndexedCollection(testCol, 17);
		assertEquals(17, c.size());
	}

	@Test
	final void testArrayIndexedCollectionCollection() {
		ArrayIndexedCollection testCol = new ArrayIndexedCollection();
		for (int i = 0; i < 10; i++) {
			testCol.add(Integer.valueOf(i));
		}
		
		ArrayIndexedCollection c = new ArrayIndexedCollection(testCol);
		assertEquals(10, c.size());
	}

	@Test
	final void testGet() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		c.add(Integer.valueOf(20));
		assertEquals(Integer.valueOf(20), c.get(0));
	}

	@Test
	final void testInsert() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		for(int i = 0; i < 10; i++) {
			c.add(Integer.valueOf(i));
		}
		
		assertThrows(IndexOutOfBoundsException.class, () -> c.insert(Integer.valueOf(20), 14));
	}

	@Test
	final void testIndexOf() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		for(int i = 0; i < 10; i++) {
			c.add(Integer.valueOf(i));
		}
		
		assertEquals(4, c.indexOf(Integer.valueOf(4)));
	}
	
	final void testIndexOfException() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		for(int i = 0; i < 10; i++) {
			c.add(Integer.valueOf(i));
		}
		
		assertThrows(IndexOutOfBoundsException.class, () -> c.indexOf(Integer.valueOf(15)));
	}

	@Test
	final void testRemoveInt() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		for(int i = 0; i < 10; i++) {
			c.add(Integer.valueOf(i));
		}
		assertEquals(10, c.size());
		c.remove(4);
		assertEquals(9, c.size());
	}

}
