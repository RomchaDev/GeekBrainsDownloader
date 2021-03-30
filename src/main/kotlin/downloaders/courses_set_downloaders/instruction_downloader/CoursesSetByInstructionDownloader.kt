package downloaders.courses_set_downloaders.instruction_downloader

import downloaders.course_downloaders.url_downloader.UrlCourseDownloader
import downloaders.html_downloader.CookieHtmlDownloader
import entity.Course
import info_html_getters.CourseNameFromHtmlGetter
import instruction_interpreters.IInstructionInterpreter
import kotlinx.coroutines.CoroutineScope
import logger.ILogger
import validateWithoutSlash

class CoursesSetByInstructionDownloader(
    private val root: String? = null,
    private val cookie: String,
    private val logger: ILogger
) : ICoursesSetByInstructionDownloader {

    override suspend fun downloadByInstructionInterpreter(
        interpreter: IInstructionInterpreter<Course>,
        downloadPath: String?,
        downloadScope: CoroutineScope
    ) {
        val validated = downloadPath!!.validateWithoutSlash()

        val nameGetter = CourseNameFromHtmlGetter()

        //val jobs = mutableListOf<Job>()
        interpreter.getEntitiesList().forEach { course ->
            /*val job = downloadScope.launch(Dispatchers.IO) {*/
            val start = System.currentTimeMillis()
            val html = CookieHtmlDownloader(cookie).getTextByUrl(course.firstLessonLink)
            val courseName = nameGetter.getName(html)
            logger.logSuccess("Course name found: $courseName")
            val courseDownloader = UrlCourseDownloader("$validated/$courseName", cookie, logger)
            courseDownloader.downloadCourse(course.firstLessonLink, course.lessonsNumber /*this*/)
            val end = System.currentTimeMillis()
            logger.logSuccess("Course $courseName downloaded (${end - start} ms)")
            //}

            //jobs.add(job)
        }

        //jobs.forEach { it.join() }
    }

    override suspend fun downloadByInstructionInterpreter(
        interpreter: IInstructionInterpreter<Course>,
        downloadScope: CoroutineScope
    ) {
        downloadByInstructionInterpreter(interpreter, root, downloadScope)
    }
}