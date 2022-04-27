package me.tomasan7.jecnadesktop.parser;

/**
 * Is thrown, when there's some error during parsing.
 * Could be wrong or incomplete source.
 */
public class ParseException extends RuntimeException
{
	public ParseException ()
	{
	}

	public ParseException (String message)
	{
		super(message);
	}

	public ParseException (String message, Throwable cause)
	{
		super(message, cause);
	}

	public ParseException (Throwable cause)
	{
		super(cause);
	}
}
