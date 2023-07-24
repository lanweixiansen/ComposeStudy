package com.example.demoapplication

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.concurrent.Executors


class ConcurrencyUnitTest {

// 代码段11

    sealed class Msg
    object AddMsg : Msg()

    class ResultMsg(
        val result: CompletableDeferred<Int>
    ) : Msg()


    /**
     * Actor处理并发
     * Actor底层为Channel，Channel实现了在不同线程中接收发送消息
     * 开启十个协程重复1000次同步发送密封类的消息，actor内收到AddMsg消息类型，对counter做累加操作
     * 最后发送ResultMsg获取counter的值，counter在单线程中操作，所以不存在同步问题
     */
    @OptIn(ObsoleteCoroutinesApi::class)
    @Test
    fun main() = runBlocking {

        suspend fun addActor() = actor<Msg> {
            var counter = 0
            for (msg in channel) {
                when (msg) {
                    is AddMsg -> counter++
                    is ResultMsg -> msg.result.complete(counter)
                }
            }
        }

        val actor = addActor()
        val jobs = mutableListOf<Job>()

        repeat(10) {
            val job = launch(Dispatchers.Default) {
                repeat(1000) {
                    actor.send(AddMsg)
                }
            }
            jobs.add(job)
        }

        jobs.joinAll()

        val deferred = CompletableDeferred<Int>()
        actor.send(ResultMsg(deferred))

        val result = deferred.await()
        actor.close()

        println("i = $result")
    }


    /**
     * 函数式协程并发
     * 每个协程内单独管理计算变量i，累加到1000，开启十个协程
     * 十个协程并发执行，最后主线程创建变量result计算十个协程的i的和
     */
    @Test
    fun asyncTest() = runBlocking {
        // 写法一
        val deferreds = mutableListOf<Deferred<Int>>()
        repeat(10) {
            val deferred = async(Dispatchers.Default) {
                var i = 0
                repeat(1000) {
                    i++
                }
                return@async i
            }
            deferreds.add(deferred)
        }

        var result = 0
        deferreds.forEach {
            result += it.await()
        }
        // 写法二
        val result2 = (1..10).map {
            async(Dispatchers.Default) {
                var i = 0
                repeat(1000) {
                    i++
                }
                return@async i
            }
        }.awaitAll().sum()

        println("i = $result  i2 = $result2")
    }


    @Test
    fun cancelTest() {
        val job = CoroutineScope(Dispatchers.Default).launch {
            var i = 0
            while (true) {
                Thread.sleep(500L)
                i++
                println("i = $i")
            }
        }
        Thread.sleep(2000L)
        job.cancel()
        Thread.sleep(20000L)
        println("End")
    }


    private val fixedDispatcher = Executors.newFixedThreadPool(2) {
        Thread(it, "MyFixedThread").apply { isDaemon = false }
    }.asCoroutineDispatcher()

    @Test
    fun structuredConcurrencyTest() = runBlocking {
        // 父协程
        val parentJob = launch(fixedDispatcher) {

            // 1，注意这里
            launch(Job()) { // 子协程1
                var i = 0
                while (isActive) {
                    Thread.sleep(500L)
                    i++
                    println("First i = $i")
                }
            }
            launch { // 子协程2
                var i = 0
                while (isActive) {
                    Thread.sleep(500L)
                    i++
                    println("Second i = $i")
                }
            }
        }

        delay(2000L)

        parentJob.cancel()
        parentJob.join()

        println("End")
        Thread.sleep(20000L)
    }


}