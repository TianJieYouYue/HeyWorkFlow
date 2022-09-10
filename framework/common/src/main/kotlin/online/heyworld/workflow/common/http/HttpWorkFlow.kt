package online.heyworld.workflow.common.http

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import online.heyworld.workflow.HeyWorkFlow

open class InnerHttpWorkFlow(
    name: String,
    val requestBuild: (reqBuilder: Request.Builder) -> Request,
    val responseHandler: (response: Response) -> Unit
) : HeyWorkFlow(name) {

    companion object {
        private val okHttpClient = OkHttpClient.Builder().build()
    }

    override fun run() {
        val request = requestBuild(Request.Builder())
        responseHandler(okHttpClient.newCall(request).execute())
    }
}

class HttpWorkFlow(
    name: String,
    url: String,
    requestBody: String,
    responseHandler: (response: String) -> Unit
) : InnerHttpWorkFlow(name, {
    it.url(url)
    if (requestBody.isNotEmpty()) {
        it.post(requestBody.toRequestBody("application/json".toMediaType()))
    }
    it.build()
}, {
    responseHandler(it.body?.string() ?: "")
})