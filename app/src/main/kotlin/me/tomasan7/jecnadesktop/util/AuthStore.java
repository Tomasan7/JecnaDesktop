package me.tomasan7.jecnadesktop.util;

import me.tomasan7.jecnadesktop.web.Auth;

import java.io.*;

/**
 * Is responsible for saving and loading user's {@link Auth} to and from a file.
 */
public class AuthStore
{
	private static final File FILE = new File("auth.nggyu");

	/**
	 * Saves the {@link Auth} to the file.
	 * @param auth The {@link Auth} to save.
	 * @param override Whether to override existing file or not.
	 */
	public static void save (Auth auth, boolean override)
	{
		try (FileOutputStream outputStream = new FileOutputStream(FILE))
		{
			outputStream.write(auth.encrypt());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Saves the {@link Auth} to the file. Overrides the file if it exists already.
	 * @param auth The {@link Auth} to save.
	 */
	public static void save (Auth auth)
	{
		save(auth, true);
	}

	/**
	 * Loads the {@link Auth} from the file.
	 * @return The loaded {@link Auth}. Or {@code null}, if the {@link Auth} isn't saved, or is incorrect format.
	 */
	public static Auth load ()
	{
		if (!isSaved())
			return null;

		try (FileInputStream inputStream = new FileInputStream(FILE))
		{
			return Auth.decrypt(inputStream.readAllBytes());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			/* Thrown when the saved password is in incorrect format. Should only happen, if the file was manipulated with from outside. */
			return null;
		}

		return null;
	}

	/**
	 * @return Whether a file with saved {@link Auth} already exists or not.
	 */
	public static boolean isSaved ()
	{
		return FILE.exists();
	}

	/**
	 * Deletes the saved {@link Auth}.
	 * @return {@code true}, if {@link Auth} was successfully deleted.
	 */
	public static boolean delete ()
	{
		return FILE.delete();
	}
}
