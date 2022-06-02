package keep.keeper.executor

import keep.analyzer.SqlAnalyzersManager
import keep.analyzer.SqlPrimitives
import keep.keeper.context.KeepContext
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class JDBCExecutor(
    private val context: KeepContext,
    private val connection: Connection,
) : BaseSqlExecutor() {
    override val analyzers: SqlAnalyzersManager = context.analyzersManager
    override fun runDrop(sqlPrimitives: SqlPrimitives) {
        val ps = prepareStatement(sqlPrimitives)
        ps.execute()
    }

    override fun runCreate(sqlPrimitives: SqlPrimitives) {
        val ps = prepareStatement(sqlPrimitives)
        ps.execute()
    }

    override fun runInsert(sqlPrimitives: SqlPrimitives) {
        val ps = prepareStatement(sqlPrimitives)
        ps.execute()
    }

    override fun runUpdate(sqlPrimitives: SqlPrimitives): Int {
        val ps = prepareStatement(sqlPrimitives)
        return ps.executeUpdate()
    }

    override fun runSelect(sqlPrimitives: SqlPrimitives): ResultSet {
        val ps = prepareStatement(sqlPrimitives)
        return ps.executeQuery()
    }

    override fun runDelete(sqlPrimitives: SqlPrimitives): Int {
        val ps = prepareStatement(sqlPrimitives)
        return ps.executeUpdate()
    }

    private fun prepareStatement(sqlPrimitives: SqlPrimitives): PreparedStatement {
        val preparedStatement = connection.prepareStatement(sqlPrimitives.preparedSql)
        sqlPrimitives.parameters.forEachIndexed { index, columnValue ->
            preparedStatement.setObject(index + 1, columnValue.value)
        }
        return preparedStatement
    }

}