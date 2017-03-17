package com.ravlinko.concordion.extension.mockserver.tag;

import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockserver.model.HttpResponse;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatusTagTest {
	private static final String MOCK_RESPONSE_VARIABLE = "#response";
	private static final String TEXT = "200";

	@Mock
	private CommandCall commandCall;
	@Mock
	private Evaluator evaluator;
	@Mock
	private ResultRecorder resultRecorder;
	@Mock
	private HttpResponse response;

	private nu.xom.Element xElement = new nu.xom.Element("statusCode");
	private Element element = new Element(xElement);

	@Test
	public void shouldAddResponseStatus() {
		StatusTag unit = new StatusTag();
		xElement.appendChild(TEXT);

		when(commandCall.getElement()).thenReturn(element);
		when(evaluator.getVariable(MOCK_RESPONSE_VARIABLE)).thenReturn(response);

		unit.setUp(commandCall, evaluator, resultRecorder);

		verify(response).withStatusCode(200);
	}
}