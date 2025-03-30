package com.carmellium.forgeshot.config;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 3/28/25
 */
public abstract class ConfigEntry<T> {

	public abstract T get();
	public abstract void set(T value);

	public abstract void save();

}
