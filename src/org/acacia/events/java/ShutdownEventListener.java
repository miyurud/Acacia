package org.acacia.events.java;

import java.util.EventListener;

public interface ShutdownEventListener extends EventListener{
	public void shutdownEventOccurred(ShutdownEvent evt);
}