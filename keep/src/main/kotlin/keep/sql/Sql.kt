package keep.sql

import keep.table.Column
import keep.table.Table

interface Sql<T : Table> : HasTag {
    val table: T
}
