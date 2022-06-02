package keep.analyzer

import keep.sql.*
import keep.sql.statement.*

interface SqlAnalyzer<S : Sql<*>> : HasTag {
    fun analyze(sql: S): SqlPrimitives
}

abstract class CreateAnalyzer : SqlAnalyzer<Create<*>> {
    override val tag: Tag = CreateTag
}

abstract class DropAnalyzer : SqlAnalyzer<Drop<*>> {
    override val tag: Tag = DropTag
}

abstract class InsertAnalyzer : SqlAnalyzer<Insert<*>> {
    override val tag: Tag = InsertTag
}

abstract class UpdateAnalyzer : SqlAnalyzer<Update<*>> {
    override val tag: Tag = UpdateTag
}

abstract class SelectAnalyzer : SqlAnalyzer<Select<*>> {
    override val tag: Tag = SelectTag
}

abstract class DeleteAnalyzer : SqlAnalyzer<Delete<*>> {
    override val tag: Tag = DeleteTag
}
