package keep.sql

interface Tag {
    val name: String
}

interface HasTag {
    val tag: Tag
}


const val SQL_CREATE = "sql_create"

object CreateTag : Tag {
    override val name: String = SQL_CREATE
}

const val SQL_DROP = "sql_drop"

object DropTag : Tag {
    override val name: String = SQL_DROP
}

const val SQL_INSERT = "sql_insert"

object InsertTag : Tag {
    override val name: String = SQL_INSERT
}

const val SQL_UPDATE = "sql_update"

object UpdateTag : Tag {
    override val name: String = SQL_UPDATE
}

const val SQL_SELECT = "sql_select"

object DeleteTag : Tag {
    override val name: String = SQL_DELETE
}

const val SQL_DELETE = "sql_delete"

object SelectTag : Tag {
    override val name: String = SQL_SELECT
}