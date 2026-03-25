package com.idscodelabs.mock_function.exceptions

class ResultNotMockedException(index: Int): RuntimeException("Result not mocked for call at index $index. Did you forget to add a 'default' in your result building?")