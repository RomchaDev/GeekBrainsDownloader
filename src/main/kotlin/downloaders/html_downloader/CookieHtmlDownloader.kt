package downloaders.html_downloader

import java.net.HttpURLConnection
import java.net.URL

class CookieHtmlDownloader(private val cookie: String): ITextByUrlGetter {
    override fun getTextByUrl(urlString: String): String {
        val con = URL(urlString)
            .openConnection() as HttpURLConnection

        con.setRequestProperty(COOKIE_KEY, cookie)

        val reader = con.inputStream.bufferedReader()
        val builder = StringBuilder()

        reader.readLines().forEach { builder.append(it) }

        reader.close()
        con.disconnect()

        return builder.toString()
    }

    companion object {
        private const val COOKIE_KEY = "cookie"
    }
}