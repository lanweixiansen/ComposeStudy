package com.example.demoapplication

import org.junit.Test
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * 动态代理
 */
class ProxyUnitTest {

    @Test
    fun testProxy() {
        val inter = ProxyUntil.created<ProxyTestInterface>()
        inter.createdTestUnitOne("a", 1)
        inter.createdTestUnitTwo(2)
    }

    object ProxyUntil {
        inline fun <reified T> created(): T {
            return Proxy.newProxyInstance(
                T::class.java.classLoader, arrayOf(T::class.java)
            ) { proxy, method, args ->
                return@newProxyInstance invoke(method, args)
            } as T
        }

        fun invoke(method: Method?, args: Array<Any>?): Any? {
            println("returnType: ${method?.genericReturnType}")
            println("method: $method")
            println("args: ${args?.toList().toString()}")
            return null
        }
    }

}

interface ProxyTestInterface {
    fun createdTestUnitOne(name: String, age: Int)

    fun createdTestUnitTwo(num: Int)
}