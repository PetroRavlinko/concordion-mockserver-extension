package com.ravlinko.concordion.extension.mockserver.command;

import org.concordion.api.AbstractCommand;
import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.concordion.api.listener.ExecuteEvent;
import org.concordion.api.listener.ExecuteListener;
import org.concordion.internal.util.Announcer;

public class HttpMethodCommand extends MockServerCommand {
	private Announcer<ExecuteListener> listeners = Announcer.to(ExecuteListener.class);

	private final String method;

	public HttpMethodCommand(final String method) {
		this.method = method;
		setName(method);
	}

	@Override
	public void setUp(final CommandCall commandCall, final Evaluator evaluator, final ResultRecorder resultRecorder) {
		Element element = commandCall.getElement();
		element.addStyleClass(method.toLowerCase());
		String path = element.getText();
		RequestCommand.requestFromEvaluator(evaluator)
				.withMethod(method)
				.withPath(path);

		listeners.announce().executeCompleted(new ExecuteEvent(element));
	}

}
