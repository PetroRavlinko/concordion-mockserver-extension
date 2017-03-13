package com.ravlinko.concordion.extension.mockserver.tag;


import com.cedarsoftware.util.io.JsonWriter;
import com.ravlinko.concordion.extension.mockserver.utils.JsonPrettyPrinter;

import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.springframework.stereotype.Component;

import static org.mockserver.model.StringBody.exact;

@Component
public class RequestBodyTag extends MockServerTag {
	private static final String MOCK_REQUEST_BODY_VARIABLE = "#requestBody";

	public RequestBodyTag() {
		setName("body");
		setHttpName("pre");
	}

	@Override
	public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
		Element element = commandCall.getElement();
		element.addStyleClass("json");

		String body = new JsonPrettyPrinter().prettyPrint(element.getText());
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

	}

}