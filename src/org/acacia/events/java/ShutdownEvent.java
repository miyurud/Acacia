package org.acacia.events.java;

import java.util.EventObject;

public class ShutdownEvent extends EventObject{

	public ShutdownEvent(Object source) {
		super(source);
	}
	
}