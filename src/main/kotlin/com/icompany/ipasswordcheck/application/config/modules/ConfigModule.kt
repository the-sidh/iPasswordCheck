package com.icompany.ipasswordcheck.application.config.modules

import com.icompany.ipasswordcheck.application.web.controllers.ValidatePasswordController
import com.icompany.ipasswordcheck.application.web.routes.ValidatePasswordRoute
import com.icompany.ipasswordcheck.domain.services.ValidatePasswordService
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val routesModule: Module = module {
    single { ValidatePasswordRoute(get()) }
}

val controllersModule: Module = module {
    single { ValidatePasswordController(get()) }
}

val serviceModule: Module = module {
    single { ValidatePasswordService() }
}

