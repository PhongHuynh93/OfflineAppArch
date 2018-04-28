package example.test.phong.offlineapparch

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import example.test.phong.offlineapparch.db.Cryptocurrency
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CryptocurrenciesViewModel @Inject constructor(private val cryptocurrencyRepository: CryptocurrencyRepository) : ViewModel() {
    var cryptocurrenciesResult: MutableLiveData<List<Cryptocurrency>> = MutableLiveData()
    var cryptocurrenciesError: MutableLiveData<String> = MutableLiveData()
    lateinit var disposableObserver: DisposableObserver<List<Cryptocurrency>>
    var cryptocurrenciesLoader: MutableLiveData<Boolean> = MutableLiveData()

    fun cryptocurrenciesResult(): LiveData<List<Cryptocurrency>> {
        return cryptocurrenciesResult
    }

    fun cryptocurrenciesError(): LiveData<String> {
        return cryptocurrenciesError
    }

    fun loadCryptocurrencies(limit: Int, offset: Int) {

        disposableObserver = object : DisposableObserver<List<Cryptocurrency>>() {
            override fun onComplete() {

            }

            override fun onNext(cryptocurrencies: List<Cryptocurrency>) {
                cryptocurrenciesResult.postValue(cryptocurrencies)
                cryptocurrenciesLoader.postValue(false)
            }

            override fun onError(e: Throwable) {
                cryptocurrenciesError.postValue(e.message)
            }
        }

        cryptocurrencyRepository.getCryptocurrencies(limit, offset)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe(disposableObserver)
    }

    fun disposeElements() {
        if (!disposableObserver.isDisposed) disposableObserver.dispose()
    }

    fun cryptocurrenciesLoader(): LiveData<Boolean> {
        return cryptocurrenciesLoader
    }
}