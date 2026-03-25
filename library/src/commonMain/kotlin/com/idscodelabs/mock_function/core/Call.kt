package com.idscodelabs.mock_function.core

class Call(
        val args: List<Any?>,
    ) {
        override fun toString(): String = "CALL with arguments $args"
    }