package org.tc.osgi.bundle.apt.io.exception;

import org.tc.osgi.bundle.apt.io.interf.exception.AptException;

/**
 * AptConnectorException.java.
 * @author Collonville Thomas
 * @version 0.0.1
 */
public class AptDecoderException extends AptException {

	/**
	 * long serialVersionUID.
	 */
	private static final long serialVersionUID = -621620180673701362L;

	/**
	 * AptConnectorException constructor.
	 * @param _message String
	 */
	public AptDecoderException(final String _message) {
		super(_message);
	}

	/**
	 * AptConnectorException constructor.
	 * @param _message String
	 * @param _e Throwable
	 */
	public AptDecoderException(final String _message, final Throwable _e) {
		super(_message, _e);
	}
}
