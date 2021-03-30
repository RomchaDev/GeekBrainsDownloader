package downloaders.course_downloaders.url_downloader

import downloaders.course_downloaders.ICourseDownloader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

interface IUrlCourseDownloader : ICourseDownloader {
    suspend fun downloadCourse(
        firstLessonUrlString: String,
        lessonsNumber: Int,
        scope: CoroutineScope = GlobalScope
    )
}