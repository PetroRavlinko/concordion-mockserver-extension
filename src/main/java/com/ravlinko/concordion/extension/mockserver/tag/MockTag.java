package com.ravlinko.concordion.extension.mockserver.tag;

import com.ravlinko.concordion.extension.mockserver.drivers.MockServerDriver;

import org.concordion.api.CommandCall;
import org.concordion.api.CommandCallList;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MockTag extends MockServerTag {
	@Value("${mockserver.server.client.host}")
	private String host;
	@Value("${mockserver.server.client.port}")
	private int port;

	public MockTag() {
		setName("mock");
	}

	@Override
	public void execute(final CommandCall commandCall, final Evaluator evaluator, final ResultRecorder resultRecorder) {
		MockServerDriver initializer = MockServerDriver.newExecutor(evaluator, host, port);

		CommandCallList childCommands = commandCall.getChildren();
		childCommands.setUp(evaluator, resultRecorder);
		childCommands.execute(evaluator, resultRecorder);

		Element element = commandCall.getElement();
		boolean reset = Boolean.parseBoolean(element.getAttributeValue("reset"));

		initializer.execute(reset);

		childCommands.verify(evaluator, resultRecorder);
	}
}
