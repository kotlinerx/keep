package keep.sql

import keep.table.Column

class ColumnValue<T : Any>(
    val column: Column<T>,
    val value: Any?,
) {
    override fun toString(): String {
        return "ColumnValue(${column.label} = $value)"
    }
}