package keep.sql

import keep.table.Column


/**
 * sql 查询条件
 */
sealed interface Condition {
    /**
     * 无查询条件
     */
    object All : Condition

    /**
     * 单一条件
     */
    class Simple<T : Any>(
        val column: Column<T>,
        val operator: Operator,
        val value: T?,
    ) : Condition

    /**
     * 支持 IN 和 NOT_IN 条件
     */
    class Values<T : Any>(
        val column: Column<T>,
        val operator: Operator,
        val values: Collection<T>,
    ) : Condition

    /**
     * 支持 AND 和 OR
     */
    class Complex(
        val condition0: Condition,
        val operator: Operator,
        val condition1: Condition,
    ) : Condition

    /**
     * [unary] 表示一元运算符,不需值
     */
    enum class Operator(val text: String, val unary: Boolean = false) {
        AND("and"), OR("or"), EQUAL("="), NOT_EQUAL("<>"), GREAT_THAN(">"), EQUAL_GREAT_THAN(">="), LESS_THAN("<"), EQUAL_LESS_THAN(
            "<="),
        IS_NULL("is null", true), NOT_NULL("is not null", true), IN("in"), NOT_IN("not in");
    }
}