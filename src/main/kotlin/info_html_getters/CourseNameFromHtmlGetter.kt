package info_html_getters

class CourseNameFromHtmlGetter : INameFromHtmlGetter {
    override fun getName(html: String): String {
        val bigParts = html.split("\"course-title\" title=\"")
        val smallParts = bigParts[1].split('"')
        return smallParts.first()
    }
}