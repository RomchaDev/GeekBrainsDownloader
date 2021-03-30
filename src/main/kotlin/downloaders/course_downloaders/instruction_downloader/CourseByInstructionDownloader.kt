package downloaders.course_downloaders.instruction_downloader

import downloaders.lesson_downloaders.ILessonDownloader
import entity.Lesson
import instruction_interpreters.IInstructionInterpreter
import kotlinx.coroutines.*
import validateWithSlash

class CourseByInstructionDownloader(
    private val root: String? = null,
    private val lessonDownloader: ILessonDownloader,
) : IDownloaderByInstruction<Lesson> {

    override suspend fun downloadByInstructionInterpreter(
        interpreter: IInstructionInterpreter<Lesson>,
        downloadPath: String?,
        downloadScope: CoroutineScope
    ) {
        downloadPath!!.validateWithSlash()

        val lessons = interpreter.getEntitiesList()

        //val jobs = mutableListOf<Job>()

        lessons.forEach { lesson ->
            val curPath = "$downloadPath/${lesson.name}"

            //val job = downloadScope.launch(Dispatchers.IO) {
            lessonDownloader.downloadLesson(lesson, curPath)
            // }

            //jobs.add(job)
        }

        //jobs.forEach { it.join() }
    }

    override suspend fun downloadByInstructionInterpreter(
        interpreter: IInstructionInterpreter<Lesson>,
        downloadScope: CoroutineScope
    ) {
        downloadByInstructionInterpreter(interpreter, root, downloadScope)
    }

}