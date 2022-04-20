package com.aya.games.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemCategoryGameFiveBinding
import com.aya.games.databinding.ItemCategoryGameSixBinding
import com.aya.games.domain.model.ListenLookCategoryGames
import com.aya.games.presentation.ui.interfaces.OnClickGameSix
import com.aya.games.presentation.utils.setGlideImageUrl

class AdapterGameSix(
    private var list: ArrayList<ListenLookCategoryGames>,
    val onClick : OnClickGameSix

) : RecyclerView.Adapter<AdapterGameSix.ViewHolderSubGameSix>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubGameSix {
        val binding: ItemCategoryGameSixBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_category_game_six, parent, false
        )
        return ViewHolderSubGameSix(binding)
    }

    override fun getItemCount(): Int {
        return if (list.size > 0) list.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolderSubGameSix, position: Int) {
        val model = list[position]

        holder.itemRowBinding.image.setGlideImageUrl(model.image!!,holder.itemRowBinding.progress)
        holder.itemRowBinding.imageTx.text = model.name_ar

        holder.itemRowBinding.image.setOnClickListener {
                onClick.onClickChooseGames(model.id.toString())
        }
    }

    class ViewHolderSubGameSix(binding: ItemCategoryGameSixBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemCategoryGameSixBinding = binding
        fun bind(model: String) {
            itemRowBinding.executePendingBindings()
        }
    }
}