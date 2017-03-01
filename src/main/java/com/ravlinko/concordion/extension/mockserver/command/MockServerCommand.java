package com.ravlinko.concordion.extension.mockserver.command;

import org.concordion.api.AbstractCommand;

public class MockServerCommand extends AbstractCommand {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
