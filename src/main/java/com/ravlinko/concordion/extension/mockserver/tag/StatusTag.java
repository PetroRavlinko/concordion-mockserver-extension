package com.ravlinko.concordion.extension.mockserver.tag;

import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.springframework.stereotype.Component;

@Component
public class StatusTag extends MockServerTag {

	public StatusTag() {
		setName("statusCode");
	}

	@Override
	public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
		Element element = commandCall.getElement();
		String value = element.getText();
		ResponseTag.responseFromEvaluator(evaluator).withStatusCode(Integer.parseInt(value));
	}

}
