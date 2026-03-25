package com.idscodelabs.mock_function.result

interface ResultProducer<R>: (Int)->R {

    companion object {
        fun <R> always(result: R): ResultProducer<R> = object: ResultProducer<R> {
            override fun invoke(p1: Int): R = result
        }

        fun <R> from(builderFunction: ResultBuilderScope<R>.() -> Unit): ResultProducer<R> {
            val scope = ResultBuilderScope<R>()
            scope.builderFunction()
            return scope.build()
        }
    }
}