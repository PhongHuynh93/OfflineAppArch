package example.test.phong.offlineapparch.db

import android.arch.persistence.room.*
import android.arch.persistence.room.Database
import io.reactivex.Single

@Database(entities = arrayOf(Cryptocurrency::class), version = 1)
abstract class Database: RoomDatabase() {
    abstract fun cryptocurrenciesDao(): CryptocurrenciesDao
}

@Dao
interface CryptocurrenciesDao {
    // note - get the info in chunks of data rather than in bulk:
    @Query("SELECT * FROM cryptocurrency ORDER BY rank limit :limit offset :offset")
    fun queryCryptocurrencies(limit:Int, offset:Int): Single<List<Cryptocurrency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCryptocurrency(cryptocurrency: Cryptocurrency)
}