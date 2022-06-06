package keep.keeper

import keep.keeper.sqlscope.QueryScope

inline fun <reified E : Any> Keeper.query(): QueryScope<E> {
    return obtainQueryScope(E::class, context.mapper)
}