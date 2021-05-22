package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class StudentDatabase.
 */
public class StudentDatabase {

	private List<StudentRecord> records;
	private Map<String, StudentRecord> index;

	/**
	 * Instantiates a new student database based on the given list of strings.
	 * If there are any duplicate JMBAG-s or grades that are outside the acceptable range(1-5),
	 * the user is notified and the program is terminated.
	 *
	 * @param strings the list of Strings.
	 */
	public StudentDatabase(List<String> strings) {
		records = new ArrayList<>();
		index = new HashMap<>();

		for (String s : strings) {
			String[] data = s.split("\\s+");

			int grade = Integer.parseInt(data[3]);
			if (grade < 1 || grade > 5) {
				System.err.println("Grade must be a number between 1 and 5! Instead was: " + grade);
				System.exit(0);
			}

			StudentRecord rec = new StudentRecord(data[0], data[1], data[2], grade);
			if (index.containsKey(data[0]) || records.contains(rec)) {
				System.err.println("There is a duplicate jmbag at record: " + rec);
				System.exit(0);
			}

			index.put(data[0], rec);
			records.add(rec);
		}
	}

	/**
	 * Returns data of student with the correspoding JMBAG in the form of <code>StudentRecord</code>. If there is no such student, returns null.
	 *
	 * @param jmbag the jmbag
	 * @return <code>StudentRecord</code>
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}

	/**
	 * Filters the records in the database using given filter.
	 *  
	 * <p>
	 * Instantiates a new empty list of student records. For each student record in the database, the
	 * method <code>filter.accepts(StudentRecord record)</code> is called. If it returns <code>true</code>, the record
	 * is added to the newly formed list. After checking all the elements, the newly formed list is returned.
	 *
	 * @param filter the filter to be used
	 * @return the list
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> temp = new ArrayList<>();
		for (StudentRecord rec : records)
			if (filter.accepts(rec)) temp.add(rec);
		
		return temp;
	}

}
