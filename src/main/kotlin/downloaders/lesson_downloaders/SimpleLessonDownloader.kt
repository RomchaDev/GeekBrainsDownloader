package downloaders.lesson_downloaders

import downloaders.file_downloaders.IBitFileDownloader
import entity.Lesson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import logger.ILogger
import validateWithoutSlash
import validations.mapUrls
import java.io.File

class SimpleLessonDownloader(
    private val fileDownloader: IBitFileDownloader,
    private val videoName: String = DEFAULT_VIDEO_NAME,
    private val docName: String = DEFAULT_DOC_NAME,
    private val logger: ILogger
) : ILessonDownloader {

    override suspend fun downloadLesson(lesson: Lesson, path: String, downloadScope: CoroutineScope) {
        val validated = path.validateWithoutSlash()

        val root = File(validated)

        root.mkdirs()

        val jobs = mutableListOf<Job>()

        for (pair in mapUrls(lesson)) {
            val fieldName = pair.key.split('.')[0]

            val job = downloadScope.launch(Dispatchers.IO) {
                try {
                    val start = System.currentTimeMillis()

                    fileDownloader.downloadFileByUrl(
                        pair.value,
                        "$validated/$fieldName"
                    )

                    val end = System.currentTimeMillis()

                    logger.logSuccess(
                        "$fieldName ${lesson.name} downloaded (${end - start} ms)\n" +
                                "      link: ${pair.value}"
                    )
                } catch (e: Throwable) {
                    logger.logFailure("Error while downloading $fieldName ${lesson.name}:")
                    e.printStackTrace()
                }
            }

            jobs.add(job)

        }

        jobs.forEach { it.join() }
    }

    companion object {
        const val DEFAULT_VIDEO_NAME = "video.mp4"
        const val DEFAULT_DOC_NAME = "doc.pdf"
    }
}