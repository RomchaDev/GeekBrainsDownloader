package instruction_interpreters.course_interpreters

import entity.Course
import instruction_interpreters.AbstractFileInterpreter

class CourseInstructionsInterpreter(path: String) : AbstractFileInterpreter<Course>(path) {
    override fun getEntityFromStringList(list: List<String>) =
        Course(list[0], list[1].toInt())
}