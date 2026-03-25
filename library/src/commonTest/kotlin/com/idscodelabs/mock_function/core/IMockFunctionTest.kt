package com.idscodelabs.mock_function.core

import com.idscodelabs.mock_function.helpers.TestIMockFunction
import com.idscodelabs.mock_function.helpers.parametrisedTest
import com.idscodelabs.mock_function.result.ResultProducer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class IMockFunctionTest {
    
    val defaultResult: Int = 1
    private fun getMockFunction(result: Int): IMockFunction<Int> = TestIMockFunction(ResultProducer.always(result))
    private fun invokeFunction(function: IMockFunction<Int>): Int = function.onInvoke()

    @Test
    fun `GIVEN MockFunction with static arg1 WHEN invoked THEN result is returned`() = parametrisedTest<Int>(
        100,
        { it }
    ){
        val expected = it
        val mockFunction = getMockFunction(it)
        val actual = invokeFunction(mockFunction)
        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN MockFunction is invoked WHEN verify wasCalled is called THEN no error is thrown`()= parametrisedTest<Int>(
        100,
        { it }
    ){
        val mockFunction = getMockFunction(it)
        invokeFunction(mockFunction)

        mockFunction.verify {
            wasCalled()
        }
    }

    @Test
    fun `GIVEN MockFunction is invoked arg1 times WHEN verify wasCalledExactly arg2 eq arg1 is called THEN no error is thrown`()= parametrisedTest<Pair<Int, Int>>(
        100,
        { it to it }
    ){(calls, assertCalls) ->
        val mockFunction = getMockFunction(defaultResult)
        (0 until calls).forEach {
            invokeFunction(mockFunction)
        }

        mockFunction.verify {
            wasCalledExactly(assertCalls)
        }
    }

    @Test
    fun `GIVEN MockFunction is invoked arg1 times WHEN verify wasCalledAtLeast arg2 lte arg1 is called THEN no error is thrown`() = parametrisedTest<Pair<Int, Int>>(
        0 to 0,
        1 to 0,
        2 to 0,
        2 to 1,
        50 to 1,
        60 to 59,
        1000 to 560
    ){(calls, assertCalls) ->
        val mockFunction = getMockFunction(defaultResult)
        (0 until calls).forEach {
            invokeFunction(mockFunction)
        }

        mockFunction.verify {
            wasCalledAtLeast(assertCalls)
        }
    }

    @Test
    fun `GIVEN MockFunction is invoked three times WHEN verify wasCalledAtMost 4 is called THEN no error is thrown`(){
        val mockFunction = getMockFunction(defaultResult)
        invokeFunction(mockFunction)
        invokeFunction(mockFunction)

        mockFunction.verify {
            wasCalledAtMost(4)
        }
    }

    @Test
    fun `GIVEN MockFunction is not invoked WHEN verify wasNotCalled is called THEN no error is thrown`(){
        val mockFunction = getMockFunction(defaultResult)

        mockFunction.verify {
            wasNotCalled()
        }
    }


    @Test
    fun `GIVEN MockFunction is not invoked WHEN verify wasCalled is called THEN error is thrown`(){
        val mockFunction = getMockFunction(defaultResult)

        assertFailsWith<AssertionError>("Expected function to be called, was not called.") {
            mockFunction.verify {
                wasCalled()
            }
        }
    }

    @Test
    fun `GIVEN MockFunction is invoked once WHEN verify wasCalledExactly 2 is called THEN error is thrown`(){
        val mockFunction = getMockFunction(defaultResult)
        invokeFunction(mockFunction)

        assertFailsWith<AssertionError>("Expected function to be called 2 times, was called 1 times.") {
            mockFunction.verify {
                wasCalledExactly(2)
            }
        }
    }

    @Test
    fun `GIVEN MockFunction is invoked one time WHEN verify wasCalledAtLeast 2 is called THEN error is thrown`(){
        val mockFunction = getMockFunction(defaultResult)
        invokeFunction(mockFunction)

        assertFailsWith<AssertionError>("Expected function to be called at least 2 times, was called 1 times.") {
            mockFunction.verify {
                wasCalledAtLeast(2)
            }
        }
    }

    @Test
    fun `GIVEN MockFunction is invoked five times WHEN verify wasCalledAtMost 4 is called THEN error is thrown`(){
        val mockFunction = getMockFunction(defaultResult)
        invokeFunction(mockFunction)
        invokeFunction(mockFunction)
        invokeFunction(mockFunction)
        invokeFunction(mockFunction)
        invokeFunction(mockFunction)

        assertFailsWith<AssertionError>("Expected function to be called at most 4 times, was called 5 times.") {
            mockFunction.verify {
                wasCalledAtMost(4)
            }
        }
    }

    @Test
    fun `GIVEN MockFunction is invoked WHEN verify wasNotCalled is called THEN error is thrown`(){
        val mockFunction = getMockFunction(defaultResult)

        invokeFunction(mockFunction)

        assertFailsWith<AssertionError>("Expected function not to be called, was called.") {
            mockFunction.verify {
                wasNotCalled()
            }
        }
    }
}