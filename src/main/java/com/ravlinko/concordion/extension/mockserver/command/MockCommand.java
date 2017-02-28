package com.ravlinko.concordion.extension.mockserver.command;

import com.ravlinko.concordion.extension.mockserver.MockServerExtension.Config;
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

public class MockCommand extends AbstractCommand {
	private Announcer<ExecuteListener> listeners = Announcer.to(ExecuteListener.class);

	private Config config;

	public MockCommand(final Config config) {
		this.config = config;
	}

	@Override
	public void execute(final CommandCall commandCall, final Evaluator evaluator, final ResultRecorder resultRecorder) {
		InitializationExecutor initializer = InitializationExecutor.newExecutor(evaluator, this.config);

		CommandCallList childCommands = commandCall.getChildren();
		childCommands.setUp(evaluator, resultRecorder);
		childCommands.execute(evaluator, resultRecorder);

		initializer.execute();

		announceExecuteCompleted(commandCall.getElement());
		childCommands.verify(evaluator, resultRecorder);
	}

	private void announceExecuteCompleted(Element element) {
		listeners.announce().executeCompleted(new ExecuteEvent(element));
	}
}
