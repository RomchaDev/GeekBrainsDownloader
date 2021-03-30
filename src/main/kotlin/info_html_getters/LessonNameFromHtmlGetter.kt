package info_html_getters

class LessonNameFromHtmlGetter : INameFromHtmlGetter {
    override fun getName(html: String): String {
        return try {
            val bigParts = html.split("title-block\"><h3 class=\"title\">")
            val smallParts = bigParts[1].split("<")
            smallParts.first()
        } catch (e: Throwable) {
            "$UNKNOWN${++unknownCounter}"
        }
    }

    companion object {
        private const val UNKNOWN = "Unknown"
        private var unknownCounter = 0
    }
}