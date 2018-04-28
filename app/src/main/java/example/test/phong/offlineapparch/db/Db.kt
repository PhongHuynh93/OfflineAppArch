package example.test.phong.offlineapparch.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.Database

@Database(entities = arrayOf(Cryptocurrency::class), version = 1)
abstract class Database: RoomDatabase() {
    abstract fun cryptocurrenciesDao(): CryptocurrenciesDao
}

@Dao
interface CryptocurrenciesDao {
    @Query("SELECT * FROM cryptocurrency")
    fun queryCryptocurrencies(): LiveData<List<Cryptocurrency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCryptocurrency(cryptocurrency: Cryptocurrency)
}