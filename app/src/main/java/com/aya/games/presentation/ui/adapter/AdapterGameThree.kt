package com.aya.games.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemCategoryGameThreeBinding
import com.aya.games.domain.model.ListenCategoryGames
import com.aya.games.presentation.ui.interfaces.OnClickGameThree
import com.aya.games.presentation.utils.setGlideImageUrl

class AdapterGameThree(
    private var list: ArrayList<ListenCategoryGames>,
    val onClick : OnClickGameThree

) : RecyclerView.Adapter<AdapterGameThree.ViewHolderSubGameTwo>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubGameTwo {
        val binding: ItemCategoryGameThreeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_category_game_three, parent, false
        )
        return ViewHolderSubGameTwo(binding)
    }

    override fun getItemCount(): Int {
        return if (list.size > 0) list.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolderSubGameTwo, position: Int) {
        val model = list[position]

        holder.itemRowBinding.image.setGlideImageUrl(model.image!!,holder.itemRowBinding.progress)
        holder.itemRowBinding.imageTx.text = model.name_ar

        holder.itemRowBinding.image.setOnClickListener {
                onClick.onClickChooseGames(model.id.toString(),model.type.toString())
        }
    }

    class ViewHolderSubGameTwo(binding: ItemCategoryGameThreeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemCategoryGameThreeBinding = binding
        fun bind(model: String) {
            itemRowBinding.executePendingBindings()
        }
    }
}