package com.ravlinko.concordion.extension.mockserver.listener;

import com.ravlinko.concordion.extension.mockserver.tag.MockServerTag;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

import org.concordion.api.listener.DocumentParsingListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MockServerParsingListener implements DocumentParsingListener {
	@Value("${mockserver.extension.ns}")
	private String mockserverExtensionNs;
	@Autowired
	private List<MockServerTag> commands;

	@Override
	public void beforeParsing(Document document) {
		visit(document.getRootElement());
	}

	private void visit(Element elem) {
		Elements children = elem.getChildElements();

		for (int i = 0; i < children.size(); i++) {
			visit(children.get(i));
		}

		if (mockserverExtensionNs.equals(elem.getNamespaceURI())) {
			String localName = translateTag(elem.getLocalName());
			if (localName.isEmpty()) {
				elem.getParent().removeChild(elem);
			} else {
				Attribute attr = new Attribute(elem.getLocalName(), "");
				attr.setNamespace("ms", mockserverExtensionNs);
				elem.addAttribute(attr);
				elem.setNamespacePrefix("");
				elem.setNamespaceURI(null);
				elem.setLocalName(localName);
			}
		}
	}

	private String translateTag(final String localName) {
		for (MockServerTag mst : commands) {
			if (localName.equalsIgnoreCase(mst.getName())) {
				return mst.getHttpName();
			}
		}
		return localName;
	}


}
