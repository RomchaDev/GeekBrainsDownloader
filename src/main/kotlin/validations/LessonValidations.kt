package validations

import entity.Lesson

fun mapUrls(lesson: Lesson) = mapOf(
    VIDEO to lesson.videoUrl,
    DOC to lesson.docUrl,
)

const val VIDEO = "video.mp4"
const val DOC = "doc.pdf"
const val NAME = "name"
