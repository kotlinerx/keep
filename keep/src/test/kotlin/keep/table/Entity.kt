package keep.table

data class Entity(
    val id: Int,
    val name: String,
)

object TbEntity : Table("tb_entity") {
    val id by "id".integer().primary().auto()
    val name by "name".varchar(255)
}