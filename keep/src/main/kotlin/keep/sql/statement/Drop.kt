package keep.sql.statement

import keep.sql.DropTag
import keep.sql.Sql
import keep.sql.Tag
import keep.table.Table

class Drop<T : Table>(override val table: T) : Sql<T> {
    override val tag: Tag = DropTag
}
