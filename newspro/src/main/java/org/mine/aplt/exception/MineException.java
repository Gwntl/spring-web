package org.mine.aplt.exception;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class MineException extends RuntimeException{
	
	private static final long serialVersionUID = 11745362295480131L;

	public MineException(String message){
		super(message);
	}
	
	public static MineException newException(String message){
		return new MineException(message);
	}
	
	public static String getStackTrace(Throwable e){
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			e.printStackTrace(new PrintWriter(stream, true));
			String stack = stream.toString();
			stream.close();
			return stack;
		} catch (IOException e1) {
		}
		return null;
	}
}
