package com.example.demoapplication

import android.graphics.Color
import com.example.lib_base.manager.AppManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Test
import java.io.File
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class FileUnitTest {
    private lateinit var  outputFile: File
    private val url =
        "https://raw.githubusercontent.com/chaxiu/ImageProcessor/main/src/main/resources/images/android.png"

    @Test
    fun main() {
        runBlocking {
            val path = AppManager.getApplicationContext().filesDir
            outputFile = File(path, "down.png")
            downloadSync()
        }
    }

    /**
     * 下载图片到本地
     */
    private suspend fun downloadSync() {
        withContext(Dispatchers.IO) {
            val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(10L, TimeUnit.SECONDS)
                .readTimeout(10L, TimeUnit.SECONDS)
                .build()

            val request = Request.Builder().url(url).build()
            val response = okHttpClient.newCall(request).execute()

            val body = response.body
            val responseCode = response.code

            if (responseCode >= HttpURLConnection.HTTP_OK &&
                responseCode < HttpURLConnection.HTTP_MULT_CHOICE &&
                body != null
            ) {
                body.byteStream().apply {
                    outputFile.outputStream().use { fileOut ->
                        copyTo(fileOut)
                    }
                }
            }
        }
    }

    private fun loadImage(file: File) {

    }

    class Image(private val image: Array<Array<Color>>) {
        fun getWidth(): Int {
            return image[0].size
        }

        fun getHeight() = image.size

        fun getPixel(x: Int, y: Int) = image[y][x]
    }
}