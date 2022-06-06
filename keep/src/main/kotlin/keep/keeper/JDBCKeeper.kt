package keep.keeper

import keep.keeper.context.KeepContext
import keep.keeper.source.JDBCSource
import keep.keeper.source.buildJDBCSource
import keep.keeper.sqlscope.QueryScope
import keep.keeper.mapper.ResultMapper
import keep.keeper.sqlscope.SqlScope
import keep.sql.Condition
import keep.sql.WhereScope
import keep.table.Table
import kotlin.reflect.KClass

class JDBCKeeper(
    override val context: KeepContext,
) : Keeper {
    private val source: JDBCSource = buildJDBCSource(context)
    override operator fun <T> invoke(startTransaction: Boolean, block: SqlScope.() -> T): T {
        val session = source.obtainSession()
        val transaction = session.obtainTransaction()
        return try {
            transaction.start()
            val result = transaction.obtainSqlScope().block()
            transaction.commit()
            result
        } catch (e: Exception) {
            transaction.rollback()
            throw e
        } finally {
            source.close(session)
        }
    }

    override val defaultMapper: ResultMapper get() = context.mapper

    override fun <E : Any> obtainQueryScope(kClass: KClass<E>, mapper: ResultMapper?): QueryScope<E> =
        object : QueryScope<E> {
            override fun <T : Table> from(table: T, block: WhereScope.(T) -> Condition): List<E> {
                return invoke(false) {
                    val result = select(table) {
                        where(block)
                    }
                    val m: ResultMapper = mapper ?: defaultMapper
                    m.map(result, kClass)
                }
            }
        }
}