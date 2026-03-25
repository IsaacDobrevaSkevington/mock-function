package com.idscodelabs.mock_function.helpers

import com.idscodelabs.mock_function.core.IMockFunction
import com.idscodelabs.mock_function.result.ResultProducer

class TestIMockFunction<R>(resultProducer: ResultProducer<R>): IMockFunction<R>(resultProducer)