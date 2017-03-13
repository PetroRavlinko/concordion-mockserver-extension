package com.ravlinko.concordion.extension.mockserver.tag;

import org.concordion.api.AbstractCommand;

public class MockServerTag extends AbstractCommand {

	private String name;
	private String httpName;

	public String getName() {
		return name;
	}

	public MockServerTag setName(String name) {
		this.name = name;
		return this;
	}

	public String getHttpName() {
		return httpName;
	}

	public MockServerTag setHttpName(String httpName) {
		this.httpName = httpName;
		return this;
	}
}
