package com.example.springmockkbug

import com.ninjasquad.springmockk.MockkBean
import io.mockk.MockKException
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TestWithDefaultMock {

    @MockkBean
    private lateinit var service: ExampleService

    @Test
    fun `method should return stubbed result if it was provided`() {
        val stubbedResult = "stubbedResult"
        every { service.foo() } returns stubbedResult

        val actualResult = service.foo()

        assertThat(actualResult).isEqualTo(stubbedResult)
    }

    @Test
    fun `method should throw exception if no answer was provided`() {
        assertThatThrownBy { service.foo() }
            .isInstanceOf(MockKException::class.java)
            .hasMessageContaining("no answer found for: ExampleService(com.example.springmockkbug.ExampleService#0 bean#1).foo()")
    }
}
