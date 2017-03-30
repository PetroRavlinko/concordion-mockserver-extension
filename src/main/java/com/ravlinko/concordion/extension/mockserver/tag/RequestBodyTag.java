package com.ravlinko.concordion.extension.mockserver.tag;


import com.cedarsoftware.util.io.JsonWriter;
import com.ravlinko.concordion.extension.mockserver.utils.JsonPrettyPrinter;

import org.apache.commons.io.IOUtils;
import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockserver.model.StringBody.exact;

@Component
public class RequestBodyTag extends MockServerTag {
	private static final String MOCK_REQUEST_BODY_VARIABLE = "#requestBody";

	public RequestBodyTag() {
		setName("body");
	}

	@Override
	public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
		Element element = commandCall.getElement();
		String resource = element.getAttributeValue("resource");
		String body = null;
		if (resource != null) {
			try {
				body = IOUtils.toString(Files.newInputStream(Paths.get(ClassLoader.getSystemResource(resource).toURI())));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			body = new JsonPrettyPrinter().prettyPrint(element.getText());
		}
		element.appendText(body);
		evaluator.setVariable(MOCK_REQUEST_BODY_VARIABLE, body);

		RequestTag.requestFromEvaluator(evaluator).withBody(exact(body));
	}

	@Override
	public void verify(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
		Element element = commandCall.getElement();

		String body = JsonWriter.formatJson((String) evaluator.getVariable(MOCK_REQUEST_BODY_VARIABLE));
		element.moveChildrenTo(new Element("span"));
		element.appendText(body);
		super.verify(commandCall, evaluator, resultRecorder);
	}

}