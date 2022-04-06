package com.aya.games.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemSubGameFourMemoryBinding
import com.aya.games.presentation.utils.setGlideImageUrl

class AdapterSubGameFourMemory(
    private var list: ArrayList<String>

) : RecyclerView.Adapter<AdapterSubGameFourMemory.ViewHolderSubGameFour>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubGameFour {
        val binding: ItemSubGameFourMemoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_sub_game_four_memory, parent, false
        )
        return ViewHolderSubGameFour(binding)
    }

    override fun getItemCount(): Int {
        return if (list.size > 0) list.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolderSubGameFour, position: Int) {
        val model = list[position]

        holder.itemRowBinding.image.setGlideImageUrl(model,holder.itemRowBinding.progress)
    }

    class ViewHolderSubGameFour(binding: ItemSubGameFourMemoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemSubGameFourMemoryBinding = binding
        fun bind(model: String) {
            itemRowBinding.executePendingBindings()
        }
    }
}