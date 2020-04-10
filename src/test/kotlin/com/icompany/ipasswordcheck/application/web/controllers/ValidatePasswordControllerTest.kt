package com.icompany.ipasswordcheck.application.web.controllers

import com.icompany.ipasswordcheck.domain.entities.PasswordWrapper
import com.icompany.ipasswordcheck.domain.entities.ValidationResponse
import com.icompany.ipasswordcheck.domain.exceptions.InvalidBodySuppliedException
import com.icompany.ipasswordcheck.domain.services.ValidatePasswordService
import com.icompany.ipasswordcheck.samples.samplePasswordWrapper
import io.javalin.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ValidatePasswordControllerTest {
    private val ctx = mockk<Context>(relaxed = true)
    @Test
    fun `given a requested to validate the password when supplied a valid password wrapper, should call the appropriate service function`() {
        val service = mockk<ValidatePasswordService>()
        val passwordWrapper = samplePasswordWrapper()
        every {ctx.body<PasswordWrapper>()}returns passwordWrapper
        every { service.validate(passwordWrapper.password) } returns ValidationResponse(true)
        val controller = ValidatePasswordController(service)
        controller.validate(ctx)
        verify(exactly = 1) { service.validate(passwordWrapper.password) }
    }

    @Test
    fun `given the request was bad formatted, should throw an exception`() {
        val service = mockk<ValidatePasswordService>()
        every {ctx.body<PasswordWrapper>()} throws Exception()
        val controller = ValidatePasswordController(service)
        assertThrows<InvalidBodySuppliedException> {  controller.validate(ctx) }
    }

}