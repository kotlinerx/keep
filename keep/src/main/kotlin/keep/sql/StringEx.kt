package keep.sql

fun <T> T.toSqlValue(): String = if (this is String) {
    "'${this}'"
} else {
    this.toString()
}

fun String.quoted(): String = "'$this'"