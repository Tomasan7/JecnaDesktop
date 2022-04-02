package me.tomasan7.jecnadesktop;

import me.tomasan7.jecnadesktop.web.JecnaWebClient;

import java.util.concurrent.ExecutionException;

public class Main
{
	public static void main (String[] args) throws ExecutionException, InterruptedException
	{
		/* Replace with your login. */
		JecnaWebClient client = new JecnaWebClient("user", "password");

		if (client.login().get())
			System.out.println("Login successful.");
		else
			System.out.println("Login failed.");
	}
}
