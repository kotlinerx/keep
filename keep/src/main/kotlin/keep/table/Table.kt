package keep.table

open class Table(private val namePolicy: NamePolicy) : TableScope, NamePolicy by namePolicy {
    constructor(name: String) : this(SimpleNamePolicy(name))

    override val columns: MutableList<Column<*>> = ArrayList()
    override fun <T : Any> registry(column: Column<T>): Column<T> {
        columns.add(column)
        return column
    }

}