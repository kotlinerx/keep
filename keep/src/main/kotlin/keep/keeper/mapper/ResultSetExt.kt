package keep.keeper.mapper

import java.sql.ResultSet
import kotlin.reflect.KClass

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