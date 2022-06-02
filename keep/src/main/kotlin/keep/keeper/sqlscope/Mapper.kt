package keep.keeper.sqlscope

import java.sql.ResultSet
import kotlin.reflect.KClass

interface ResultMapper {
    fun <E : Any> map(result: ResultSet, entityClass: KClass<E>): List<E>
}