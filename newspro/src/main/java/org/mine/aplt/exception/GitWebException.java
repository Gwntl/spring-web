package org.mine.aplt.exception;

public class GitWebException extends MineException{

	private static final long serialVersionUID = 709133615026825495L;

	public GitWebException(String message) {
		super(message);
	}
	
	public static MineException GIT0001(String errmsg){
		return newException(errmsg);
	}
	
}
