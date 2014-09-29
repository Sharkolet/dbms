package dbms;

import static org.junit.Assert.*;

import org.junit.*;

import ua.shark.dbms.entities.*;

public class TestTable {
	Table expectedJoin;
	Table students;
	Table people;
	
	@Before
	public void initialize() {
		students = new Table("Student");
		people = new Table("People");
		Record rec = new Record();
		
		rec = new Record();
		students.addAttribute(new Attribute("Year", Type.INT));
		students.addAttribute(new Attribute("Bal", Type.INT));
		rec.addValue(new Value(8, Type.INT));
		rec.addValue(new Value(190, Type.INT));
		students.addRecord(rec);
		
		rec = new Record();
		people.addAttribute(new Attribute("Age", Type.INT));
		people.addAttribute(new Attribute("kator", Type.INT));
		rec.addValue(new Value(8, Type.INT));
		rec.addValue(new Value(879, Type.INT));
		people.addRecord(rec);
	}
	
	@Test
	public void testJoining() {
		Table expectedJoin = new Table("ExpectedJoin");
		Record rec = new Record();
		expectedJoin.addAttribute(new Attribute("Year", Type.INT));
		expectedJoin.addAttribute(new Attribute("Bal", Type.INT));
		expectedJoin.addAttribute(new Attribute("Age", Type.INT));
		expectedJoin.addAttribute(new Attribute("kator", Type.INT));
		rec.addValue(new Value(8, Type.INT));
		rec.addValue(new Value(190, Type.INT));
		rec.addValue(new Value(8, Type.INT));
		rec.addValue(new Value(879, Type.INT));
		expectedJoin.addRecord(rec);
		assertEquals("JoinFailed", expectedJoin, students.joinTables(people, 0, 0));
	}
	
	@Test
	public void testIntersection() {
		Table expectedIntersection = new Table("ExpectedIntersection");
		Record rec = new Record();
		expectedIntersection.addAttribute(new Attribute("Year", Type.INT));
		expectedIntersection.addAttribute(new Attribute("Bal", Type.INT));
		rec.addValue(new Value(8, Type.INT));
		rec.addValue(new Value(190, Type.INT));
		rec.addValue(new Value(8, Type.INT));
		rec.addValue(new Value(879, Type.INT));
		expectedIntersection.addRecord(rec);
		assertEquals("IntersectionFailed", expectedIntersection, students.intersectTables(people));
	}
	
	@Test
	public void testAttributeAdding() {
		Table expectedAdd = new Table("ExpectedAdd");
		Record rec = new Record();
		expectedAdd.addAttribute(new Attribute("Year", Type.INT));
		expectedAdd.addAttribute(new Attribute("Bal", Type.INT));
		rec.addValue(new Value(8, Type.INT));
		rec.addValue(new Value(190, Type.INT));
		expectedAdd.addRecord(rec);
		expectedAdd.addAttribute(new Attribute("Male", Type.CHAR));
		students.addAttribute(new Attribute("Male", Type.CHAR));
		assertEquals("AddingAttributeFailed", expectedAdd, students);
	}

}
