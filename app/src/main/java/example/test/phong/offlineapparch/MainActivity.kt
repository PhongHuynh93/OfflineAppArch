package example.test.phong.offlineapparch

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import example.test.phong.offlineapparch.db.Cryptocurrency
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var cryptocurrenciesViewModelFactory: CryptocurrenciesViewModelFactory
    lateinit var cryptocurrenciesViewModel: CryptocurrenciesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        cryptocurrenciesViewModel = ViewModelProviders.of(this, cryptocurrenciesViewModelFactory).get(
                CryptocurrenciesViewModel::class.java)

        cryptocurrenciesViewModel.loadCryptocurrencies()

        cryptocurrenciesViewModel.cryptocurrenciesResult().observe(this,
                Observer<List<Cryptocurrency>> {
                    tvHello.text = "Hello ${it?.size} cryptocurrencies"
                })

        cryptocurrenciesViewModel.cryptocurrenciesError().observe(this, Observer<String> {
            tvHello.text = "Hello error $it"
        })
    }

    override fun onDestroy() {
        cryptocurrenciesViewModel.disposeElements()
        super.onDestroy()
    }
}
