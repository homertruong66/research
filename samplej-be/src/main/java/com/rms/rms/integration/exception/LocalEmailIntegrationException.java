package com.rms.rms.integration.exception;

/**
 * homertruong
 */

public class LocalEmailIntegrationException extends Exception {

	private Exception detailedException;

	public LocalEmailIntegrationException(Exception detailedException) {
		super(detailedException);

		this.detailedException = detailedException;
	}

    public Exception getDetailedException() {
        return detailedException;
    }

    public void setDetailedException(Exception detailedException) {
        this.detailedException = detailedException;
    }

    @Override
    public String getMessage() {
        return detailedException.getMessage();
    }
}
