package com.example.vpweek9.soal2.util

import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException
import retrofit2.HttpException

suspend fun <T> safeCall(block: suspend () -> T): Result<T> =
    try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }

fun Throwable.toUserMessage(): String = when (this) {
    is HttpException -> if (code() == 429) "HTTP 429 - Request limit reached" else "HTTP ${code()}"
    is IOException -> message ?: "Tidak ada koneksi internet."
    else -> message ?: "Terjadi kesalahan."
}