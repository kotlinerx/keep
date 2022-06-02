package keep.keeper

import keep.keeper.context.KeepContext
import keep.keeper.source.JDBCSource
import keep.keeper.source.buildJDBCSource
import keep.keeper.sqlscope.SqlScope

interface Keeper {
    val context: KeepContext
    operator fun <T> invoke(startTransaction: Boolean = true, block: SqlScope.() -> T): T
}

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
}