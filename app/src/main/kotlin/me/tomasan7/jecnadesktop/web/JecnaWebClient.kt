package me.tomasan7.jecnadesktop.web

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture

class JecnaWebClient : AuthWebClient
{
    private val coroutineScope = CoroutineScope(CoroutineName("JecnaWebClient-Coroutine-Scope"))

    constructor(auth: Auth) : super(auth)

    constructor(username: String, password: String) : super(username, password)

    override fun login(): CompletableFuture<Boolean>
    {
        val future = CompletableFuture<Boolean>()

        coroutineScope.launch(Dispatchers.IO) {
            /* The user login request. */
            val response = httpClient.submitForm(
                block = newRequestBuilder("/user/login"),
                formParameters = Parameters.build {
                    append("user", auth.username)
                    append("pass", auth.password)
                })

            /* If the login was successful, web responds with a redirect status code. */
            future.complete(response.status == HttpStatusCode.Found)
        }

        return future
    }

    override fun queryStringBody(path: String): CompletableFuture<String>
    {
        val future = CompletableFuture<String>()

        coroutineScope.launch(Dispatchers.IO) {

            future.complete(httpClient.get(newRequestBuilder(path)).body())
        }

        return future
    }

    /**
     * Returns a function modifying [HttpRequestBuilder] used by Ktor HttpClient.
     * Sets the url relative to [ENDPOINT].
     * Adds a User-Agent header, since the web requires it. (uses Mozilla/5.0)
     *
     * @param path The path to query. Must include first slash.
     * @param block Additional modifications to the request.
     * @return The function.
     */
    private fun newRequestBuilder(path: String, block: (HttpRequestBuilder.() -> Unit)? = null): HttpRequestBuilder.() -> Unit
    {
        return {
            if (block != null)
                block()

            url(urlString = ENDPOINT + path)
            /* The web requires a User-Agent header, otherwise it responds to the login request with
			 * 403 - "The page you were looking for is not availible." (yes, it contains the grammar mistake) */
            header(HttpHeaders.UserAgent, "Mozilla/5.0")
        }
    }

    companion object
    {
        const val ENDPOINT = "https://www.spsejecna.cz"
    }
}