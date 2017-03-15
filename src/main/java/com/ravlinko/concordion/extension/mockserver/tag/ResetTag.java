package com.ravlinko.concordion.extension.mockserver.tag;

import com.ravlinko.concordion.extension.mockserver.executors.InitializationExecutor;

import org.concordion.api.CommandCall;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.springframework.stereotype.Component;

@Component
public class ResetTag extends MockServerTag {
	public ResetTag() {
		setName("reset");
		setHttpName("");
	}

	@Override
	public void setUp(final CommandCall commandCall, final Evaluator evaluator, final ResultRecorder resultRecorder) {
		InitializationExecutor initializationExecutor = InitializationExecutor.fromEvaluator(evaluator);
		initializationExecutor.reset();
	}
}
