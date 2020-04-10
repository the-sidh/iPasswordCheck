package com.icompany.ipasswordcheck.web

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.icompany.ipasswordcheck.application.App
import com.icompany.ipasswordcheck.domain.entities.PasswordWrapper
import com.icompany.ipasswordcheck.samples.samplePasswordWrapper
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import org.eclipse.jetty.http.HttpStatus
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.*

class ValidatePasswordTest {

    @Test
    fun `given that the service is running, when a well formatted request for password validation is supplied, should return http status 200 and provide a well-formatted response`() {
        val passwordWrapper = samplePasswordWrapper()
        postAndVerifyBodyResponse(
            passwordWrapper = passwordWrapper,
            expectedStatus = HttpStatus.OK_200,
            expectedValidationStatus = true
        )

    }

    @Test
    fun `given that the service is running, when a badly formatted request for password validation is supplied, should return http status 422`() {
        val passwordWrapper = "{invalid_json:true}"
        postAndVerifyStatus(
            passwordWrapper = passwordWrapper,
            expectedStatus = HttpStatus.UNPROCESSABLE_ENTITY_422
        )
    }


    @Test
    fun `given that the service is running, when a request for password validation is supplied with a valid password, should return http status 200 and provide a well-formatted response`() {
        val passwordWrapper = samplePasswordWrapper().copy(password = "Ab!456789")
        postAndVerifyBodyResponse(
            passwordWrapper = passwordWrapper,
            expectedStatus = HttpStatus.OK_200,
            expectedValidationStatus = true
        )
    }

    @Test
    fun `given that the service is running, when a request for password validation is supplied with a invalid password, should return http status 200 and provide a well-formatted response`() {
        val passwordWrapper = samplePasswordWrapper().copy(password = "Ab456789")
        postAndVerifyBodyResponse(
            passwordWrapper = passwordWrapper,
            expectedStatus = HttpStatus.OK_200,
            expectedValidationStatus = false
        )
    }

    private val objectMapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .registerModule(JavaTimeModule()).setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)

    companion object {

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            RestAssured.requestSpecification = RequestSpecBuilder()
                .setBaseUri("http://localhost:7000")
                .setBasePath("/")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build()

            App.start()
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            App.shutdown()
        }
    }

    fun shutdown() {
        App.shutdown()
    }

    init {
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                shutdown()
            }
        })
    }

    private fun postAndVerifyBodyResponse(
        passwordWrapper: PasswordWrapper,
        expectedStatus: Int,
        expectedValidationStatus: Boolean
    ) = RestAssured.given()
        .contentType(ContentType.JSON)
        .body(objectMapper.writeValueAsBytes(passwordWrapper)).post("/validate").then()
        .statusCode(expectedStatus)
        .body("valid", equalTo(expectedValidationStatus))
        .extract()
        .jsonPath()

    private fun postAndVerifyStatus(
        passwordWrapper: String,
        expectedStatus: Int
    ) = RestAssured.given()
        .contentType(ContentType.JSON)
        .body(passwordWrapper).post("/validate").then()
        .statusCode(expectedStatus)
        .extract()
        .jsonPath()

}
