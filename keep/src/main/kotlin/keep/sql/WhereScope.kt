package keep.sql

import keep.table.Column

interface WhereScope {
    //
    infix fun <T : Any> Column<T>.eq(value: T?) = Condition.Simple(this, Condition.Operator.EQUAL, value)
    infix fun <T : Any> Column<T>.neq(value: T?) = Condition.Simple(this, Condition.Operator.NOT_EQUAL, value)
    infix fun <T : Any> Column<T>.gt(value: T?) = Condition.Simple(this, Condition.Operator.GREAT_THAN, value)
    infix fun <T : Any> Column<T>.egt(value: T?) = Condition.Simple(this, Condition.Operator.EQUAL_GREAT_THAN, value)
    infix fun <T : Any> Column<T>.lt(value: T?) = Condition.Simple(this, Condition.Operator.LESS_THAN, value)
    infix fun <T : Any> Column<T>.elt(value: T?) = Condition.Simple(this, Condition.Operator.EQUAL_LESS_THAN, value)
    fun <T : Any> Column<T>.isNull() = Condition.Simple(this, Condition.Operator.IS_NULL, null)
    fun <T : Any> Column<T>.isNotNull() = Condition.Simple(this, Condition.Operator.NOT_NULL, null)

    //
    infix fun <T : Any> Column<T>.isIn(values: Collection<T>) = Condition.Values(this, Condition.Operator.IN, values)
    infix fun <T : Any> Column<T>.notIn(values: Collection<T>) =
        Condition.Values(this, Condition.Operator.NOT_IN, values)

    fun <T : Any> Column<T>.isIn(vararg values: T) = Condition.Values(this, Condition.Operator.IN, values.toList())
    fun <T : Any> Column<T>.notIn(vararg values: T) = Condition.Values(this, Condition.Operator.NOT_IN, values.toList())

    //
    infix fun Condition.and(condition: Condition) = Condition.Complex(this, Condition.Operator.AND, condition)
    infix fun Condition.or(condition: Condition) = Condition.Complex(this, Condition.Operator.OR, condition)
}