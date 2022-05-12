package com.aya.games.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemCategoryGameSixBinding
import com.aya.games.databinding.ItemPuzzelSubGameSixBinding
import com.aya.games.domain.model.FocusCategoryGames
import com.aya.games.presentation.ui.interfaces.OnClickGameSix
import com.aya.games.presentation.ui.interfaces.OnClickPuzzelAnsGameSix
import com.aya.games.presentation.ui.interfaces.OnClickPuzzelGameSix
import com.aya.games.presentation.utils.setGlideImageUrl

class AdapterPuzzelGameSix(
    private var list: ArrayList<String>,
    val onClick : OnClickPuzzelAnsGameSix

) : RecyclerView.Adapter<AdapterPuzzelGameSix.ViewHolderSubGameSix>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubGameSix {
        val binding: ItemPuzzelSubGameSixBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_puzzel_sub_game_six, parent, false
        )
        return ViewHolderSubGameSix(binding)
    }

    override fun getItemCount(): Int {
        return if (list.size > 0) list.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolderSubGameSix, position: Int) {
        val model = list[position]

        holder.itemRowBinding.image.setGlideImageUrl(model!!,holder.itemRowBinding.progress)

        holder.itemRowBinding.image.setOnClickListener {
           // notifyItemRemoved(position)

            onClick.onClickChooseAnsGames(position)
        }
    }

    class ViewHolderSubGameSix(binding: ItemPuzzelSubGameSixBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemPuzzelSubGameSixBinding = binding
        fun bind(model: String) {
            itemRowBinding.executePendingBindings()
        }
    }
}