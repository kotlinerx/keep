package keep.analyzer

import keep.table.Column
import java.sql.JDBCType

val jdbcStringArray = arrayOf(
    JDBCType.VARCHAR, JDBCType.NVARCHAR, JDBCType.LONGVARCHAR, JDBCType.LONGNVARCHAR
)

fun JDBCType.isString(): Boolean = jdbcStringArray.contains(this)
fun Column<*>.typeString(): String = if (this.sqlType.isString()) {
    sqlType.name + "(${length})"
} else {
    sqlType.name
}

fun Column<*>.defaultDefinition(): String = buildString {
    append(label).append(" ").append(this@defaultDefinition.typeString())
    if (primary) append(" primary key")
    if (auto) append(" autoincrement")
    if (notNull) append(" not null")
    if (default != null) {
        append(" default")
        if (sqlType.isString()) {
            append(" '$default'")
        } else {
            append(" $default")
        }
    }
}

