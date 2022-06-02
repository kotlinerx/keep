package keep.analyzer

import keep.sql.ColumnValue

class SqlPrimitives(
    val preparedSql: String,
    val parameters: List<ColumnValue<*>>,
)
