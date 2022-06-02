package keep.sql

import keep.table.Column

interface SetScope {
    infix fun <T : Any> Column<T>.eq(value: T?)
}