package downloaders.course_downloaders.url_downloader

import downloaders.course_downloaders.instruction_downloader.CourseByInstructionDownloader
import downloaders.file_downloaders.SimpleFileDownloader
import downloaders.html_downloader.CookieHtmlDownloader
import downloaders.lesson_downloaders.SimpleLessonDownloader
import entity.Lesson
import info_html_getters.FileLinkFromHtmlGetter
import info_html_getters.LessonNameFromHtmlGetter
import instruction_interpreters.lesson_interpreters.LessonsInstructionsInterpreter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import logger.ILogger
import validateDocLink
import validateWithoutSlash

class UrlCourseDownloader(
    private val root: String,
    private val cookie: String,
    private val logger: ILogger
) : IUrlCourseDownloader {

    override suspend fun downloadCourse(
        firstLessonUrlString: String,
        lessonsNumber: Int,
        scope: CoroutineScope
    ) {
        val lessons = getLessonsList(firstLessonUrlString, lessonsNumber)
        val fileDownloader = SimpleFileDownloader()

        val rootValidated = root.validateWithoutSlash()

        val lessonDownloader = SimpleLessonDownloader(
            fileDownloader = fileDownloader,
            logger = logger
        )

        val downloader = CourseByInstructionDownloader(rootValidated, lessonDownloader)
        downloader.downloadByInstructionInterpreter(LessonsInstructionsInterpreter(lessons), scope)
    }

    private suspend fun getLessonsList(firstLessonUrlString: String, lessonsNumber: Int): List<Lesson> {
        val validated = firstLessonUrlString.validateWithoutSlash()

        val parts = validated.split('/')

        val mainPart = validated.dropLastWhile { it != '/' }
        val firstNum = parts.last().toLong()

        val htmlDownloader = CookieHtmlDownloader(cookie)
        val linkGetter = FileLinkFromHtmlGetter()
        val nameGetter = LessonNameFromHtmlGetter()

        val lessons = mutableListOf<Lesson>()

        for (i in firstNum until firstNum + lessonsNumber) {
            val curUrl = mainPart + i
            val html = htmlDownloader.getTextByUrl(curUrl)

            val videoDeferred = GlobalScope.async {
                val start = System.currentTimeMillis()

                val link = linkGetter.findLinkByName(VIDEO_LINK_NAME, html)

                val end = System.currentTimeMillis()

                logger.logSuccess("Video link ${i + 1} found (${end - start} ms)")

                link
            }

            val docDeferred = GlobalScope.async {
                val start = System.currentTimeMillis()

                val link = validateDocLink(
                    linkGetter.findLinkByName(DOC_LINK_NAME, html)
                )

                val end = System.currentTimeMillis()

                logger.logSuccess("Doc link ${i + 1} found (${end - start} ms)")

                link
            }

            val nameDeferred = GlobalScope.async {
                val start = System.currentTimeMillis()

                val name = nameGetter.getName(html)

                val end = System.currentTimeMillis()

                logger.logSuccess("Name '$name' found (${end - start} ms)")

                name
            }


            lessons.add(
                Lesson(
                    videoUrl = videoDeferred.await(),
                    docUrl = docDeferred.await(),
                    name = nameDeferred.await()
                )
            )
        }

        return lessons
    }

    companion object {
        private const val VIDEO_LINK_NAME = "Запись вебинара"
        private const val DOC_LINK_NAME = "Методичка"
    }
}
