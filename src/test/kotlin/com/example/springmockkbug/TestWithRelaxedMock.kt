package com.example.springmockkbug

import com.ninjasquad.springmockk.MockkBean
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TestWithRelaxedMock {

    @MockkBean(relaxed = true)
//    @MockkBean(relaxed = true, name = "relaxed") // solution in current implementation
    private lateinit var service: ExampleService

    // This test will fail if default mock of ExampleService is registered first. Check details in TestWithDefaultMock.
    // In current implementation this problem can be solved by adding `name` in @MockkBean annotation to distinguish
    // relaxed and default versions of mocks.
    @Test
    fun `method call should be verifiable`() {
        service.foo()

        verify { service.foo() }
    }
}
