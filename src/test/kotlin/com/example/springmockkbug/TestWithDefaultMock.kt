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
//    @MockkBean(name = "default") // fixes problem
    private lateinit var service: ExampleService

    @Test
    fun `method should return stubbed result if it was provided`() {
        val stubbedResult = "stubbedResult"
        every { service.foo() } returns stubbedResult

        val actualResult = service.foo()

        assertThat(actualResult).isEqualTo(stubbedResult)
    }

    // This test passes if this class is run separately but fails if relaxed mock of ExampleService is registered first.
    // The reason is in MockkDefinition class: equals and hashcode don't take relaxed and relaxUnitFun on board.
    // In current implementation this problem can be solved by adding `name` in @MockkBean annotation to distinguish
    // relaxed and default versions of mocks.
    @Test
    fun `method should throw exception if no answer was provided`() {
        assertThatThrownBy { service.foo() }
            .isInstanceOf(MockKException::class.java)
            .hasMessageContaining("no answer found for: ExampleService(")
            .hasMessageContaining(").foo()")
    }
}
