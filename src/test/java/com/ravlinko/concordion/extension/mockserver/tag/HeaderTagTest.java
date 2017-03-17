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
import org.mockserver.model.Header;
import org.mockserver.model.HttpResponse;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HeaderTagTest {
	private static final String MOCK_RESPONSE_VARIABLE = "#response";
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String NAME = "name";
	private static final String TEXT_JSON = "text/json";

	@Mock
	private CommandCall commandCall;
	@Mock
	private Evaluator evaluator;
	@Mock
	private ResultRecorder resultRecorder;

	@Mock
	private HttpResponse response;

	private nu.xom.Element xElement = new nu.xom.Element("header");

	private Element element = new Element(xElement);

	private HeaderTag unit = new HeaderTag();

	@Test
	public void shouldSetHeaderToResponse() throws Exception {
		Attribute attribute = new Attribute(NAME, CONTENT_TYPE);
		xElement.addAttribute(attribute);
		xElement.appendChild(TEXT_JSON);

		when(commandCall.getElement()).thenReturn(element);
		when(evaluator.getVariable(MOCK_RESPONSE_VARIABLE)).thenReturn(response);

		unit.setUp(commandCall, evaluator, resultRecorder);

		verify(response).withHeader(new Header(CONTENT_TYPE, TEXT_JSON));
	}

}