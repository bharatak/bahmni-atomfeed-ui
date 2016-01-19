package org.bahmni.atomfeed.core.model;

public class FailedEvent {

	private String errorMessage;
	private String errorHashCode;
	private String failedAt;
	private int retries;

	public FailedEvent(String errorMessage, String errorHashCode, String failedAt, int retries) {
		this.errorMessage = errorMessage;
		this.errorHashCode = errorHashCode;
		this.failedAt = failedAt;
		this.retries = retries;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorHashCode() {
		return errorHashCode;
	}

	public String getFailedAt() {
		return failedAt;
	}
}
