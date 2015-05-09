package org.acacia.csr.java;

public class ZeroFoundException extends InterruptedException{
	public ZeroFoundException(){}
	
	public ZeroFoundException(String message){
		super(message);
	}
}