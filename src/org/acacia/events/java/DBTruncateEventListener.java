package org.acacia.events.java;

import java.util.EventListener;

public interface DBTruncateEventListener extends EventListener{
	public void truncateEventOccurred(DBTruncateEvent evt);
}