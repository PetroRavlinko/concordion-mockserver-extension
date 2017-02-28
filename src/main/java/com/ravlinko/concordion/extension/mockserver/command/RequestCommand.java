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
import org.mockserver.model.HttpRequest;

import static org.mockserver.model.HttpRequest.*;

public class RequestCommand extends AbstractCommand {
	private static final String MOCK_REQUEST_VARIABLE = "#request";

	private Announcer<ExecuteListener> listeners = Announcer.to(ExecuteListener.class);
	private HttpRequest httpRequest;

	@Override
	public void setUp(final CommandCall commandCall, final Evaluator evaluator, final ResultRecorder resultRecorder) {
		httpRequest = request();
		evaluator.setVariable(MOCK_REQUEST_VARIABLE, httpRequest);

		CommandCallList childCommands = commandCall.getChildren();
		childCommands.setUp(evaluator, resultRecorder);
		childCommands.execute(evaluator, resultRecorder);

		InitializationExecutor initializationExecutor = InitializationExecutor.fromEvaluator(evaluator);
		initializationExecutor.httpRequest(httpRequest);

		announceExecuteCompleted(commandCall.getElement());
		childCommands.verify(evaluator, resultRecorder);
	}

	private void announceExecuteCompleted(Element element) {
		listeners.announce().executeCompleted(new ExecuteEvent(element));
	}

	public static HttpRequest requestFromEvaluator(Evaluator evaluator) {
		return (HttpRequest) evaluator.getVariable(MOCK_REQUEST_VARIABLE);
	}
}
