package com.example.lib_base.ext

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

fun jsonToString(context: Context, fileName: String): String {
    val buffer = StringBuffer()
    runCatching {
        val assetManager = context.assets
        val bufferedReader = BufferedReader(InputStreamReader(assetManager.open(fileName)))
        var line: String?
        while (true) {
            line = bufferedReader.readLine()
            if (line == null) return@runCatching
            buffer.append(line)
        }
    }
    return buffer.toString()
}