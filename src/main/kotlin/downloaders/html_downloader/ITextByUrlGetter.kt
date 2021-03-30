package downloaders.html_downloader

interface ITextByUrlGetter {
    fun getTextByUrl(urlString: String): String
}