package com.ravlinko.concordion.extension.mockserver.executors;


import com.ravlinko.concordion.extension.mockserver.MockServerExtension.Config;

import org.concordion.api.Evaluator;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

public class InitializationExecutor {
	private static final String MOCK_EXECUTOR_VARIABLE = "#mock";

	private MockServerClient mockServerClient;
	private Config config;

	private InitializationExecutor(final Config config) {
		this.config = config;
		mockServerClient = new MockServerClient(this.config.getHost(), this.config.getPort());
	}

	public static InitializationExecutor newExecutor(final Evaluator evaluator, final Config config) {
		InitializationExecutor initializer = new InitializationExecutor(config);
		evaluator.setVariable(MOCK_EXECUTOR_VARIABLE, initializer);
		return initializer;
	}

	public static InitializationExecutor fromEvaluator(Evaluator evaluator) {
		return (InitializationExecutor) evaluator.getVariable(MOCK_EXECUTOR_VARIABLE);
	}

	private HttpRequest httpRequest;
	private HttpResponse httpResponse;

	public void execute() {
		mockServerClient
				.when(httpRequest)
				.respond(httpResponse);

	}

	public InitializationExecutor httpRequest(final HttpRequest httpRequest) {
		this.httpRequest = httpRequest;
		return this;
	}

	public InitializationExecutor httpResponse(final HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
		return this;
	}
}
