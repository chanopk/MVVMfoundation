package com.chanop.mvvmfoundation.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chanop.mvvmfoundation.R
import com.chanop.mvvmfoundation.databinding.ViewHolderListBinding
import com.finnomena.project.candidate.repositiry.model.PokemonEntry
import com.gisc.track.util.SharedPrefsUtil
import kotlin.properties.Delegates

class ListAdapter(val context: Context) : RecyclerView.Adapter<ListAdapter.ListResultViewHolder>() {

    var listData: List<PokemonEntry> by Delegates.observable(listOf()) { _, _, _ -> notifyDataSetChanged() }
    lateinit var clickListener: (item: PokemonEntry) -> Unit
    private var focusPosition: Int? = null

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ListResultViewHolder, position: Int) {
        val data = listData[position]
        holder.bindData(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListResultViewHolder {
        val binding = ViewHolderListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListResultViewHolder(binding)
    }

    inner class ListResultViewHolder(private val binding: ViewHolderListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: PokemonEntry) {
            itemView.apply {
                item.pokemonSpecies?.name?.let {
                    binding.tvPokemonName.text = it
                    SharedPrefsUtil.getString(context, it)?.let { item.frontDefault = it }
                }
                item.entryNumber?.let { binding.tvNumber.text = "#$it" }
                if (item.frontDefault != null) {
                    Glide.with(context)
                        .load(item.frontDefault)
                        .into(binding.tvPokemonImage)
                } else {
                    Glide.with(context)
                        .load("")
                        .into(binding.tvPokemonImage)
                }
                binding.layoutPokemon.setOnClickListener {
                    clickListener.invoke(item)
                }
                focusPosition?.let {
                    if (position == focusPosition) {
                        binding.tvPokemonName.setTextColor(context.getColor(R.color.colorPrimary))
                        binding.tvNumber.setTextColor(context.getColor(R.color.colorPrimary))
                    } else {
                        binding.tvPokemonName.setTextColor(context.getColor(R.color.colorDarkGray))
                        binding.tvNumber.setTextColor(context.getColor(R.color.colorDarkGray))
                    }
                }
            }
        }
    }

    fun focusPosition(position: Int) {
        focusPosition = position
    }

    fun refresh() {
        notifyDataSetChanged()
    }
}
