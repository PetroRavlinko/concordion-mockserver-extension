package com.ravlinko.concordion.extension.mockserver.drivers;


import org.concordion.api.Evaluator;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

public class MockServerDriver {
	private static final String MOCK_EXECUTOR_VARIABLE = "#mock";

	private MockServerClient mockServerClient;

	private MockServerDriver(final String host, final int port) {
		mockServerClient = new MockServerClient(host, port);
	}

	public static MockServerDriver newExecutor(final Evaluator evaluator, final String host, final int port) {
		MockServerDriver initializer = new MockServerDriver(host, port);
		evaluator.setVariable(MOCK_EXECUTOR_VARIABLE, initializer);
		return initializer;
	}

	public static MockServerDriver fromEvaluator(Evaluator evaluator) {
		return (MockServerDriver) evaluator.getVariable(MOCK_EXECUTOR_VARIABLE);
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

	public MockServerDriver httpRequest(final HttpRequest httpRequest) {
		this.httpRequest = httpRequest;
		return this;
	}

	public MockServerDriver httpResponse(final HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
		return this;
	}
}
