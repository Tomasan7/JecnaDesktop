package me.tomasan7.jecnadesktop.web;

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
