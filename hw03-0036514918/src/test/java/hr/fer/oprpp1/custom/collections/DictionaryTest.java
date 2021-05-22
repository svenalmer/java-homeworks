package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {

	@Test
	void testPutNullKey() {
		Dictionary<String, Integer> dict = new Dictionary<>(); 
		assertThrows(NullPointerException.class, () -> dict.put(null, 6));
	}
	
	@Test
	void testPutSameKey() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		dict.put("Key", 4);
		assertEquals(4, dict.get("Key"));
		dict.put("Key", 7);
		assertEquals(7, dict.get("Key"));
	}

	@Test
	void testSize() {
		Dictionary<String, Integer> dict = new Dictionary<>(); 
		dict.put("SomeKey", 1);
		dict.put("otherKey", 6);
		assertEquals(2, dict.size());
	}
	
	@Test
	void testIsEmpty() {
		Dictionary<String, Integer> dict = new Dictionary<>(); 
		assertEquals(true, dict.isEmpty());
	}
	
	@Test
	void testClear() {
		Dictionary<String, Integer> dict = new Dictionary<>(); 
		dict.put("SomeKey", 1);
		dict.put("otherKey", 6);
		assertEquals(2, dict.size());
		dict.clear();
		assertEquals(0, dict.size());
	}
	
	@Test
	void testGet() {
		Dictionary<String, Integer> dict = new Dictionary<>(); 
		dict.put("SomeKey", 1);
		dict.put("otherKey", 6);
		assertEquals(1, dict.get("SomeKey"));
	}
	
	@Test
	void testGetNonexistent() {
		Dictionary<String, Integer> dict = new Dictionary<>(); 
		dict.put("SomeKey", 1);
		dict.put("otherKey", 6);
		assertNull(dict.get("kljuc"));
	}
}
