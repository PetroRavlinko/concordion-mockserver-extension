package com.ravlinko.concordion.extension.mockserver.command;


import com.cedarsoftware.util.io.JsonWriter;

import org.concordion.api.AbstractCommand;
import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;

import static org.mockserver.model.StringBody.exact;

public class RequestBodyCommand extends MockServerCommand {
	private static final String MOCK_REQUEST_BODY_VARIABLE = "#requestBody";

	public RequestBodyCommand() {
		setName("body");
	}

	@Override
	public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
		Element element = commandCall.getElement();
		element.addStyleClass("json");

		String body = element.getText();
		element.appendText(body);
		evaluator.setVariable(MOCK_REQUEST_BODY_VARIABLE, body);

		RequestCommand.requestFromEvaluator(evaluator).withBody(exact(body));
	}

	@Override
	public void verify(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
		Element element = commandCall.getElement();

		String body = JsonWriter.formatJson((String) evaluator.getVariable(MOCK_REQUEST_BODY_VARIABLE));
		element.moveChildrenTo(new Element("span"));
		element.appendText(body);

	}

}