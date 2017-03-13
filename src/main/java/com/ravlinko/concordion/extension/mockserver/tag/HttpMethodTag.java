package com.ravlinko.concordion.extension.mockserver.tag;

import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;

public class HttpMethodTag extends MockServerTag {
	private final String method;

	public HttpMethodTag(final String method) {
		this.method = method;
		setName(method);
		setHttpName("code");
	}

	@Override
	public void setUp(final CommandCall commandCall, final Evaluator evaluator, final ResultRecorder resultRecorder) {
		Element element = commandCall.getElement();
		element.addStyleClass(method.toLowerCase());
		String path = element.getText();
		RequestTag.requestFromEvaluator(evaluator)
				.withMethod(method)
				.withPath(path);
	}

}
