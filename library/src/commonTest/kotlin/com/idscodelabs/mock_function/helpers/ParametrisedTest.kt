package com.idscodelabs.mock_function.helpers


inline fun <reified T> parametrisedTest(
    caseCount: Int,
    produceResult: (Int)->T,
    noinline testFunction: (T)->Unit
) = parametrisedTest(
    *(0 until caseCount).map(produceResult).toTypedArray(),
    testFunction = testFunction
)
fun <T> parametrisedTest(
    vararg values: T,
    testFunction: (T)->Unit
){
    val result = values.map {
        try{
            testFunction(it)
            true
        } catch(e: Throwable){
            println("------------------------------------------------")
            println("Test FAILED for case: $it\n")
            e.printStackTrace()
            false
        }
    }
    val failed = result.count { !it }
    val passed = result.count { it }

    if(failed == 0) {
        println("${values.size} test cases PASSED.")
    } else {
        println("${values.size} test cases run.")
        println("$failed test cases FAILED.")
        println("$passed test cases PASSED.")
        throw TestFailedException("$failed/${values.size} test cases FAILED.")
    }

}
