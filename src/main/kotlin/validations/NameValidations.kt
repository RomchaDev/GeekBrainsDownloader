fun String.validateWithSlash() =
    if (endsWith('/'))
        plus('/')
    else this


fun String.validateWithoutSlash() =
    if (endsWith('/'))
        this.dropLast(1)
    else this

fun validateDocLink(link: String): String {
    var res = link.replace("drive", "docs")
    res = res.replace("open?id=", "document/d/")

    val parts = res.split("/edit")

    res = parts[0]

    return res.plus("/export?format=pdf")
}

fun validateLessonName(name: String): String {
    val builder: StringBuilder = StringBuilder()

    name.map map@{ char ->
        when (char) {
            '.' -> return@map
            ' ' -> builder.append('_')
            else -> builder.append(char)
        }
    }

    val str = builder.toString()

    return if (str.isNotBlank()) str
    else throw IllegalStateException("entity.Lesson name cannot be blank or contain only '.'")
}