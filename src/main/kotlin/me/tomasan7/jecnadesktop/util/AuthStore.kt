package me.tomasan7.jecnadesktop.util

import me.tomasan7.jecnaapi.web.Auth
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * Is responsible for saving and loading user's [Auth] to and from a file.
 */
object AuthStore
{
    private val FILE = File("auth.nggyu")

    /**
     * Saves the [Auth] to the file.
     * @param auth The [Auth] to save.
     * @param override Whether to override existing file or not.
     */
    fun save(auth: Auth, override: Boolean = true)
    {
        if (FILE.exists() && !override)
            return

        try
        {
            FileOutputStream(FILE).use { outputStream -> outputStream.write(auth.encrypt()) }
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }
    }

    /**
     * Loads the [Auth] from the file.
     * @return The loaded [Auth]. Or `null`, if the [Auth] isn't saved, or is incorrect format.
     */
	fun load(): Auth?
    {
        if (!isSaved) return null

        try
        {
            FileInputStream(FILE).use { inputStream -> return Auth.decrypt(inputStream.readAllBytes()) }
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }
        catch (e: IllegalArgumentException)
        {
            /* Thrown when the saved password is in incorrect format. Should only happen, if the file was manipulated with from outside. */
            return null
        }

        return null
    }

    /**
     * @return Whether a file with saved [Auth] already exists or not.
     */
    val isSaved: Boolean
        get() = FILE.exists()

    /**
     * Deletes the saved [Auth].
     * @return `true`, if [Auth] was successfully deleted.
     */
	fun delete(): Boolean
    {
        return FILE.delete()
    }
}