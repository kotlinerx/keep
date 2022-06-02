package keep.table

import java.sql.JDBCType

interface TableScope : ColumnContainer {
    fun String.integer(): ColumnDelegate<Int> {
        return ColumnDelegate(
            kClass = Int::class, label = this, index = columns.size, sqlType = JDBCType.INTEGER
        ).also { registry(it) }
    }

    fun String.varchar(length: Int): ColumnDelegate<String> {
        return ColumnDelegate(
            kClass = String::class, label = this, index = columns.size, sqlType = JDBCType.VARCHAR
        ).also {
            registry(it)
            it.length = length
        }
    }

    fun String.boolean(): ColumnDelegate<Boolean> {
        return ColumnDelegate(
            kClass = Boolean::class, label = this, index = columns.size, sqlType = JDBCType.BOOLEAN
        ).also { registry(it) }
    }
}
