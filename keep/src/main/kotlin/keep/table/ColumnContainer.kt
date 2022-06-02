package keep.table

interface ColumnContainer {
    val columns: List<Column<*>>
    fun <T : Any> registry(column: Column<T>): Column<T>
}