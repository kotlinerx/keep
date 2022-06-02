package keep.keeper.mapper

import java.sql.ResultSet
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor

abstract class BaseMapper : ResultMapper {
    override fun <E : Any> map(result: ResultSet, entityClass: KClass<E>): List<E> {
        val resultList = ArrayList<E>()
        val constructor = entityClass.primaryConstructor
            ?: throw IllegalStateException("[$entityClass] must have primary constructor")
        while (result.next()) {
            val entity = rowToEntity(result, constructor, entityClass)
            resultList.add(entity)

        }
        return resultList
    }

    abstract fun <E : Any> rowToEntity(row: ResultSet, constructor: KFunction<E>, kClass: KClass<E>): E
}

class NameMapper : BaseMapper() {
    fun <T : Any> ResultSet.valueOrNull(label: String, kClass: KClass<T>): Any? = when (kClass) {
        String::class -> {
            getString(label)
        }
        Int::class -> {
            getInt(label)
        }
        Boolean::class -> {
            getBoolean(label)
        }
        else -> {
            getObject(label)
        }
    }

    override fun <E : Any> rowToEntity(row: ResultSet, constructor: KFunction<E>, kClass: KClass<E>): E {
        val map: Map<KParameter, Any?> = constructor.parameters.associateWith {
            row.valueOrNull(it.name!!, kClass)
        }
        return constructor.callBy(map)
    }
}