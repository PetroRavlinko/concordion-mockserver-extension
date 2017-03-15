package com.ravlinko.concordion.extension.mockserver.tag;

import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.mockserver.model.Header;
import org.springframework.stereotype.Component;

@Component
public class HeaderTag extends MockServerTag {
	public HeaderTag() {
		setName("header");
		setHttpName("code");
	}

	@Override
	public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
		Element element = commandCall.getElement();
		String name = element.getAttributeValue("name");
		String value = element.getText();
		ResponseTag.responseFromEvaluator(evaluator).withHeader(new Header(name, value));
	}

}
