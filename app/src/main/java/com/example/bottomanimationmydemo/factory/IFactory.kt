package com.example.bottomanimationmydemo.factory

interface IFactory<T, R> {
    fun create(data: T): R
}