package example.test.phong.offlineapparch

import android.util.Log
import example.test.phong.offlineapparch.db.CryptocurrenciesDao
import example.test.phong.offlineapparch.db.Cryptocurrency
import example.test.phong.offlineapparch.remote.ApiInterface
import io.reactivex.Observable
import javax.inject.Inject

class CryptocurrencyRepository @Inject constructor(val apiInterface: ApiInterface,
                                                   val cryptocurrenciesDao: CryptocurrenciesDao,
                                                   val utils: Utils) {

    fun getCryptocurrencies(limit: Int, offset: Int): Observable<List<Cryptocurrency>> {
        val hasConnection = utils.isConnectedToInternet()
        var observableFromApi: Observable<List<Cryptocurrency>>? = null
        if (hasConnection) {
            observableFromApi = getCryptocurrenciesFromApi()
        }
        val observableFromDb = getCryptocurrenciesFromDb(limit, offset)
        // In the future (in another entry), the idea is to do a check to verify if there is a connection, and then get the data and update the DB, otherwise, get the last info from the DB, just to achieve the goal: offline first apps
        return if (hasConnection) Observable.concatArrayEager(observableFromApi, observableFromDb)
        else observableFromDb
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

    fun getCryptocurrenciesFromDb(limit: Int, offset: Int): Observable<List<Cryptocurrency>> {
        val hasConnection = utils.isConnectedToInternet()
        return cryptocurrenciesDao.queryCryptocurrencies(limit, offset)
                .toObservable()
                .doOnNext {
                    //Print log it.size :)
                    Log.e("REPOSITORY DB *** ", it.size.toString())
                }
    }
}