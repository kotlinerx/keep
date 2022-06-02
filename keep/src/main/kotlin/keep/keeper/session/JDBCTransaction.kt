package keep.keeper.session

import keep.keeper.context.KeepContext
import keep.keeper.sqlscope.SqlScope
import keep.keeper.executor.JDBCExecutor
import keep.keeper.sqlscope.CommonSqlScope
import java.sql.Connection

class JDBCTransaction(
    private val context: KeepContext,
    override val useTransaction: Boolean,
    private val connection: Connection,
) : Transaction {

    override fun start() {
        connection.autoCommit = !useTransaction
    }

    override fun rollback() {
        if (useTransaction) connection.rollback()
    }

    override fun commit() {
        if (useTransaction) connection.commit()
    }

    override fun obtainSqlScope(): SqlScope {
        val executor = JDBCExecutor(context = context, connection = connection)
        return CommonSqlScope(
            context = context, executor = executor
        )
    }
}