package test

import keep.Keep
import keep.table.TbEntity
import kotlin.test.Test

class TestSql {
    val sqliteKeeper = Keep.sqlite("jdbc:sqlite:local/local.db")


    @Test
    fun test() {
        sqliteKeeper {
            drop(TbEntity)
            create(TbEntity)

            insert(TbEntity) {
                set {
                    it.id eq 1
                    it.name eq "leo"
                }
            }
            insert(TbEntity) {
                set {
                    it.id eq 2
                    it.name eq "yasuofenglei"
                }
            }

            select(TbEntity) {
                where {
                    it.name eq "leo"
                }
            }.also {
                assert(it.next())
                assert(it.getInt("id") == 1)
            }

            update(TbEntity) {
                set { it.name eq "new" }
                where { it.name eq "leo" }
            }
            select(TbEntity) {
                where {
                    it.name eq "leo"
                }
            }.also {
                assert(!it.next())
            }

            delete(TbEntity) {}
            val res = select(TbEntity) {}
            assert(!res.next())

        }
    }
}