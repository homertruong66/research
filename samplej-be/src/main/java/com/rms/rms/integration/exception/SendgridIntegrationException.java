package com.rms.rms.integration.exception;

/**
 * homertruong
 */

public class SendgridIntegrationException extends Exception {

	private Exception detailedException;

	public SendgridIntegrationException(Exception detailedException) {
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
