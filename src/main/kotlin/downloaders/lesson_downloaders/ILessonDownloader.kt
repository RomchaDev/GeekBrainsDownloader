package downloaders.lesson_downloaders

import entity.Lesson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

interface ILessonDownloader {
    suspend fun downloadLesson(
        lesson: Lesson,
        path: String,
        downloadScope: CoroutineScope = GlobalScope
    )
}