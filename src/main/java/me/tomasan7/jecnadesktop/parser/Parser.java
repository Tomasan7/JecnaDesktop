package me.tomasan7.jecnadesktop.parser;

public interface Parser<T>
{
	/**
	 * Takes a {@link String} and parses it into new {@link T} instance.
	 * @param source The string to parse from, could be anything, but most likely an HTML or JSON.
	 * @return The {@link T} instance represented by the passed {@link String}.
	 */
	T parse (String source);
}