package keep.keeper.executor

import keep.analyzer.SqlAnalyzer
import keep.analyzer.SqlAnalyzersManager
import keep.analyzer.SqlPrimitives
import keep.sql.Sql
import keep.sql.statement.*
import keep.table.Table
import mu.KotlinLogging
import java.sql.ResultSet

private val logger = KotlinLogging.logger { }

abstract class BaseSqlExecutor : SqlExecutor {
    abstract val analyzers: SqlAnalyzersManager

    @Suppress("unchecked_cast")
    protected fun <S : Sql<*>> analyzeSql(sql: S): SqlPrimitives {
        val analyzer = analyzers.provideAnalyzer(sql.tag) as SqlAnalyzer<S>
        val sqlPrimitives = analyzer.analyze(sql)
        logger.debug { sqlPrimitives.preparedSql }
        logger.debug {
            "param:${
                sqlPrimitives.parameters.joinToString(",") {
                    it.column.label + "=" + it.value
                }
            } "
        }
        return sqlPrimitives
    }

    override fun <T : Table> executeDrop(sql: Drop<T>) {
        val sqlPrimitives = analyzeSql(sql)
        runDrop(sqlPrimitives)
    }

    abstract fun runDrop(sqlPrimitives: SqlPrimitives)

    override fun <T : Table> executeCreate(sql: Create<T>) {
        val sqlPrimitives = analyzeSql(sql)
        runCreate(sqlPrimitives)
    }

    abstract fun runCreate(sqlPrimitives: SqlPrimitives)

    override fun <T : Table> executeInsert(sql: Insert<T>): ResultSet {
        val sqlPrimitives = analyzeSql(sql)
        return runInsert(sqlPrimitives)
    }

    abstract fun runInsert(sqlPrimitives: SqlPrimitives): ResultSet

    override fun <T : Table> executeUpdate(sql: Update<T>): Int {
        val sqlPrimitives = analyzeSql(sql)
        return runUpdate(sqlPrimitives)
    }

    abstract fun runUpdate(sqlPrimitives: SqlPrimitives): Int

    override fun <T : Table> executeSelect(sql: Select<T>): ResultSet {
        val sqlPrimitives = analyzeSql(sql)
        return runSelect(sqlPrimitives)
    }

    abstract fun runSelect(sqlPrimitives: SqlPrimitives): ResultSet

    override fun <T : Table> executeDelete(sql: Delete<T>): Int {
        val sqlPrimitives = analyzeSql(sql)
        return runDelete(sqlPrimitives)
    }

    abstract fun runDelete(sqlPrimitives: SqlPrimitives): Int
}