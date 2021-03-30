package info_html_getters

class FileLinkFromHtmlGetter : IFileLinkGetter {
    override fun findLinkByName(name: String, html: String): String {
        val bigParts = html.split(name)
        val smallParts = bigParts.first().split("<a")
        val hrefSplit = smallParts.last().split("href=")
        return hrefSplit[1].split('"')[1]
    }
}