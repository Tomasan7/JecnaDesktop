package me.tomasan7.jecnadesktop.web;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

/**
 * Simply holds a username and password pair.
 * Defines {@link #encrypt()} and {@link #decrypt(byte[])} methods.
 */
public record Auth(@NotNull String username, @NotNull String password)
{
	/**
	 * All passwords are checked with this regex.
	 * If the password (trimmed) doesn't match this regex, it's invalid and construction will fail.
	 */
	private static final String VALID_PASSWORD_REGEX = "[\\w\\-@$!%*#?&]+";

	public Auth (@NotNull String username, @NotNull String password)
	{
		if (username.isBlank() || password.isBlank())
			throw new IllegalArgumentException("Username and password cannot be empty.");

		String resultUsername = username.trim();
		String resultPassword = password.trim();

		if (resultUsername.contains("\n") || resultPassword.contains("\n"))
			throw new IllegalArgumentException("Username and password cannot contain new lines.");

		if (!resultPassword.matches(VALID_PASSWORD_REGEX))
			throw new IllegalArgumentException("Invalid password.");

		this.username = resultUsername;
		this.password = resultPassword;
	}

	/**
	 * Simply encrypts this login into <code>byte[]</code> so it isn't easily human-readable.
	 * <b>Doesn't secure the information!</b>
	 *
	 * @return The encrypted login.
	 */
	public byte[] encrypt ()
	{
		byte[] usernameBytes = username.getBytes(StandardCharsets.UTF_8);
		byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);

		byte[] bytes = new byte[usernameBytes.length + passwordBytes.length + 1];

		/* Copies usernameBytes, new line and passwordBytes in order indo bytes. */
		System.arraycopy(usernameBytes, 0, bytes, 0, usernameBytes.length);
		bytes[usernameBytes.length] = '\n';
		System.arraycopy(passwordBytes, 0, bytes, usernameBytes.length + 1, passwordBytes.length);

		/* Shift the bytes, so each character's byte is shifted. */
		for (int i = 0; i < bytes.length; i++)
			bytes[i] += 10;

		return bytes;
	}

	/**
	 * Decrypt encrypted {@link Auth}.
	 * @see #encrypt()
	 * @param bytes The encrypted bytes.
	 * @return The {@link Auth} instance.
	 */
	public static Auth decrypt (byte[] bytes)
	{
		/* Shift the characters bytes back. */
		for (int i = 0; i < bytes.length; i++)
			bytes[i] -= 10;

		String asString = new String(bytes, StandardCharsets.UTF_8);
		String[] split = asString.split("\n", 2);

		String username = split[0];
		String password = split[1];

		return new Auth(username, password);
	}
}