package com.ravlinko.concordion.extension.mockserver.utils;

import nu.xom.Builder;
import nu.xom.Serializer;

import java.io.ByteArrayOutputStream;

public class XmlPrettyPrinter {
	private static final int INDENT = 4;
	private static final String LINE_SEPARATOR = "\n";
	private static final String BASE_URI = "com";
	private static final String CHARSET_NAME = "UTF-8";

	public String prettyPrint(final String xml) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Serializer serializer = new Serializer(out);
			serializer.setIndent(INDENT);
			serializer.setLineSeparator(LINE_SEPARATOR);
			serializer.write(new Builder().build(xml, BASE_URI));
			return out.toString(CHARSET_NAME);
		} catch (Exception e) {
			return xml;
		}
	}
}
