package downloaders.course_downloaders.instruction_downloader

import downloaders.course_downloaders.ICourseDownloader
import instruction_interpreters.IInstructionInterpreter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

interface IDownloaderByInstruction<T> : ICourseDownloader {
    suspend fun downloadByInstructionInterpreter(
        interpreter: IInstructionInterpreter<T>,
        downloadPath: String?,
        downloadScope: CoroutineScope = GlobalScope
    )

    suspend fun downloadByInstructionInterpreter(
        interpreter: IInstructionInterpreter<T>,
        downloadScope: CoroutineScope = GlobalScope
    )
}