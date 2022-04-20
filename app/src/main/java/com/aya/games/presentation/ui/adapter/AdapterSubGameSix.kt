package com.aya.games.presentation.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemSubGameFiveBinding
import com.aya.games.databinding.ItemSubGameSixBinding
import com.aya.games.databinding.ItemSubGameTwoBinding
import com.aya.games.presentation.ui.interfaces.OnClickSubGameFive
import com.aya.games.presentation.ui.interfaces.OnClickSubGameSix
import com.aya.games.presentation.utils.setGlideImageUrl

class AdapterSubGameSix(
    private var list: ArrayList<String>,
    val onClick : OnClickSubGameSix,
    val  back_check : Drawable,
    var access_onClick : Boolean

) : RecyclerView.Adapter<AdapterSubGameSix.ViewHolderSubGameSix>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubGameSix {
        val binding: ItemSubGameSixBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_sub_game_six, parent, false
        )
        return ViewHolderSubGameSix(binding)
    }

    override fun getItemCount(): Int {
        return if (list.size > 0) list.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolderSubGameSix, position: Int) {
        val model = list[position]

        holder.itemRowBinding.image.setGlideImageUrl(model,holder.itemRowBinding.progress)

        holder.itemRowBinding.image.setOnClickListener {
            if(access_onClick) {
                holder.itemRowBinding.image.background = back_check
                onClick.onClickChooseGames(position.toString())
                access_onClick = false
            }
        }
    }

    class ViewHolderSubGameSix(binding: ItemSubGameSixBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemSubGameSixBinding = binding
        fun bind(model: String) {
            itemRowBinding.executePendingBindings()
        }
    }
}