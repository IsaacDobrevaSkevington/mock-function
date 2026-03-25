package com.idscodelabs.mock_function.result

import com.idscodelabs.mock_function.exceptions.ResultNotMockedException

class ResultBuilderScope<R> {

    private class Container<R>(val contained: R)

    private var default: Container<R>? = null
    private val indexed: MutableMap<Int, R> = mutableMapOf()

    fun first(result: R) = indexOf(0, result)

    fun indexOf(index: Int, result: R){
        indexed[index] = result
    }

    fun rangeOf(range: IntRange, result: R) = range.forEach {
        indexOf(it, result)
    }

    fun default(result: R){
        default = Container(result)
    }



    internal fun build(): ResultProducer<R> = object : ResultProducer<R> {
        override fun invoke(p1: Int): R {
            indexed[p1]?.let {
                return it
            }
            default?.contained?.let {
                return it
            }
            throw ResultNotMockedException(p1)
        }

    }
}