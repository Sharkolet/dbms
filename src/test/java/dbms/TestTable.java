package dbms;

import static org.junit.Assert.*;

import org.junit.*;

import ua.shark.dbms.entities.*;

public class TestTable {
	Table students;
	Table people;
	
	@Before
	public void initialize() {
		students = new Table("Student");
		people = new Table("People");
		
		students.addAttribute(new Attribute("Year", Integer.class));
		students.addAttribute(new Attribute("Bal", Integer.class));
		students.addRecord(new Record(new String[]{"8", "190"}, students.getHeader()));
		
		people.addAttribute(new Attribute("Age", Integer.class));
		people.addAttribute(new Attribute("kator", Integer.class));
		people.addRecord(new Record(new String[]{"8", "879"}, people.getHeader()));
	}
	
	@Test
	public void testJoining() {
		Table expectedJoin = new Table("ExpectedJoin");
		expectedJoin.addAttribute(new Attribute("Year", Integer.class));
		expectedJoin.addAttribute(new Attribute("Bal", Integer.class));
		expectedJoin.addAttribute(new Attribute("Age", Integer.class));
		expectedJoin.addAttribute(new Attribute("kator", Integer.class));
		expectedJoin.addRecord(new Record(new String[]{"8", "190", "8", "879"}, expectedJoin.getHeader()));
		assertEquals("JoinFailed", expectedJoin, students.joinTables(people, 0, 0));
	}
	
	@Test
	public void testIntersection() {
		Table expectedIntersection = new Table("ExpectedIntersection");
		expectedIntersection.addAttribute(new Attribute("Year", Integer.class));
		expectedIntersection.addAttribute(new Attribute("Bal", Integer.class));
		assertEquals("IntersectionFailed", expectedIntersection, students.intersectTables(people));
	}
	
	@Test
	public void testAttributeAdding() {
		Table expectedAdd = new Table("ExpectedAdd");
		expectedAdd.addAttribute(new Attribute("Year", Integer.class));
		expectedAdd.addAttribute(new Attribute("Bal", Integer.class));
		expectedAdd.addRecord(new Record(new String[]{"8", "190"}, expectedAdd.getHeader()));
		expectedAdd.addAttribute(new Attribute("Male", String.class));
		students.addAttribute(new Attribute("Male", String.class));
		assertEquals("AddingAttributeFailed", expectedAdd, students);
	}

}
