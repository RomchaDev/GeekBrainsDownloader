package instruction_interpreters.lesson_interpreters

import entity.Lesson
import instruction_interpreters.IInstructionInterpreter

class LessonsInstructionsInterpreter(private val lessons: List<Lesson>) : IInstructionInterpreter<Lesson> {
    override fun getEntitiesList() = lessons
}