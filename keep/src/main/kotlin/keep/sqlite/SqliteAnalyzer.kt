package keep.sqlite

import keep.analyzer.*
import keep.analyzer.defaultDefinition
import keep.sql.*
import keep.sql.statement.*


class SqliteCreateAnalyzer : CreateAnalyzer() {
    override fun analyze(sql: Create<*>): SqlPrimitives {
        var firstColumn = true
        val table = sql.table
        val sqlString = buildString {
            append("CREATE table if not exists ${table.baseName()} (").appendLine()
            table.columns.forEach { column ->
                if (firstColumn) {
                    firstColumn = false
                } else {
                    append(",").appendLine()
                }
                append(column.defaultDefinition())
            }
            appendLine()
            append(");")
        }
        return SqlPrimitives(sqlString, emptyList())
    }

}

class SqliteDropAnalyzer : DropAnalyzer() {
    override fun analyze(sql: Drop<*>): SqlPrimitives {
        val sqlString = "DROP table if exists ${sql.table.baseName()};"
        return SqlPrimitives(sqlString, emptyList())
    }
}

class SqliteInsertAnalyzer : InsertAnalyzer() {
    override fun analyze(sql: Insert<*>): SqlPrimitives {
        val table = sql.table
        val columnNameList: MutableList<String> = ArrayList()
        val columnValueList: MutableList<String> = ArrayList()

        sql.obtainValues().forEach {
            columnNameList.add(it.column.label)
            columnValueList.add("?")
        }
        val sqlString = buildString {
            append("Insert into ${table.baseName()} (")
            append(columnNameList.joinToString(","))
            append(")values(")
            append(columnValueList.joinToString(","))
            append(");")
        }
        return SqlPrimitives(sqlString, sql.obtainValues())
    }
}

class SqliteUpdateAnalyzer : UpdateAnalyzer(), ConditionAnalyzer {
    override fun analyze(sql: Update<*>): SqlPrimitives {
        val table = sql.table
        val parameterList = ArrayList<ColumnValue<*>>()
        val sqlString = buildString {
            append("update ${table.baseName()} set ")
            val setSqlString = sql.obtainValues().joinToString(",") {
                parameterList.add(it)
                it.column.label + " = ?"
            }
            append(setSqlString)
            val conditionString = analyseCondition(sql.obtainCondition(), parameterList::add)
            append(conditionString)
        }
        return SqlPrimitives(sqlString, parameterList)
    }
}

class SqliteSelectAnalyzer : SelectAnalyzer(), ConditionAnalyzer {
    override fun analyze(sql: Select<*>): SqlPrimitives {
        val table = sql.table
        val parameterList = ArrayList<ColumnValue<*>>()
        val sqlString = buildString {
            append("select ")
            if (sql.distinct) {
                append("distinct ")
            }
            table.columns.joinToString(",") { it.label }.also(::append)
            append(" from ${table.baseName()} ")
            val conditionString = analyseCondition(sql.obtainCondition(), parameterList::add)
            append(conditionString)
            append(";")
        }
        return SqlPrimitives(sqlString, parameterList)
    }
}

class SqliteDeleteAnalyzer : DeleteAnalyzer(), ConditionAnalyzer {
    override fun analyze(sql: Delete<*>): SqlPrimitives {
        val table = sql.table
        val parameterList = ArrayList<ColumnValue<*>>()
        val sqlstring = buildString {
            append("delete from ${table.baseName()} ")
            val conditionString = analyseCondition(sql.obtainCondition(), parameterList::add)
            append(conditionString)
        }
        return SqlPrimitives(sqlstring, parameterList)
    }
}

fun sqliteAnalyzers(): List<SqlAnalyzer<*>> =  listOf(
    SqliteCreateAnalyzer(),
    SqliteDropAnalyzer(),
    SqliteInsertAnalyzer(),
    SqliteUpdateAnalyzer(),
    SqliteSelectAnalyzer(),
    SqliteDeleteAnalyzer(),
)
