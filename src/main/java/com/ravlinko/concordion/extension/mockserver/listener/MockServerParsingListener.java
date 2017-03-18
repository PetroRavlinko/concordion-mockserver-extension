package com.ravlinko.concordion.extension.mockserver.listener;

import com.ravlinko.concordion.extension.mockserver.tag.MockServerTag;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

import org.concordion.api.listener.DocumentParsingListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MockServerParsingListener implements DocumentParsingListener {
	@Value("${mockserver.extension.ns}")
	private String mockserverExtensionNs;
	@Autowired
	private List<MockServerTag> commands;

	@Override
	public void beforeParsing(Document document) {
		visitBeforeParsing(document.getRootElement());
	}

	private void visitBeforeParsing(Element elem) {
		Elements children = elem.getChildElements();

		for (int i = 0; i < children.size(); i++) {
			visitBeforeParsing(children.get(i));
		}

		if (mockserverExtensionNs.equals(elem.getNamespaceURI())) {
			beforeParsingTag(elem.getLocalName()).ifPresent(mst -> mst.beforeParsing(elem));
		}
	}

	private Optional<MockServerTag> beforeParsingTag(final String localName) {
		for (MockServerTag mst : commands) {
			if (localName.equalsIgnoreCase(mst.getName())) {
				return Optional.ofNullable(mst);
			}
		}
		return Optional.empty();
	}

}
