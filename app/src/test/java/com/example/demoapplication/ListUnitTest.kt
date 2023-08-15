package com.example.demoapplication

import org.junit.Test

class ListUnitTest {


    @Test
    fun listRunTest() {
        val mList = arrayListOf<String>("1", "2", "3", "5")
        val mList2 = listOf<String>("1", "2", "3", "5")
        repeat(1000) {
            mList.addAll(mList2)
        }
        val s1 = System.currentTimeMillis()
        val list1 = mList.filter { it == "1" }.sortedDescending().take(3)
        val s2 = System.currentTimeMillis()
        println("list1: ${list1.toString()}， 耗时：${s2 - s1}")
        val s3 = System.currentTimeMillis()
        val list2 = mList.asSequence().filter { it == "1" }.sortedDescending().take(3).toList()
        val s4 = System.currentTimeMillis()
        println("list2: ${list2.toString()}， 耗时：${s4 - s3}")
    }
}