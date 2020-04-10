package com.icompany.ipasswordcheck.domain.services

import com.icompany.ipasswordcheck.samples.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Duration

class ValidatePasswordServiceTest() {

    @Test
    fun `Given a valid password, when validating if is valid should return true`() {
        val service = ValidatePasswordService()
        assertTrue(service.validate(VALID_PASSWORD).isValid)
    }

    @Test
    fun `Given a password missing a digit, when validating if is valid should return false`() {
        val service = ValidatePasswordService()
        assertFalse(service.validate(MISSING_DIGIT).isValid)
    }

    @Test
    fun `Given a password missing a upper case letter, when validating if is valid should return false`() {
        val service = ValidatePasswordService()
        assertFalse(service.validate(MISSING_UPPERCASE).isValid)
    }

    @Test
    fun `Given a password missing a lower case letter, when validating if is valid should return false`() {
        val service = ValidatePasswordService()
        assertFalse(service.validate(MISSING_LOWERCASE).isValid)
    }

    @Test
    fun `Given a password missing a special character, when validating if is valid should return false`() {
        val service = ValidatePasswordService()
        assertFalse(service.validate(MISSING_SPECIAL_CHARACTER).isValid)
    }

    @Test
    fun `Given a password that is too short, when validating if is valid should return false`() {
        val service = ValidatePasswordService()
        assertFalse(service.validate(TOO_SHORT).isValid)
    }

    @Test
    fun `Given a ridiculous big invalid password, when validating if is valid should return false under one second`() {
        val service = ValidatePasswordService()
        var result =true
        assertTimeout(Duration.ofMillis(1000)){
            result = service.validate(BIG_INVALID_PASSWORD).isValid
        }
        assertFalse(result)
    }

    @Test
    fun `Given a ridiculous big valid password, when validating if is valid should return true under one second`() {
        val service = ValidatePasswordService()
        var result =false
        assertTimeout(Duration.ofMillis(1000)){
            result = service.validate(BIG_VALID_PASSWORD).isValid
        }
        assertTrue(result)
    }
}