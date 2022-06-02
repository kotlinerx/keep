package keep.keeper.sqlscope

import keep.keeper.context.KeepContext
import keep.keeper.mapper.ResultMapper
import keep.sql.statement.*
import keep.table.Table
import java.sql.ResultSet
import kotlin.reflect.KClass

interface SqlScope : SaveScope {
    val context: KeepContext
    fun <T : Table> drop(table: T)
    fun <T : Table> create(table: T)
    fun <T : Table> insert(table: T, block: Insert<T>.() -> Unit)
    fun <T : Table> update(table: T, block: Update<T>.() -> Unit): Int
    fun <T : Table> select(table: T, block: Select<T>.() -> Unit): ResultSet
    fun <T : Table> delete(table: T, block: Delete<T>.() -> Unit): Int

    fun <T : Any> ResultSet.map(kClass: KClass<T>, mapper: ResultMapper? = null): List<T> {
        val map: ResultMapper = mapper ?: context.mapper
        return map.map(this, kClass)
    }
}


