package com.ravlinko.concordion.extension.mockserver;

import com.ravlinko.concordion.extension.mockserver.tag.MockServerTag;

import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;
import org.concordion.api.listener.DocumentParsingListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MockServerExtension implements ConcordionExtension {
	@Value("${mockserver.extension.ns}")
	private String mockserverExtensionNs;
	@Autowired
	private List<MockServerTag> commands;
	@Autowired
	private List<DocumentParsingListener> documentParsingListeners;

	@Override
	public void addTo(ConcordionExtender concordionExtender) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MockServerConfig.class);
		MockServerExtension extension = (MockServerExtension) ctx.getBean("mockServerExtension");
		extension.init(concordionExtender);
	}

	private void init(ConcordionExtender concordionExtender) {
		commands.forEach(command -> concordionExtender.withCommand(mockserverExtensionNs, command.getName(), command));
		documentParsingListeners.forEach(listener -> concordionExtender.withDocumentParsingListener(listener));
	}
}
