package com.aya.games.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemCategoryGameFourBinding
import com.aya.games.databinding.ItemSubGameFourRememberBinding
import com.aya.games.domain.model.MemoryGamesRemember
import com.aya.games.presentation.ui.interfaces.OnClickSubGameFourRemember
import com.aya.games.presentation.utils.setGlideImageUrl

class AdapterSubGameFourRemember(
    private var list: ArrayList<MemoryGamesRemember>,
    val onClick : OnClickSubGameFourRemember

) : RecyclerView.Adapter<AdapterSubGameFourRemember.ViewHolderSubGameFour>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubGameFour {
        val binding: ItemSubGameFourRememberBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_sub_game_four_remember, parent, false
        )
        return ViewHolderSubGameFour(binding)
    }

    override fun getItemCount(): Int {
        return if (list.size > 0) list.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolderSubGameFour, position: Int) {
        val model = list[position]

        holder.itemRowBinding.image.setGlideImageUrl(model.image!!,holder.itemRowBinding.progress)
        holder.itemRowBinding.text.text = model.name

        holder.itemRowBinding.image.setOnClickListener {
                onClick.onClickChooseGames(model.id!!.toInt() , model.num!!)
        }
    }

    class ViewHolderSubGameFour(binding: ItemSubGameFourRememberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemSubGameFourRememberBinding = binding
        fun bind(model: String) {
            itemRowBinding.executePendingBindings()
        }
    }
}