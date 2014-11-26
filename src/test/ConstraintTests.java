package test;

import java.util.ArrayList;
import java.util.List;

import examSchedule.assignment.Assignment;
import examSchedule.assignment.Room;
import examSchedule.assignment.Session;
import examSchedule.assignment.Student;
import examSchedule.course.Instructor;
import examSchedule.course.Lecture;
import examSchedule.date.Time;
import examSchedule.solution.Constraints;

import org.junit.*;

public class ConstraintTests {
	

	Lecture lecture1 = new Lecture("CPSC433", "L01");
	Lecture lecture2 = new Lecture("CPSC413", "L02");
	Lecture lecture3 = new Lecture("GLGY209", "L01");
	Lecture lecture4 = new Lecture("PSYC383", "L02");
	Session session1 = new Session("S1");
	Session session2 = new Session("S2");
	Session session3 = new Session("S3");
	Session session4 = new Session("S4");
	
	@Test
	// Single student with 2 exams at 6:00
	public void SoftCon1failtest1() {
		Student student = new Student("Bob");
		Time time = new Time.Builder("6:00").build();
		session1.setTime(time);
		session1.setDay("M");
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		session2.setTime(time);
		session2.setDay("M");
		student.enroll(lecture2);

		
		int violation = Constraints.calcSoftOne(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 100, violation);
	}
	
	@Test
	// Single student with 1 exam at 6:00, another at 6:30
	public void SoftCon1failtest2() {
		Student student = new Student("Bob");
		Time time = new Time.Builder("6:00").build();
		session1.setTime(time);
		session1.setDay("M");
		session1.setLength(3);
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		Time time2 = new Time.Builder("6:30").build();
		session2.setTime(time2);
		session2.setDay("M");
		student.enroll(lecture2);

		
		int violation = Constraints.calcSoftOne(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 100, violation);
	}
	
	@Test
	// Student 1: Exams at 6:00 and 6:30
	// Student 2: Exams at 5:00 and 6:30
	public void SoftCon1failtest3() {
		Student student = new Student("Bob");
		Time time = new Time.Builder("6:00").build();
		session1.setTime(time);
		session1.setDay("T");
		session1.setLength(3);
		lecture1.setSession(session1);
		student.enroll(lecture1);

		Student student2 = new Student("Fred");
		Time time2 = new Time.Builder("5:00").build();
		session2.setTime(time2);
		session2.setDay("T");
		session2.setLength(3);
		lecture2.setSession(session2);
		student2.enroll(lecture2);
		
		Time time3 = new Time.Builder("6:30").build();
		session3.setTime(time3);
		session3.setDay("T");
		session3.setLength(3);
		
		student.enroll(lecture3);
		student2.enroll(lecture3);
		
		int violation = Constraints.calcSoftOne(session3, lecture3);
		Assert.assertEquals("Soft constraint violation is incorrect", 200, violation);
	}
	
	@Test
	public void SoftCon1passtest() {
		Student student = new Student("Bob");
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		session1.setDay("M");
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		Time time2 = new Time.Builder("3:00").build();
		session2.setTime(time2);
		session2.setDay("M");
		student.enroll(lecture2);

		
		int violation = Constraints.calcSoftOne(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation);
	}
	
	
	
	@Test
	// Single instructor supervising two different rooms at 9:00
	public void SoftCon2failtest1() {
		Instructor instructor = new Instructor("John");
		instructor.addInstructedLecture(lecture1);
		lecture1.setInstructor(instructor);
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		session1.setDay("M");
		Room room = new Room("ST148");
		room.setCapacity(250);
		session1.setRoom(room);
		lecture1.setSession(session1);
				
		lecture2.setInstructor(instructor);
		instructor.addInstructedLecture(lecture2);
		session2.setTime(time);
		session2.setDay("M");
		Room room2 = new Room("ST140");
		room2.setCapacity(250);
		session2.setRoom(room2);

		int violation = Constraints.calcSoftTwo(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 20, violation);
	}
	
	
	@Test
	// Single instructor supervising two different rooms at 9:00 and 9:30
	public void SoftCon2failtest2() {
		Instructor instructor = new Instructor("John");
		instructor.addInstructedLecture(lecture1);
		lecture1.setInstructor(instructor);
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		session1.setDay("M");
		session1.setLength(3);
		Room room = new Room("ST148");
		room.setCapacity(250);
		session1.setRoom(room);
		lecture1.setSession(session1);
				
		lecture2.setInstructor(instructor);
		instructor.addInstructedLecture(lecture2);
		Time time2 = new Time.Builder("9:30").build();
		session2.setTime(time2);
		session2.setDay("M");
		Room room2 = new Room("ST140");
		room2.setCapacity(250);
		session2.setRoom(room2);

		int violation = Constraints.calcSoftTwo(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 20, violation);
	}
	
	
/*	@Test
	// Single instructor supervising two different rooms at 9:00 and 9:30
	public void SoftCon2failtest3() {
		Instructor instructor = new Instructor("John");
		instructor.addInstructedLecture(lecture1);
		lecture1.setInstructor(instructor);
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		session1.setDay("M");
		session1.setLength(3);
		Room room = new Room("ST148");
		room.setCapacity(250);
		session1.setRoom(room);
		lecture1.setSession(session1);
				
		lecture2.setInstructor(instructor);
		instructor.addInstructedLecture(lecture2);
		Time time2 = new Time.Builder("9:30").build();
		session2.setTime(time2);
		session2.setDay("M");
		Room room2 = new Room("ST140");
		room2.setCapacity(250);
		session2.setRoom(room2);

		int violation = Constraints.calcSoftTwo(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 20, violation);
	}*/
	
