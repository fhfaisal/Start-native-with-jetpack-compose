package com.example.myapplication.utils

object AppValidators {

    // Empty Text Validation
    fun validateEmptyText(fieldName: String?, value: String?): String? {
        if (value.isNullOrEmpty()) {
            return "$fieldName is required."
        }
        return null
    }

    // Certificate Validation
    fun validateCertificate(value: String): String? {
        return if (value.isEmpty()) {
            "Please enter your BMDC Certificate Number"
        } else {
            null
        }
    }

    // Email Validation
    fun validateEmail(value: String?): String? {
        if (value.isNullOrEmpty()) {
            return "Email is required."
        }

        // Regular expression for email validation
        val emailRegex = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        if (!emailRegex.matches(value)) {
            return "Invalid email address."
        }
        return null
    }

    // Password Validation
    fun validatePassword(value: String?): String? {
        if (value.isNullOrEmpty()) {
            return "Password is required."
        }
        // Check for minimum password length
        if (value.length < 6) {
            return "Password must be at least 6 characters long."
        }
        return null
    }

    // Confirm Password Validation
    fun validateConfirmPassword(value: String?, newPassword: String?): String? {
        if (value.isNullOrEmpty()) {
            return "Password is required."
        }
        // Check for minimum password length
        if (value.length < 6) {
            return "Password must be at least 6 characters long."
        }
        if (newPassword != value) {
            return "Password mismatch. Re-enter new password."
        }
        return null
    }

    // Phone Number Validation
    fun validatePhoneNumber(value: String?): String? {
        if (value.isNullOrEmpty()) {
            return "Phone number is required."
        }
        // Regular expression for phone number validation (assuming 11-digit format)
        val phoneRegex = Regex("^\\d{11}\$")
        if (!phoneRegex.matches(value)) {
            return "Invalid phone number format (11 digits required)."
        }
        return null
    }

    // Add more custom validators as needed for your specific requirements.
}