package com.ravlinko.concordion.extension.mockserver.tag;

import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockserver.model.HttpRequest;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpMethodTagTest {
	private static final String MOCK_REQUEST_VARIABLE = "#request";
	private static final String GET = "GET";
	private static final String TEXT = "/info";

	@Mock
	private CommandCall commandCall;
	@Mock
	private Evaluator evaluator;
	@Mock
	private ResultRecorder resultRecorder;
	@Mock
	private HttpRequest request;

	private nu.xom.Element xElement = new nu.xom.Element("get");
	private Element element = new Element(xElement);

	@Test
	public void shouldAddRequestMethod() {
		HttpMethodTag unit = new HttpMethodTag(GET);
		xElement.appendChild(TEXT);

		when(commandCall.getElement()).thenReturn(element);
		when(request.withMethod(eq(GET))).thenReturn(request);
		when(evaluator.getVariable(MOCK_REQUEST_VARIABLE)).thenReturn(request);

		unit.setUp(commandCall, evaluator, resultRecorder);

		verify(request).withMethod(GET);
		verify(request).withPath(TEXT);
	}
}