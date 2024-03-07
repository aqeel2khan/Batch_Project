package com.dev.batchfinal.factory

interface IFactory<T, R> {
    fun create(data: T): R
}