import downloaders.courses_set_downloaders.instruction_downloader.CoursesSetByInstructionDownloader
import instruction_interpreters.course_interpreters.CourseInstructionsInterpreter
import kotlinx.coroutines.runBlocking
import launcher.COOKIE
import logger.MainLogger

fun main() {
    val logger = MainLogger("logs.txt")

    val begin = System.currentTimeMillis()

    val downloader = CoursesSetByInstructionDownloader("lessons", COOKIE, logger)

    runBlocking {
        downloader.downloadByInstructionInterpreter(CourseInstructionsInterpreter("instructions.txt"))
    }

    val end = System.currentTimeMillis()

    logger.logSuccess("Total time: ${end - begin} ms")

    logger.logFailureStatistics()

    logger.close()
}