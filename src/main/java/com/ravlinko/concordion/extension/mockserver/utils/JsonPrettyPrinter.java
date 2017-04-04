package com.ravlinko.concordion.extension.mockserver.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.springframework.stereotype.Component;

@Component
public class JsonPrettyPrinter {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public String prettyPrint(final String json) {

		try {
			JsonElement element = GSON.fromJson(json, JsonElement.class);
			return GSON.toJson(element);
		} catch (Exception e) {
			return json;
		}
	}

}
