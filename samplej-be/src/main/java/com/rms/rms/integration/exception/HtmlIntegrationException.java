package com.rms.rms.integration.exception;

/**
 * homertruong
 */

public class HtmlIntegrationException extends Exception {

	private Exception detailedException;

	public HtmlIntegrationException(Exception detailedException) {
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
