package com.ravlinko.concordion.extension.mockserver.executors;


import org.concordion.api.Evaluator;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

public class MockExecutor {
	private static final String MOCK_EXECUTOR_VARIABLE = "#mock";

	private MockServerClient mockServerClient;

	private MockExecutor(final String host, final int port) {
		mockServerClient = new MockServerClient(host, port);
	}

	public static MockExecutor newExecutor(final Evaluator evaluator, final String host, final int port) {
		MockExecutor initializer = new MockExecutor(host, port);
		evaluator.setVariable(MOCK_EXECUTOR_VARIABLE, initializer);
		return initializer;
	}

	public static MockExecutor fromEvaluator(Evaluator evaluator) {
		return (MockExecutor) evaluator.getVariable(MOCK_EXECUTOR_VARIABLE);
	}

	private HttpRequest httpRequest;
	private HttpResponse httpResponse;

	public void execute(final boolean reset) {
		if (reset) {
			mockServerClient.reset();
		}
		mockServerClient
				.when(httpRequest)
				.respond(httpResponse);
	}

	public MockExecutor httpRequest(final HttpRequest httpRequest) {
		this.httpRequest = httpRequest;
		return this;
	}

	public MockExecutor httpResponse(final HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
		return this;
	}
}
