package com.ravlinko.concordion.extension.mockserver.tag;

import nu.xom.Attribute;
import nu.xom.Element;

import org.concordion.api.AbstractCommand;
import org.concordion.api.CommandCall;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import java.util.Optional;

public abstract class MockServerTag extends AbstractCommand {
	private static final String WRAP_TAG_PROPERTY_KEY = "mockserver.command.%s.wrap.tag";
	private static final String COMMAND_BEFORE_TEXT_PROPERTY_KEY = "mockserver.command.%s.before.text";
	private static final String COMMAND_AFTER_TEXT_PROPERTY_KEY = "mockserver.command.%s.after.text";
	private static final String COMMAND_HTML_TAG = "mockserver.command.%s.htmltag";

	@Autowired
	private Environment env;

	@Value("${mockserver.extension.ns}")
	private String mockserverExtensionNs;

	private String name;
	private String htmlName;

	@Override
	public void verify(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
		final org.concordion.api.Element element = commandCall.getElement();
		Optional.ofNullable(getWrapTagProperty()).ifPresent(tag -> {
			if (tag.isEmpty()) return;
			org.concordion.api.Element wrapperElement = new org.concordion.api.Element(tag);
			wrapperElement.appendText(getBeforeTextProperty());
			element.appendSister(wrapperElement);

			element.getParentElement().removeChild(element);
			wrapperElement.appendChild(element);
			wrapperElement.appendText(getAfterTextProperty());
		});
	}

	public String getName() {
		return name;
	}

	public MockServerTag setName(final String name) {
		this.name = name;
		return this;
	}

	private String getHtmlName() {
		if (htmlName == null) {
			htmlName = Optional.ofNullable(env.getProperty(getPropertyKeyForCommand(COMMAND_HTML_TAG))).orElse("");
		}
		return htmlName;
	}

	public void beforeParsing(final Element element) {
		if (getHtmlName().isEmpty()) {
			element.getParent().removeChild(element);
		} else {
			Attribute attr = new Attribute(element.getLocalName(), "");
			attr.setNamespace("ms", mockserverExtensionNs);
			element.addAttribute(attr);
			element.setNamespacePrefix("");
			element.setNamespaceURI(null);
			element.setLocalName(getHtmlName());
		}
	}

	private String getBeforeTextProperty() {
		return env.getProperty(getPropertyKeyForCommand(COMMAND_BEFORE_TEXT_PROPERTY_KEY));
	}

	private String getAfterTextProperty() {
		return env.getProperty(getPropertyKeyForCommand(COMMAND_AFTER_TEXT_PROPERTY_KEY));
	}

	private String getWrapTagProperty() {
		return env.getProperty(getPropertyKeyForCommand(WRAP_TAG_PROPERTY_KEY));
	}

	private String getPropertyKeyForCommand(final String keyTemplate) {
		return String.format(keyTemplate, getName().toLowerCase());
	}
}
