package com.ravlinko.concordion.extension.mockserver.tag;

import com.cedarsoftware.util.io.JsonWriter;
import com.ravlinko.concordion.extension.mockserver.utils.XmlPrettyPrinter;

import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.springframework.stereotype.Component;

import static org.mockserver.model.StringBody.exact;

@Component
public class ResponseBodyTag extends MockServerTag {
	private static final String MOCK_RESPONSE_BODY_VARIABLE = "#responseBody";

	public ResponseBodyTag() {
		setName("responseBody");
		setHttpName("pre");
	}

	@Override
	public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
		Element element = commandCall.getElement();
		String style = element.getAttributeValue("class");
		String body = "";
		if (style.equalsIgnoreCase("xml") && !element.toXML().isEmpty()) {
			for (Element e : element.getChildElements()) {
				body = body.concat(e.toXML());
			}
			body = new XmlPrettyPrinter().prettyPrint(body);
			evaluator.setVariable("#responseBodyFormat", "xml");
		} else {
			body = JsonWriter.formatJson(element.getText());
			evaluator.setVariable("#responseBodyFormat", "json");
		}
		evaluator.setVariable(MOCK_RESPONSE_BODY_VARIABLE, body);
		ResponseTag.responseFromEvaluator(evaluator).withBody(exact(body));
	}

	@Override
	public void verify(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
		String body = (String) evaluator.getVariable(MOCK_RESPONSE_BODY_VARIABLE);
		Element element = commandCall.getElement();
		element.moveChildrenTo(new Element("span"));
		element.appendText(body);
	}
}
