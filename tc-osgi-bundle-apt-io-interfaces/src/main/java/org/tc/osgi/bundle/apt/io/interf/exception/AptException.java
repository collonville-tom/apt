package org.tc.osgi.bundle.apt.io.interf.exception;

public class AptException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2835361833925902897L;

	/**
	 * AptConnectorException constructor.
	 * @param _message String
	 */
	public AptException(final String _message) {
		super(_message);
	}

	/**
	 * AptConnectorException constructor.
	 * @param _message String
	 * @param _e Throwable
	 */
	public AptException(final String _message, final Throwable _e) {
		super(_message, _e);
	}
}