package com.example.demoapplication

import org.junit.Test

class CopyUnitTest {
    private var mBean: TestBean = TestBean("2")

    @Test
    fun main() {
        test(mBean)
    }

    fun test(bean: TestBean) {
        println(bean.string)
        mBean.string = "1"
        println(bean.string)
    }

    class TestBean(
        var string: String = "2"
    )
}