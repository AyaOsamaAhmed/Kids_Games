package com.aya.games.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemCategoryGameTwoBinding
import com.aya.games.domain.model.LookCategoryGames
import com.aya.games.presentation.ui.interfaces.OnClickGameTwo
import com.aya.games.presentation.utils.setGlideImageUrl

class AdapterGameTwo(
    private var list: ArrayList<LookCategoryGames>,
    val onClick : OnClickGameTwo

) : RecyclerView.Adapter<AdapterGameTwo.ViewHolderSubGameTwo>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubGameTwo {
        val binding: ItemCategoryGameTwoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_category_game_two, parent, false
        )
       /* binding.root.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )*/
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
                onClick.onClickChooseGames(model.id.toString())
        }
    }

    class ViewHolderSubGameTwo(binding: ItemCategoryGameTwoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemCategoryGameTwoBinding = binding
        fun bind(model: String) {
            itemRowBinding.executePendingBindings()
        }
    }
}