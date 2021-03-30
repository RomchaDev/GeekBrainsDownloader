package downloaders.file_downloaders

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class SimpleFileDownloader : IBitFileDownloader {
    override fun downloadFileByUrl(urlString: String, path: String) {

        val con = URL(urlString)
            .openConnection() as HttpURLConnection

        val input = con.inputStream

        val out = BufferedOutputStream(FileOutputStream(File(path)))

        out.write(input.readAllBytes())

        input.close()
        out.close()
        con.disconnect()
    }
}