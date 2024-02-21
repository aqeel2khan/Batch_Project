package com.example.bottomanimationmydemo.network.errorhandling

interface ErrorHandler {
    fun getErrorMessageFrom(ex: Exception): String?
}