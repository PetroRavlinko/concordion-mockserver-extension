package com.ravlinko.concordion.extension.mockserver.tag;


import com.ravlinko.concordion.extension.mockserver.utils.JsonPrettyPrinter;
import com.ravlinko.concordion.extension.mockserver.utils.XmlPrettyPrinter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockserver.model.StringBody.exact;

@Component
public class RequestBodyTag extends MockServerTag {
	@Autowired
	private JsonPrettyPrinter jsonPrettyPrinter;
	@Autowired
	private XmlPrettyPrinter xmlPrettyPrinter;

	public RequestBodyTag() {
		setName("body");
	}

	@Override
	public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
		Element element = commandCall.getElement();
		String resource = element.getAttributeValue("resource");
		String body = element.getText();

		if (resource != null) {
			try {
				body = IOUtils.toString(Files.newInputStream(Paths.get(ClassLoader.getSystemResource(resource).toURI())));
				element.appendText(body);
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
		RequestTag.requestFromEvaluator(evaluator).withBody(exact(body));
	}

	@Override
	public void verify(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
		Element element = commandCall.getElement();
		String body = element.getText();
		String classMarker = element.getAttributeValue("class");
		if (classMarker.contains("xml")) {
			body = xmlPrettyPrinter.prettyPrint(body);
		}
		if (classMarker.contains("json")) {
			body = jsonPrettyPrinter.prettyPrint(body);
			element.appendText(body);
		}

		element.moveChildrenTo(new Element("span"));
		element.appendText(body);

		super.verify(commandCall, evaluator, resultRecorder);
	}

}