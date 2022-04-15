package com.aya.games.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemCategoryGameFiveBinding
import com.aya.games.databinding.ItemCategoryGameFourBinding
import com.aya.games.domain.model.ListenLookCategoryGames
import com.aya.games.domain.model.MemoryCategoryGames
import com.aya.games.presentation.ui.interfaces.OnClickGameFive
import com.aya.games.presentation.utils.setGlideImageUrl

class AdapterGameFive(
    private var list: ArrayList<ListenLookCategoryGames>,
    val onClick : OnClickGameFive

) : RecyclerView.Adapter<AdapterGameFive.ViewHolderSubGameFive>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubGameFive {
        val binding: ItemCategoryGameFiveBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_category_game_five, parent, false
        )
        return ViewHolderSubGameFive(binding)
    }

    override fun getItemCount(): Int {
        return if (list.size > 0) list.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolderSubGameFive, position: Int) {
        val model = list[position]

        holder.itemRowBinding.image.setGlideImageUrl(model.image!!,holder.itemRowBinding.progress)
        holder.itemRowBinding.imageTx.text = model.name_ar

        holder.itemRowBinding.image.setOnClickListener {
                onClick.onClickChooseGames(model.id.toString())
        }
    }

    class ViewHolderSubGameFive(binding: ItemCategoryGameFiveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemCategoryGameFiveBinding = binding
        fun bind(model: String) {
            itemRowBinding.executePendingBindings()
        }
    }
}