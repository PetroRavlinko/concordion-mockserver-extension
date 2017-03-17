package com.ravlinko.concordion.extension.mockserver.tag;

import nu.xom.Attribute;

import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockserver.model.HttpRequest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QueryParameterTagTest {
	private static final String MOCK_REQUEST_VARIABLE = "#request";
	private static final String NAME = "name";
	private static final String QTY = "qty";
	private static final String TEXT = "42";

	@Mock
	private CommandCall commandCall;
	@Mock
	private Evaluator evaluator;
	@Mock
	private ResultRecorder resultRecorder;
	@Mock
	private HttpRequest request;

	private nu.xom.Element xElement = new nu.xom.Element("queryParameter");
	private Element element = new Element(xElement);

	@Test
	public void shouldAddQueryParametersToRequest() {
		QueryParameterTag unit = new QueryParameterTag();
		Attribute attribute = new Attribute(NAME, QTY);
		xElement.addAttribute(attribute);
		xElement.appendChild(TEXT);

		when(commandCall.getElement()).thenReturn(element);
		when(evaluator.getVariable(MOCK_REQUEST_VARIABLE)).thenReturn(request);

		unit.setUp(commandCall, evaluator, resultRecorder);

		verify(request).withQueryStringParameter(QTY, TEXT);
	}
}