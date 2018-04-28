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
    @Query("SELECT * FROM cryptocurrency")
    fun queryCryptocurrencies(): Single<List<Cryptocurrency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCryptocurrency(cryptocurrency: Cryptocurrency)
}