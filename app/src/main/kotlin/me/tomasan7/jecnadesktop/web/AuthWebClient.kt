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
}