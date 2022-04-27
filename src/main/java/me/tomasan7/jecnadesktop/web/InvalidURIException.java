package me.tomasan7.jecnadesktop.web;

/**
 * Thrown, when an invalid {@link java.net.URI} is used.
 * The validity of the {@link java.net.URI} depends on context.
 */
public class InvalidURIException extends RuntimeException
{
	public InvalidURIException (String uri)
	{
		super("Invalid URI '" + uri + '\'');
	}

	public InvalidURIException ()
	{
		super("Invalid URI.");
	}
}
