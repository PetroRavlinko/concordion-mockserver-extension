package com.ravlinko.concordion.extension.mockserver;

import com.ravlinko.concordion.extension.mockserver.command.HttpMethodCommand;
import com.ravlinko.concordion.extension.mockserver.command.MockCommand;
import com.ravlinko.concordion.extension.mockserver.command.MockServerCommand;
import com.ravlinko.concordion.extension.mockserver.command.RequestBodyCommand;
import com.ravlinko.concordion.extension.mockserver.command.RequestCommand;
import com.ravlinko.concordion.extension.mockserver.command.ResponseBodyCommand;
import com.ravlinko.concordion.extension.mockserver.command.ResponseCommand;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;
import org.concordion.api.listener.DocumentParsingListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MockServerExtension implements ConcordionExtension {

	private static final String MOCK_SERVER_EXTENSION_NS = "http://petroravlinko.github.io/concordion-mockserver-extension";

	public static class Config {
		private int port = 1080;
		private String host = "127.0.0.1";

		public int getPort() {
			return port;
		}

		public String getHost() {
			return host;
		}
	}

	private Config config = new Config();

	@Override
	public void addTo(ConcordionExtender concordionExtender) {
		List<MockServerCommand> commands = new LinkedList<>();
		commands.addAll(Arrays.asList(
				new MockCommand(config),
				new RequestCommand(),
				new HttpMethodCommand("GET"),
				new HttpMethodCommand("POST"),
				new RequestBodyCommand(),
				new ResponseCommand(),
				new ResponseBodyCommand()));

		for (MockServerCommand command : commands) {
			concordionExtender.withCommand(MOCK_SERVER_EXTENSION_NS, command.getName(), command);
		}

		concordionExtender.withDocumentParsingListener(new DocumentParsingListener() {

			private Map<String, String> tags = new HashMap<String, String>() {{
				put("mock", "div");
				put("request", "div");
				put("get", "code");
				put("post", "code");
				put("body", "pre");
				put("response", "div");
				put("responseBody", "pre");
			}};

			@Override
			public void beforeParsing(Document document) {
				visit(document.getRootElement());
			}

			private void visit(Element elem) {

				Elements children = elem.getChildElements();
				for (int i = 0; i < children.size(); i++) {
					visit(children.get(i));
				}

				if (MockServerExtension.MOCK_SERVER_EXTENSION_NS.equals(elem.getNamespaceURI())) {
					Attribute attr = new Attribute(elem.getLocalName(), "");
					attr.setNamespace("ms", MOCK_SERVER_EXTENSION_NS);
					elem.addAttribute(attr);
					elem.setNamespacePrefix("");
					elem.setNamespaceURI(null);
					elem.setLocalName(translateTag(elem.getLocalName()));
				}
			}

			private String translateTag(String localName) {
				String name = tags.get(localName);
				return name == null ? localName : name;
			}
		});
	}
}
