package com.chanop.mvvmfoundation.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.chanop.mvvmfoundation.MainApplication
import com.chanop.mvvmfoundation.ViewModelFactory
import com.chanop.mvvmfoundation.databinding.ActivityDetailBinding
import com.finnomena.project.candidate.repositiry.model.PokemonEntry
import com.gisc.track.util.SharedPrefsUtil
import com.google.gson.Gson

class DetailActivity : AppCompatActivity() {
    private lateinit var bindng: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

//    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindng = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bindng.root)

        val bundle = intent.extras
        bundle?.let {
            val json = bundle.getString("pokemonEntry")
            val data = Gson().fromJson(json, PokemonEntry::class.java)
            if (data.entryNumber != null) {
                initViewModel(data.entryNumber!!)
            } else {
                onBackPressed()
            }
        }
        initViewData()
        initEvent()
    }

    private fun initEvent() {
        bindng.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViewModel(entryNumber: Int) {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(MainApplication.getApiHelperInstance()),
        )[DetailViewModel::class.java]

        viewModel.loadPokemon(entryNumber.toString())

        viewModel.pokemonData.observe(this) { pokemon ->
            bindng.tvPokemonName.text = pokemon?.name
            loadImage(url = pokemon?.sprites?.frontDefault, view = bindng.ivPokemonImg, name = pokemon?.name ?: "")
            bindng.tvPokemonHeight.text = "Height : " + pokemon?.height.toString()
            bindng.tvPokemonWeight.text = "Weight : " + pokemon?.weight.toString()
            bindng.tvPokemonTypes.text = "Types : " + pokemon?.types?.map { it.type?.name }.toString().replace("[", "").replace("]", "")
            pokemon?.stats?.forEach {
                when (it.stat?.name) {
                    "hp" -> { bindng.tvhp.text = it.baseStat.toString() }
                    "attack" -> { bindng.tvattack.text = it.baseStat.toString() }
                    "special-attack" -> { bindng.tvspecialAttack.text = it.baseStat.toString() }
                    "defense" -> { bindng.tvdefense.text = it.baseStat.toString() }
                    "special-defense" -> { bindng.tvspecialDefense.text = it.baseStat.toString() }
                    "speed" -> { bindng.tvspeed.text = it.baseStat.toString() }
                }
            }
        }
        viewModel.pokemonDataError.observe(this) {
            Toast.makeText(
                this,
                "Failed to load the data $it",
                Toast.LENGTH_LONG,
            ).show()
        }
    }

    private fun initViewData() {
    }

    private fun loadImage(url: String?, view: ImageView, name: String) {
        if (!url.isNullOrBlank()) {
            Glide.with(this)
                .load(url)
                .into(view)
            SharedPrefsUtil.setString(this, name, url)
        }
    }
}