	@Test
	public void SoftCon2passtest() {
		Instructor instructor = new Instructor("John");
		instructor.addInstructedLecture(lecture1);
		lecture1.setInstructor(instructor);
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		session1.setDay("M");
		Room room = new Room("ST148");
		room.setCapacity(250);
		session1.setRoom(room);
		lecture1.setSession(session1);
		
		
		lecture2.setInstructor(instructor);
		instructor.addInstructedLecture(lecture2);
		session2.setTime(time);
		session2.setDay("T");
		Room room2 = new Room("ST140");
		room2.setCapacity(250);
		session2.setRoom(room2);

		
		int violation = Constraints.calcSoftTwo(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation);
	}
	
	@Test
	public void SoftCon3failtest() {
		session1.setDay("M");
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		lecture1.setSession(session1);
		List<Lecture> listofLecs = new ArrayList<Lecture>();
		listofLecs.add(lecture1);
		
		Lecture lecture2 = new Lecture("CPSC433", "L02");
		Time time2 = new Time.Builder("5:00").build();
		session2.setTime(time2);
		session2.setDay("M");
		int violation = Constraints.calcSoftThree(listofLecs, session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 50, violation);
	}
	
	@Test
	public void SoftCon3passtest() {
		session1.setDay("M");
		Time time = new Time.Builder("9:00").build();
		session1.setTime(time);
		lecture1.setSession(session1);
		List<Lecture> listofLecs = new ArrayList<Lecture>();
		listofLecs.add(lecture1);
		
		Lecture lecture2 = new Lecture("CPSC433", "L02");
		session2.setTime(time);
		session2.setDay("M");
		int violation = Constraints.calcSoftThree(listofLecs, session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation);
	}
	
	@Test
	public void SoftCon4failtest() {
		Student student = new Student("Bob");
		lecture1.setExamLength(3);
		session1.setLength(3);
		session1.setDay("M");
		lecture1.setSession(session1);
		//lecture1.enrollStudent(student);
		student.enroll(lecture1);
		
		session2.setLength(3);
		session2.setDay("M");
		//lecture2.enrollStudent(student);
		student.enroll(lecture2);

		
		int violation = Constraints.calcSoftFour(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 50, violation);
	}
	
	@Test
	public void SoftCon4passtest() {
		Student student = new Student("Bob");
		lecture1.setExamLength(3);
		session1.setLength(3);
		session1.setDay("M");
		lecture1.setSession(session1);
		//lecture1.enrollStudent(student);
		student.enroll(lecture1);
		
		
		session2.setLength(2);
		session2.setDay("M");
		//lecture2.enrollStudent(student);
		student.enroll(lecture2);

		int violation = Constraints.calcSoftFour(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation); 
	}
	
	@Test
	public void SoftCon5failtest(){
		Student student = new Student("Bob");
		Time time = new Time.Builder("8:00").build();
		session1.setTime(time);
		lecture1.setExamLength(3);
		session1.setLength(3);
		session1.setDay("M");
		//lecture1.enrollStudent(student);
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		Time time2 = new Time.Builder("11:00").build();
		
		session2.setTime(time2);
		lecture2.setExamLength(3);
		session2.setLength(3);
		session2.setDay("M");
		//lecture2.enrollStudent(student);
		student.enroll(lecture2);
		
		int violation = Constraints.calcSoftFive(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 50, violation);
	}
	
	@Test
	public void SoftCon5passtest(){
		Student student = new Student("Bob");
		Time time = new Time.Builder("8:00").build();
		session1.setTime(time);
		lecture1.setExamLength(3);
		session1.setLength(3);
		session1.setDay("M");
		//lecture1.enrollStudent(student);
		lecture1.setSession(session1);
		student.enroll(lecture1);
		
		Time time2 = new Time.Builder("12:00").build();
		
		session2.setTime(time2);
		lecture2.setExamLength(3);
		session2.setLength(3);
		session2.setDay("M");
		//lecture2.enrollStudent(student);
		student.enroll(lecture2);
		
		int violation = Constraints.calcSoftFive(session2, lecture2);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation);
	}
	
	
	@Test
	public void SoftCon7failtest() {
		session1.setLength(3);
		lecture1.setExamLength(2);	
		int violation = Constraints.calcSoftSeven(session1, lecture1);
		Assert.assertEquals("Soft constraint violation is incorrect", 5, violation);
	}
	
	@Test
	public void SoftCon7passtest() {
		session1.setLength(3);
		lecture1.setExamLength(3);	
		int violation = Constraints.calcSoftSeven(session1, lecture1);
		Assert.assertEquals("Soft constraint violation is incorrect", 0, violation);
	}
	
	//Assignment assignment2 = new Assignment();
}
