package com.icompany.ipasswordcheck.application.web.controllers

import com.icompany.ipasswordcheck.domain.entities.PasswordWrapper
import com.icompany.ipasswordcheck.domain.exceptions.InvalidBodySuppliedException
import com.icompany.ipasswordcheck.domain.services.ValidatePasswordService
import io.javalin.Context
import org.eclipse.jetty.http.HttpStatus
import java.lang.Exception

class ValidatePasswordController(private val service: ValidatePasswordService) {
    fun validate(ctx: Context) {
        val passwordWrapper = try {
            ctx.body<PasswordWrapper>()
        } catch (e: Exception) {
            throw InvalidBodySuppliedException()
        }
        val validationResponse = service.validate(passwordWrapper.password)
        ctx.json(validationResponse).status(HttpStatus.OK_200)
    }
}