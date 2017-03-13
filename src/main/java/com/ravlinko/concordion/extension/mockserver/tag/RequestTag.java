package com.ravlinko.concordion.extension.mockserver.tag;

import com.ravlinko.concordion.extension.mockserver.executors.InitializationExecutor;

import org.concordion.api.CommandCall;
import org.concordion.api.CommandCallList;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.mockserver.model.HttpRequest;
import org.springframework.stereotype.Component;

import static org.mockserver.model.HttpRequest.request;

@Component
public class RequestTag extends MockServerTag {
	private static final String MOCK_REQUEST_VARIABLE = "#request";

	private HttpRequest httpRequest;

	public RequestTag() {
		setName("request");
		setHttpName("div");
	}

	@Override
	public void setUp(final CommandCall commandCall, final Evaluator evaluator, final ResultRecorder resultRecorder) {
		httpRequest = request();
		evaluator.setVariable(MOCK_REQUEST_VARIABLE, httpRequest);

		CommandCallList childCommands = commandCall.getChildren();
		childCommands.setUp(evaluator, resultRecorder);
		childCommands.execute(evaluator, resultRecorder);

		InitializationExecutor initializationExecutor = InitializationExecutor.fromEvaluator(evaluator);
		initializationExecutor.httpRequest(httpRequest);

		childCommands.verify(evaluator, resultRecorder);
	}

	public static HttpRequest requestFromEvaluator(Evaluator evaluator) {
		return (HttpRequest) evaluator.getVariable(MOCK_REQUEST_VARIABLE);
	}
}
