package com.mbronshteyn.gameserver.exception;

public class GameServerException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private int errorStatus;
	
	public GameServerException(String message, int errorStatus) {
		super(message);
		this.message = message;
		this.errorStatus = errorStatus;
	}

	public GameServerException(String message2) {
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorStatus() {
		return errorStatus;
	}

	public void setErrorStatus(int errorStatus) {
		this.errorStatus = errorStatus;
	}
	

}
