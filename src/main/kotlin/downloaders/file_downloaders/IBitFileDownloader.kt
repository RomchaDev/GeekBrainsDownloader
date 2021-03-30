package downloaders.file_downloaders

interface IBitFileDownloader {
    fun downloadFileByUrl(urlString: String, path: String)
}