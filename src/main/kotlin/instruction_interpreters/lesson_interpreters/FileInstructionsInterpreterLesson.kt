package instruction_interpreters.lesson_interpreters

import entity.Lesson
import instruction_interpreters.AbstractFileInterpreter
import instruction_interpreters.IInstructionInterpreter
import validateDocLink
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class FileInstructionsInterpreterLesson(path: String) : AbstractFileInterpreter<Lesson>(path) {
    override fun getEntityFromStringList(list: List<String>) = Lesson(list[0], list[1])
}