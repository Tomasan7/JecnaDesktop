package me.tomasan7.jecnadesktop.web

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.cookies.*
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.concurrent.CompletableFuture

abstract class AuthWebClient
{
    protected val auth: Auth

    protected val cookieStorage = AcceptAllCookiesStorage()

    protected val httpClient = HttpClient(CIO) {
        install(HttpCookies) {
            storage = cookieStorage
        }
    }

    constructor(auth: Auth)
    {
        this.auth = auth
    }

    constructor(username: String, password: String)
    {
        auth = Auth(username, password)
    }

    /**
     * Logins the client.
     *
     * @return True if login was successful, false otherwise.
     */
    abstract fun login(): CompletableFuture<Boolean>

    /**
     * Makes a request to the provided path. May vary depending on whether user is logged in or not.
     *
     * @param path Relative path from the domain. Must include first slash.
     * @return The HTTP response's body as [String].
     */
    abstract fun queryStringBody(path: String): CompletableFuture<String>

    companion object
    {
        protected fun encodeParams(params: Map<String, String>): String
        {
            return params.entries.joinToString(separator = "&") { (key, value) -> key + "=" + URLEncoder.encode(value, StandardCharsets.UTF_8) }
        }

        protected fun encodeParams(vararg params: String): String
        {
            if (params.size % 2 != 0) throw RuntimeException("Received even number of parameters - cannot assign their values.")
            val paramsMap: MutableMap<String, String> = HashMap()
            var i = 0
            while (i < params.size)
            {
                paramsMap[params[i]] = params[i + 1]
                i += 2
            }
            return encodeParams(paramsMap)
        }
    }
}