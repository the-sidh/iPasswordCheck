package com.icompany.ipasswordcheck.application.web.routes

import com.icompany.ipasswordcheck.application.web.controllers.ValidatePasswordController
import io.javalin.apibuilder.ApiBuilder

class ValidatePasswordRoute(val controller: ValidatePasswordController) {
    fun register() {
        ApiBuilder.post(
            "/validate/",
            controller::validate
        )
    }
}