//package com.example.demoapplication
//
//import org.junit.Test
//import kotlin.reflect.KMutableProperty1
//import kotlin.reflect.full.memberProperties
//
///**
// * 反射
// * 反射的特质：
// * 1：感知程序的状态
// * 2：修改程序的状态
// * 3：根据程序的状态做出反应
// */
//class RefiledUnitTest {
//    data class Student(val name: String, val score: Double, val height: Int)
//    data class School(val name: String, var address: String)
//
//    /**
//     * 反射读取数据类内容
//     */
//    private fun readMembers(obj: Any) {
//        obj::class.memberProperties.forEach {
//            println("${obj::class.simpleName}.${it.name} = ${it.getter.call(obj)}")
//        }
//    }
//
//    /**
//     * 反射修改数据类内容
//     */
//    private fun changeValue(obj: Any) {
//        obj::class.memberProperties.forEach {
//            if (it.name == "address" &&  // 修改属性名为 address 的值
//                it is KMutableProperty1 && // 判断改属性是否为可变
//                it.setter.parameters.size == 2 && // 判断改属性的setter方法的参数是否为两个，setter.call("对象", "值")
//                it.getter.returnType.classifier == String::class // 判断改属性的 getter方法返回值是否为 String
//            ) {
//                it.setter.call(obj, "Hang_Zhou")
//                println("====== Address change ======")
//            } else {
//                println("====== Wrong Type ======")
//            }
//        }
//    }
//
//    @Test
//    fun test() {
//        val student = Student("haha", 1.0, 170)
//        val school = School("hehe", "1")
//        readMembers(school)
//        readMembers(student)
//        changeValue(school)
//        readMembers(school)
//    }
//
//    private fun kClassTest(obj: Any) {
//        obj::class // KClass 代表了一个 Kotlin 的类，下面是它的重要成员
//        obj::class.apply {
//            /**
//             * 类的名称，对于匿名内部类，则为 null
//             */
//            simpleName
//            /**
//             * 完整的类名
//             */
//            qualifiedName
//            /**
//             * 所有的成员属相和方法，类型是Collection>
//             */
//            members
//            /**
//             * 类的所有成员属性
//             */
//            memberProperties
//            /**
//             * 类的所有构造函数，类型是Collection>>
//             */
//            constructors
//            /**
//             * 类的所有嵌套类，类型是Collection>
//             */
//            nestedClasses
//            /**
//             * 类的可见性，类型是KVisibility?，分别是这几种情况，PUBLIC、PROTECTED、INTERNAL、PRIVATE；isFinal，是不是 final
//             */
//            visibility
//            /**
//             * 是不是 open
//             */
//            isOpen
//            /**
//             * 是不是抽象类
//             */
//            isAbstract
//            /**
//             * 是不是密封类
//             */
//            isSealed
//            /**
//             * 是不是数据类
//             */
//            isData
//            /**
//             * 是不是内部类
//             */
//            isInner
//            /**
//             * 是不是伴生对象
//             */
//            isCompanion
//            /**
//             * 是不是函数式接口
//             */
//            isFun
//            /**
//             * 是不是Value Class
//             */
//            isValue
//        }
//    }
//
//    private fun kCallTest(obj: Any) {
//        obj::class.memberProperties.forEach {
//            it.getter.apply {
//                /**
//                 * 名称（属性 / 函数）
//                 */
//                name
//                /**
//                 * 所有的参数，类型是List<KParameter>，指的是调用这个元素所需的所有参数
//                 */
//                parameters
//                /**
//                 * 返回值类型，类型是KType
//                 */
//                returnType
//                /**
//                 * 所有的类型参数 (比如泛型)，类型是List<KParameter>
//                 */
//                typeParameters
//                /**
//                 * KCallable 对应的调用方法
//                 */
//                call()
//                /**
//                 * 可见性
//                 */
//                visibility
//                /**
//                 * 是否是挂起函数
//                 */
//                isSuspend
//            }
//        }
//    }
//
//    /**
//     * 代表了KCallable当中的参数，它的重要成员如下
//     */
//    private fun kParameterTest(obj: Any) {
//        obj::class.memberProperties.forEach { it ->
//            it.getter.parameters.forEach {
//                /**
//                 * 参数的位置，下表从0开始
//                 */
//                it.index
//                /**
//                 * 参数的名称
//                 */
//                it.name
//                /**
//                 * 参数的类型
//                 */
//                it.type
//                /**
//                 * 参数的种类，INSTANCE 是对象实例、EXTENSION_RECEIVER扩展接收者、VALUE实际的参数值
//                 */
//                it.kind
//
//            }
//        }
//    }
//
//
//    /**
//     * KType, 代表了Kotlin 中的类型
//     */
//    private fun kTypeTest(obj: Any) {
//        obj::class.memberProperties.forEach { it ->
//            it.getter.parameters.forEach {
//                it.type.apply {
//                    /**
//                     * 对应的 Kotlin类，即 KClass
//                     */
//                    classifier
//                    /**
//                     * 类型的参数类型，类型的泛型参数
//                     */
//                    arguments
//                    /**
//                     * 是否为可空类型，即是否有 ? 标记
//                     */
//                    isMarkedNullable
//                }
//            }
//        }
//    }
//}
//
//
