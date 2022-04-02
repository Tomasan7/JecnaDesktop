package me.tomasan7.jecnadesktop.web;

import java.util.Arrays;
import java.util.Optional;

public class JecnaSession extends Session
{
	private static final String COOKIE_KEY = "JSESSIONID";

	protected JecnaSession (String sessionID)
	{
		super(sessionID);
	}

	public static JecnaSession fromHeader (String cookieHeader)
	{
		String[] cookies = cookieHeader.split(";");

		Optional<String> sessionCookie = Arrays.stream(cookies)
									 .filter(cookie -> cookie.contains(COOKIE_KEY))
									 .findAny();

		return new JecnaSession(sessionCookie.get().split("=")[1]);
	}

	@Override
	public String getCookieKey ()
	{
		return COOKIE_KEY;
	}
}
