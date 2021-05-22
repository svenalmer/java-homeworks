package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class StudentDatabaseTest {
	
	private static List<String> list = new ArrayList<>(List.of("0036514918 Almer Sven 3", "0000000001 Markovic Mirko 1"));
	
	@Test
	void testForJMBAGExisting() {
		StudentDatabase db = new StudentDatabase(list);
		StudentRecord rec = db.forJMBAG("0036514918");
		assertEquals("0036514918", rec.getJmbag());
	}
	
	@Test
	void testForJMBAGNotExisting() {
		StudentDatabase db = new StudentDatabase(list);
		StudentRecord rec = db.forJMBAG("0000000008");
		assertNull(rec);
	}

	@Test
	void testFilterAll() {
		StudentDatabase db = new StudentDatabase(list);
		assertEquals(2, db.filter(new FilterAll()).size());
	}
	
	@Test
	void testFilterNone() {	
		StudentDatabase db = new StudentDatabase(list);
		assertEquals(0, db.filter(new FilterNone()).size());
	}
	private class FilterAll implements IFilter {
		@Override
		public boolean accepts(StudentRecord record) {
			return true;
		}
	}
	
	private class FilterNone implements IFilter {

		@Override
		public boolean accepts(StudentRecord record) {
			return false;
		}
		
	}
}
