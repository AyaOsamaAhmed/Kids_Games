package com.aya.games.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemSubGameTwoBinding
import com.aya.games.presentation.ui.interfaces.OnClickSubGameTwo
import com.aya.games.presentation.utils.setGlideImageUrl

class AdapterSubGameOne(
    private var list: ArrayList<String>,
    val onClick : OnClickSubGameTwo

) : RecyclerView.Adapter<AdapterSubGameOne.ViewHolderSubGameTwo>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubGameTwo {
        val binding: ItemSubGameTwoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_sub_game_two, parent, false
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

        holder.itemRowBinding.image.setGlideImageUrl(model,holder.itemRowBinding.progress)

        holder.itemRowBinding.image.setOnClickListener {
                onClick.onClickChooseGames(position.toString())
        }
    }

    class ViewHolderSubGameTwo(binding: ItemSubGameTwoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemSubGameTwoBinding = binding
        fun bind(model: String) {
            itemRowBinding.executePendingBindings()
        }
    }
}