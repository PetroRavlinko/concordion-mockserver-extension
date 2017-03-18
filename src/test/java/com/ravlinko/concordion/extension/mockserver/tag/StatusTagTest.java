package com.ravlinko.concordion.extension.mockserver.tag;

import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockserver.model.HttpResponse;
import org.springframework.core.env.Environment;

import static org.mockito.Matchers.eq;
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
	@Mock
	private Environment env;

	private nu.xom.Element xElement = new nu.xom.Element("statusCode");
	private Element element = new Element(xElement);

	@InjectMocks
	private StatusTag unit = new StatusTag();

	@Test
	public void shouldAddResponseStatus() {
		xElement.appendChild(TEXT);
		when(env.getProperty(eq("mockserver.command.statuscode.name"))).thenReturn("statusCode");
		when(env.getProperty(eq("mockserver.command.statuscode.htmltag"))).thenReturn("code");
		when(env.getProperty(eq("mockserver.command.statuscode.before.text"))).thenReturn("Status Code: ");
		when(env.getProperty(eq("mockserver.command.statuscode.after.text"))).thenReturn(";");
		when(env.getProperty(eq("mockserver.command.statuscode.wrap.tag"))).thenReturn("span");
		when(commandCall.getElement()).thenReturn(element);
		when(evaluator.getVariable(MOCK_RESPONSE_VARIABLE)).thenReturn(response);

		unit.setUp(commandCall, evaluator, resultRecorder);

		verify(response).withStatusCode(200);
	}
}