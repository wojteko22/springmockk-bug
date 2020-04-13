package com.example.springmockkbug

import com.ninjasquad.springmockk.MockkBean
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TestWithRelaxedMock {

    @MockkBean(relaxed = true)
    private lateinit var service: ExampleService

    @Test
    fun `method call should be verifiable`() {
        service.foo()

        verify { service.foo() }
    }
}
