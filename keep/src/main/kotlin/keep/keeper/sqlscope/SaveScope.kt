package keep.keeper.sqlscope

import keep.table.Table

interface SaveScope {
    fun <E : Any, T : Table> add(table: T, vararg entities: E)
    fun <E : Any, T : Table> add(table: T, list: List<E>)
    fun <E : Any, T : Table> addOne(table: T, entity: E)
}