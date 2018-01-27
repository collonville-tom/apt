package org.tc.osgi.bundle.apt.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.tc.osgi.bundle.apt.io.exception.AptConnectorException;
import org.tc.osgi.bundle.apt.io.interf.model.AptType;
import org.tc.osgi.bundle.apt.io.interf.model.IAptObject;
import org.tc.osgi.bundle.apt.io.model.AptObject;
import org.tc.osgi.bundle.apt.io.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.apt.io.visitor.decoder.AptDecoder;
import org.tc.osgi.bundle.apt.io.visitor.encoder.AptEncoder;

/**
 * AptConnector.java.
 *
 * @author Collonville Thomas
 * @version 0.0.1
 */
public class AptConnector {

	/**
	 * List<AbstractAptObject> document.
	 */
	private List<IAptObject> document = null;

	/**
	 * File file.
	 */
	private File file = null;

	/**
	 * AptConnector constructor.
	 *
	 * @param filePath
	 *            String
	 * @throws AptConnectorException
	 */
	public AptConnector(final String filePath) {

		file = new File(filePath);
		LoggerServiceProxy.getInstance().getLogger(AptConnector.class)
				.debug("Creation du connecteur apt for" + file.getAbsolutePath());

	}

	/**
	 * extractAptObject.
	 *
	 * @param aptObject
	 *            AbstractAptObject
	 * @return List<AbstractAptObject>
	 */
	protected List<IAptObject> extractAptObject(final IAptObject aptObject) {
		final List<IAptObject> listObj = new ArrayList<IAptObject>();
		listObj.add(aptObject);
		for (final IAptObject obj : aptObject.getListOfAptElement()) {
			listObj.addAll(extractAptObject(obj));
		}
		return listObj;
	}

	/**
	 * getDocument.
	 *
	 * @return List<AbstractAptObject>
	 */
	public List<IAptObject> getDocument() {
		return document;
	}

	/**
	 * processAptFile.
	 *
	 * @return List<AbstractAptObject>
	 * @throws IOException
	 * @throws AptConnectorException
	 */
	public List<IAptObject> processAptFile() throws AptConnectorException {
		final List<String> l = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			if (file.exists() && file.isFile()) {
				while (reader.ready()) {
					l.add(reader.readLine());
				}
			}
		} catch (IOException e) {
			throw new AptConnectorException("File apt does not exist", e);
		}
		return this.processAptFile(l);

	}

	/**
	 * processAptFile.
	 * 
	 * @param l
	 *            List<String>
	 * @return List<AptObject>
	 */
	public List<IAptObject> processAptFile(final List<String> l) {

		final IAptObject aptFile = new AptObject(AptType.FILE, l);
		aptFile.accept(new AptDecoder());

		return extractAptObject(aptFile);

	}

	/**
	 * saveAptFile.
	 * 
	 * @param aptObject
	 *            AptObject
	 * @throws IOException
	 */
	public void saveAptFile(final IAptObject aptObject) throws IOException {
		final AptEncoder encod = new AptEncoder();
		aptObject.accept(encod);
		LoggerServiceProxy.getInstance().getLogger(AptEncoder.class).debug(encod.getBuff().toString());
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
		{
			writer.write(encod.getBuff().toString());
		}
	}

	/**
	 * setDocument.
	 *
	 * @param document
	 *            List<AptObject>
	 */
	public void setDocument(final List<IAptObject> document) {
		this.document = document;
	}

}
