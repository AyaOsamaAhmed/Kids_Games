package com.aya.games.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemCategoryGameOneBinding
import com.aya.games.databinding.ItemSubGameTwoBinding
import com.aya.games.domain.model.TalkCategoryGames
import com.aya.games.presentation.ui.interfaces.OnClickGameOne
import com.aya.games.presentation.ui.interfaces.OnClickSubGameTwo
import com.aya.games.presentation.utils.setGlideImageUrl

class AdapterGameOne(
    private var list: ArrayList<TalkCategoryGames>,
    val onClick : OnClickGameOne

) : RecyclerView.Adapter<AdapterGameOne.ViewHolderSubGameTwo>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubGameTwo {
        val binding: ItemCategoryGameOneBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_category_game_one, parent, false
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

    class ViewHolderSubGameTwo(binding: ItemCategoryGameOneBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemCategoryGameOneBinding = binding
        fun bind(model: String) {
            itemRowBinding.executePendingBindings()
        }
    }
}