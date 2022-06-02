package keep.keeper.source

import keep.keeper.session.KeepSession

interface KeepSource<S : KeepSession> {
    fun obtainSession(): S
    fun close(session: S)
}