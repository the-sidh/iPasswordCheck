package com.icompany.ipasswordcheck.domain.services

import com.icompany.ipasswordcheck.domain.entities.ValidationResponse

const val SPECIAL_CHARACTER = "(?=.*[^a-zA-Z0-9 ])"
const val DIGITS = """(?=.*\d)"""
const val LOWER_CASE = "(?=.*[a-z])"
const val UPPER_CASE = "(?=.*[A-Z])"
const val MINIMUM_SIZE = ".{9}"

class ValidatePasswordService {
    fun validate(password: String): ValidationResponse {
        val regex = Regex(
            pattern = "($SPECIAL_CHARACTER" +
                    DIGITS +
                    LOWER_CASE +
                    UPPER_CASE +
                    "$MINIMUM_SIZE)"
        )
        return ValidationResponse(
            regex.containsMatchIn(input = password)
        )
    }
}
