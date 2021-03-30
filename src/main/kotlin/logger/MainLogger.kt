package logger

import java.io.File
import java.io.PrintWriter

class MainLogger(logPath: String) : ILogger {
    private val failures = mutableListOf<String>()
    private val logFile = File(logPath)
    private val writer = PrintWriter(logFile)
    init {
        if (!logFile.exists())
            logFile.createNewFile()
    }

    override fun logSuccess(message: String) {
        writelnToAll(message)
    }

    override fun logFailure(message: String) {
        writelnToAll(message)
        failures.add(message)
    }

    override fun logFailureStatistics() {
        writelnToAll("")
        repeat(100) { writeToAll("*") }
        writelnToAll("\n")
        writelnToAll("Failures:")
        writelnToAll("")
        failures.forEach { writelnToAll(it) }
    }

    override fun close() {
        writer.close()
    }

    private fun writelnToFile(message: String) {
        writer.println(message)
    }

    private fun writeToFile(message: String) {
        writer.print(message)
    }

    private fun writelnToAll(message: String) {
        println(message)
        writelnToFile(message)
    }

    private fun writeToAll(message: String) {
        print(message)
        writeToFile(message)
    }
}