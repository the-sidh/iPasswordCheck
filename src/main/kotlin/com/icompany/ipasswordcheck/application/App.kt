@file:JvmName("Example")

package com.icompany.ipasswordcheck.application

import com.icompany.ipasswordcheck.application.config.modules.*
import com.icompany.ipasswordcheck.application.web.routes.ValidatePasswordRoute
import com.icompany.ipasswordcheck.domain.exceptions.APIException
import io.javalin.Javalin
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject


object App : KoinComponent {
    private lateinit var app: Javalin
    private val validatePasswordRoute: ValidatePasswordRoute by inject()
    fun start(extraProperties: Map<String, Any> = emptyMap()) {

        StandAloneContext.startKoin(
            listOf(
                routesModule,
                serviceModule,
                controllersModule
            ),
            useEnvironmentProperties = true,
            useKoinPropertiesFile = true,
            extraProperties = extraProperties
        )

        app = Javalin.create().apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            exception(APIException::class.java) { e, ctx -> e.handleError(ctx) }
            error(404) { ctx -> ctx.json("not found") }
        }.start(7000)

        app.routes {
            validatePasswordRoute.register()
        }
    }

    fun shutdown() {
        app.stop()
        StandAloneContext.stopKoin()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        start()
    }
}
