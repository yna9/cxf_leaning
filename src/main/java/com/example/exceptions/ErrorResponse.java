package com.example.exceptions;

public class ErrorResponse {
	
	private final int errorCode;
	private final String reason;
	
	ErrorResponse(int errorCode, String reason){
		this.errorCode = errorCode;
		this.reason = reason;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getReason() {
		return reason;
	}


}
