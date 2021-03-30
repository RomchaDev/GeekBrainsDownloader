package instruction_interpreters

import entity.Lesson
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

abstract class AbstractFileInterpreter<T>(private val path: String) : IInstructionInterpreter<T> {
    override fun getEntitiesList(): List<T> {
        val instructions = File(path)

        if (!instructions.exists())
            throw IllegalStateException("Instructions file has been not created")

        val reader = BufferedReader(FileReader(instructions))

        val lessonsList = mutableListOf<T>()

        reader.lineSequence().forEach { line ->
            val parts = line.split(' ')
            lessonsList.add(getEntityFromStringList(parts))
        }

        return lessonsList
    }

    protected abstract fun getEntityFromStringList(list: List<String>): T
}
