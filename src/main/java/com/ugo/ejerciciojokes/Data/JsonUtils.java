package com.ugo.ejerciciojokes.Data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {
	
	public static <T> String crearJsonPretty(T object) {
		return new GsonBuilder()
				.setPrettyPrinting()
				.create()
				.toJson(object);
	}
	
	public static <T> T devolverObjetoGsonGenerico(String url, Class<T> clase) {
        return new Gson().fromJson(InternetUtils.readUrl(url),clase);
	}
	

}
