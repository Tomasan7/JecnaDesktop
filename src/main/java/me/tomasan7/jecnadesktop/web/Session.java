package me.tomasan7.jecnadesktop.web;

/**
 * Made for accessing web's session id.
 */
public abstract class Session
{
	/**
	 * The session ID passed in cookies.
	 */
	private final String sessionID;

	protected Session (String sessionID)
	{
		this.sessionID = sessionID;
	}

	/**
	 * @return this session in an http Cookie header format. (CookieKey=SessionID)
	 */
	public String toHeader ()
	{
		return getCookieKey() + "=" + sessionID;
	}

	/**
	 * Gets the key passed as http cookie.
	 */
	public abstract String getCookieKey();

	/**
	 * Gets the session ID passed in cookies.
	 */
	public String getSessionID()
	{
		return sessionID;
	}
}
