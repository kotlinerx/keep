package keep.sql.statement

import keep.sql.CreateTag
import keep.sql.Sql
import keep.sql.Tag
import keep.table.Table

class Create<T : Table>(override val table: T) : Sql<T> {
    override val tag: Tag = CreateTag
}