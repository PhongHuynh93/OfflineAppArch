package example.test.phong.offlineapparch

import android.util.Log
import example.test.phong.offlineapparch.db.CryptocurrenciesDao
import example.test.phong.offlineapparch.db.Cryptocurrency
import example.test.phong.offlineapparch.remote.ApiInterface
import io.reactivex.Observable
import javax.inject.Inject

class CryptocurrencyRepository @Inject constructor(val apiInterface: ApiInterface,
                                                   val cryptocurrenciesDao: CryptocurrenciesDao) {

    fun getCryptocurrencies(): Observable<List<Cryptocurrency>> {
        val observableFromApi = getCryptocurrenciesFromApi()
        val observableFromDb = getCryptocurrenciesFromDb()
        // In the future (in another entry), the idea is to do a check to verify if there is a connection, and then get the data and update the DB, otherwise, get the last info from the DB, just to achieve the goal: offline first apps
        return Observable.concatArrayEager(observableFromDb, observableFromApi)
    }

    fun getCryptocurrenciesFromApi(): Observable<List<Cryptocurrency>> {
        return apiInterface.getCryptocurrencies("0")
                .doOnNext {
                    Log.e("REPOSITORY API * ", it.size.toString())
                    for (item in it) {
                        cryptocurrenciesDao.insertCryptocurrency(item)
                    }
                }
    }

    fun getCryptocurrenciesFromDb(): Observable<List<Cryptocurrency>> {
        return cryptocurrenciesDao.queryCryptocurrencies()
                .toObservable()
                .doOnNext {
                    //Print log it.size :)
                    Log.e("REPOSITORY DB *** ", it.size.toString())
                }
    }
}