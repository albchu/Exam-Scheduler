package examSchedule.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import examSchedule.exceptions.ElementDoesNotExistException;

public class CourseMap
{
	private HashMap<String, List<CourseLecturePair>> courseMap;

	public CourseMap()
	{
		courseMap = new HashMap<String, List<CourseLecturePair>>();
	}
	
	/**
	 * Adds a course with an uninitialized list of CourseLecturePairs only if the course key does not exist in the map
	 * @param courseName
	 */
	public void addCourse(String courseName)
	{
		if(!courseMap.containsKey(courseName))
			courseMap.put(courseName, new ArrayList<CourseLecturePair>());
	}
	
	public void addLecture(String courseName, String lectureName)
	{
		if(!courseMap.containsKey(courseName)) //throw new ElementDoesNotExistException("Could not find course: " + courseName + " in course map");
			addCourse(courseName);
		courseMap.get(courseName).add(new CourseLecturePair(courseName, lectureName));
	}
	
	/**
	 * Returns the course lecture pair corresponding to the course and lecture names
	 * @param courseName
	 * @param lectureName
	 * @return
	 */
	public CourseLecturePair getCourseLecturePair(String courseName, String lectureName)
	{
		if(!courseMap.containsKey(courseName)) //throw new ElementDoesNotExistException("Could not find course: " + courseName + " in course map");
			addCourse(courseName);
//		System.out.println("Searching in course: " + courseName);
		for (CourseLecturePair pair : courseMap.get(courseName))
		{
			if (pair.getLecture().getLectureName().equals(lectureName))
				return pair;
		}
		addLecture(courseName, lectureName);
		return getCourseLecturePair(courseName, lectureName);	//TODO: this is ugly, fucking fix this albert. dont let it stay recursion
	}
	
	public List<Lecture> getLectures(String courseName)
	{
		if(!courseMap.containsKey(courseName)) throw new ElementDoesNotExistException("Could not find course in course map");
		List<Lecture> lectures = new ArrayList<Lecture>();
		for (CourseLecturePair pair : courseMap.get(courseName))
			lectures.add(pair.getLecture());
		return lectures;
	}
	
	public void updateExamLength(String courseName, String lectureName, int examLength)
	{
		getCourseLecturePair(courseName, lectureName).getLecture().setExamLength(examLength);
	}
	
	public void updateInstructor(String courseName, String lectureName, String instructor)
	{
		getCourseLecturePair(courseName, lectureName).getLecture().setInstructor(instructor);
	}
	
	public void incrementLectureSize(String courseName, String lectureName)
	{
		getCourseLecturePair(courseName, lectureName).getLecture().incrementClassSize();
	}

	public HashMap<String, List<CourseLecturePair>> getCourseMap()
	{
		return courseMap;
	}
	
}