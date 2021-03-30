package info_html_getters

interface IFileLinkGetter : IHtmlGetter{
    fun findLinkByName(name: String, html: String): String
}