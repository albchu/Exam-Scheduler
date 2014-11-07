package main.examSchedule.assignment;

import java.util.HashMap;

import main.examSchedule.course.CourseLecturePair;
import main.examSchedule.exceptions.DuplicateSessionException;
import main.examSchedule.exceptions.ElementDoesNotExistException;

public class StudentMap
{
	private HashMap<String, Student> studentMap;
	
	public StudentMap()
	{
		studentMap = new HashMap<String, Student>();
	}
	
	public Student getStudent(String studentID)
	{
		if(!studentMap.containsKey(studentID)) throw new ElementDoesNotExistException("Could not find student in student map");
		return studentMap.get(studentID);
	}
	
	public void addStudent(String studentID)
	{
		if(studentMap.containsKey(studentID)) throw new DuplicateSessionException("student has already been added in student map");
		studentMap.put(studentID, new Student(studentID));
	}
	
	public void enroll(String studentID, CourseLecturePair courseLecturePair)
	{
		getStudent(studentID).enroll(courseLecturePair);
	}
}