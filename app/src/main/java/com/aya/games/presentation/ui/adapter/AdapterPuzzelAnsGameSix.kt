package com.aya.games.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemCategoryGameSixBinding
import com.aya.games.databinding.ItemPuzzelAnsSubGameSixBinding
import com.aya.games.databinding.ItemPuzzelSubGameSixBinding
import com.aya.games.domain.model.FocusCategoryGames
import com.aya.games.presentation.ui.interfaces.OnClickGameSix
import com.aya.games.presentation.ui.interfaces.OnClickPuzzelGameSix
import com.aya.games.presentation.utils.setGlideImageUrl

class AdapterPuzzelAnsGameSix(
    private var list: ArrayList<String>,
    val onClick : OnClickPuzzelGameSix

) : RecyclerView.Adapter<AdapterPuzzelAnsGameSix.ViewHolderSubGameSix>() {

    var check_position = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubGameSix {
        val binding: ItemPuzzelAnsSubGameSixBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_puzzel_ans_sub_game_six, parent, false
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

            holder.itemRowBinding.image.setBackgroundResource(R.drawable.background_border_pink)
            onClick.onClickChooseGames(position)
            notifyItemChanged(check_position)
            check_position = position
        }

    }

    class ViewHolderSubGameSix(binding: ItemPuzzelAnsSubGameSixBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemPuzzelAnsSubGameSixBinding = binding
        fun bind(model: String) {
            itemRowBinding.executePendingBindings()
        }
    }
}