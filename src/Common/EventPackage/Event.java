/**
 * Copyright(c) 2018 All rights reserved by JU Consulting
 */

package Common.EventPackage;

import java.io.Serializable;

/**
 * @author Jungho Kim
 * @date 2018
 * @version 1.1
 * @description
 */
public class Event implements Serializable {
    private static final long serialVersionUID = 1L; //Default serializable vale
    
    private String message;
	private EventId eventId;

	public Event(EventId id, String text ) {
		this.message = text;
		this.eventId = id;
	}

	public Event(EventId id ) {
		this.message = null;
		this.eventId = id;
	}
	
	public EventId getEventId() {
		return eventId;
	}
	
	public String getMessage() {
		return message;
	}

}