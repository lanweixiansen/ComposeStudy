package com.example.demoapplication

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.system.measureTimeMillis

class JobUnitTest {

    @Test
    fun test() {
        runBlocking {
            suspend fun getRE1(): String {
                delay(1000)
                return "RE1"
            }

            suspend fun getRE2(): String {
                delay(1000)
                return "RE2"
            }

            suspend fun getRE3(): String {
                delay(1000)
                return "RE3"
            }

            val result = mutableListOf<String>()
            val time = measureTimeMillis {
                result.add(getRE1())
                result.add(getRE2())
                result.add(getRE3())
            }

            val results = mutableListOf<String>()
            val time2 = measureTimeMillis {
                val result1 = async { getRE1() }
                val result2 = async { getRE2() }
                val result3 = async { getRE3() }
                results.add(result1.await())
                results.add(result2.await())
                results.add(result3.await())
            }

            println("Time: $time")

            println("Time2: $time2")
        }
    }
}