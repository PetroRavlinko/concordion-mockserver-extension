package com.ravlinko.concordion.extension.mockserver.command;

import com.ravlinko.concordion.extension.mockserver.executors.InitializationExecutor;

import org.concordion.api.AbstractCommand;
import org.concordion.api.CommandCall;
import org.concordion.api.CommandCallList;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.concordion.api.listener.ExecuteEvent;
import org.concordion.api.listener.ExecuteListener;
import org.concordion.internal.util.Announcer;
import org.mockserver.model.HttpResponse;

import static org.mockserver.model.HttpResponse.response;

public class ResponseCommand extends AbstractCommand {
	private static final String MOCK_RESPONSE_VARIABLE = "#response";

	private Announcer<ExecuteListener> listeners = Announcer.to(ExecuteListener.class);
	private HttpResponse httpResponse;

	@Override
	public void setUp(final CommandCall commandCall, final Evaluator evaluator, final ResultRecorder resultRecorder) {
		httpResponse = response();
		evaluator.setVariable(MOCK_RESPONSE_VARIABLE, httpResponse);

		CommandCallList childCommands = commandCall.getChildren();
		childCommands.setUp(evaluator, resultRecorder);
		childCommands.execute(evaluator, resultRecorder);

		InitializationExecutor initializationExecutor = InitializationExecutor.fromEvaluator(evaluator);
		initializationExecutor.httpResponse(httpResponse);

		announceExecuteCompleted(commandCall.getElement());
		childCommands.verify(evaluator, resultRecorder);
	}

	private void announceExecuteCompleted(Element element) {
		listeners.announce().executeCompleted(new ExecuteEvent(element));
	}

	public static HttpResponse responseFromEvaluator(Evaluator evaluator) {
		return (HttpResponse) evaluator.getVariable(MOCK_RESPONSE_VARIABLE);
	}
}
