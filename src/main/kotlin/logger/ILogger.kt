package logger

interface ILogger : AutoCloseable{
    fun logSuccess(message: String)

    fun logFailure(message: String)

    fun logFailureStatistics()
}