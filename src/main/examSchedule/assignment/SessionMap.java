package main.examSchedule.assignment;

import java.util.HashMap;
import java.util.List;

import main.examSchedule.exceptions.DuplicateSessionException;
import main.examSchedule.exceptions.ElementDoesNotExistException;


public class SessionMap
{
	private HashMap<String, Session> sessionMap;
	
	public SessionMap()
	{
		sessionMap = new HashMap<String, Session>();
	}
	
	public Session getSession(String sessionID)
	{
		if(!sessionMap.containsKey(sessionID)) throw new ElementDoesNotExistException("Could not find session in session map");
		return sessionMap.get(sessionID);
	}
	
	public void addSession(String sessionID)
	{
		if(sessionMap.containsKey(sessionID)) throw new DuplicateSessionException("Session has already been added in session map");
		sessionMap.put(sessionID, new Session(sessionID));
	}
}
