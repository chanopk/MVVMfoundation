package com.chanop.mvvmfoundation.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.chanop.mvvmfoundation.MainApplication
import com.chanop.mvvmfoundation.ViewModelFactory
import com.chanop.mvvmfoundation.databinding.ActivityMainBinding
import com.chanop.mvvmfoundation.ui.detail.DetailActivity
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var bindng: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private var pokemonAdapter: ListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindng = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindng.root)

        initViewModel()
        initViewData()
        initEvent()
    }
    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(MainApplication.getApiHelperInstance()),
        )[MainViewModel::class.java]

        viewModel.loadPokemonKanto()
    }

    private fun initViewData() {
        pokemonAdapter = ListAdapter(this)
        bindng.listRecycler.adapter = pokemonAdapter
//        viewModel.getPokemonKanto().observe(this) {
//            when (it.status) {
//                Status.SUCCESS -> {
//                    Toast.makeText(
//                        this,
//                        "success ${it.data}",
//                        Toast.LENGTH_LONG,
//                    ).show()
//                }
//                Status.FAILURE -> {
//                    Toast.makeText(
//                        this,
//                        "Failed to load the data ${it.message}",
//                        Toast.LENGTH_LONG,
//                    ).show()
//                }
//                Status.LOADING -> {
//                    Toast.makeText(
//                        this,
//                        "Loading...",
//                        Toast.LENGTH_LONG,
//                    ).show()
//                }
//            }
//        }

        viewModel.pokemonKantoData.observe(this) { kanto ->
            Toast.makeText(
                this,
                "success $kanto",
                Toast.LENGTH_LONG,
            ).show()

            kanto?.pokemonEntries?.let { pokemonEntry -> pokemonAdapter?.listData = pokemonEntry }
        }

        viewModel.pokemonKantoDataError.observe(this) {
            Toast.makeText(
                this,
                "Failed to load the data $it",
                Toast.LENGTH_LONG,
            ).show()
        }
    }

    private fun initEvent() {
        bindng.tvSearch.addTextChangedListener {
            val textSearch = bindng.tvSearch.text.toString()
            if (textSearch == "") {
                viewModel.pokemonKantoData.value?.pokemonEntries?.let { pokemonEntry -> pokemonAdapter?.listData = pokemonEntry }
            } else {
                viewModel.pokemonKantoData.value?.pokemonEntries?.filter { it.pokemonSpecies?.name?.contains(textSearch) ?: false || it.entryNumber.toString().contains(textSearch) }?.apply {
                    pokemonAdapter?.listData = this
                }
            }
        }

        pokemonAdapter?.clickListener = { pokemonEntry ->
            val json = Gson().toJson(pokemonEntry)
            val bundle = Bundle()
            bundle.putString("pokemonEntry", json)
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        pokemonAdapter?.refresh()
    }
}
