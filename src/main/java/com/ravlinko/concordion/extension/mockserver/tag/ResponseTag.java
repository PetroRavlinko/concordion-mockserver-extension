package com.ravlinko.concordion.extension.mockserver.tag;

import com.ravlinko.concordion.extension.mockserver.executors.MockExecutor;

import org.concordion.api.CommandCall;
import org.concordion.api.CommandCallList;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.mockserver.model.HttpResponse;
import org.springframework.stereotype.Component;

import static org.mockserver.model.HttpResponse.response;

@Component
public class ResponseTag extends MockServerTag {
	private static final String MOCK_RESPONSE_VARIABLE = "#response";

	private HttpResponse httpResponse;

	public ResponseTag() {
		setName("response");
	}

	@Override
	public void setUp(final CommandCall commandCall, final Evaluator evaluator, final ResultRecorder resultRecorder) {
		httpResponse = response();
		evaluator.setVariable(MOCK_RESPONSE_VARIABLE, httpResponse);

		CommandCallList childCommands = commandCall.getChildren();
		childCommands.setUp(evaluator, resultRecorder);
		childCommands.execute(evaluator, resultRecorder);

		MockExecutor mockExecutor = MockExecutor.fromEvaluator(evaluator);
		mockExecutor.httpResponse(httpResponse);

		childCommands.verify(evaluator, resultRecorder);
	}

	public static HttpResponse responseFromEvaluator(Evaluator evaluator) {
		return (HttpResponse) evaluator.getVariable(MOCK_RESPONSE_VARIABLE);
	}
}
