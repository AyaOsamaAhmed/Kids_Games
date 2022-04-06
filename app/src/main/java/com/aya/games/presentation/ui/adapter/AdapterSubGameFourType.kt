package com.aya.games.presentation.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemSubGameFourTypeBinding
import com.aya.games.presentation.ui.interfaces.OnClickSubGameFour
import com.aya.games.presentation.utils.setGlideImageUrl

class AdapterSubGameFourType(
    private var list: ArrayList<String>,
    val onClick : OnClickSubGameFour,
    val  back_check : Drawable,
    var access_onClick : Boolean

) : RecyclerView.Adapter<AdapterSubGameFourType.ViewHolderSubGameFour>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubGameFour {
        val binding: ItemSubGameFourTypeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_sub_game_four_type, parent, false
        )
        return ViewHolderSubGameFour(binding)
    }

    override fun getItemCount(): Int {
        return if (list.size > 0) list.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolderSubGameFour, position: Int) {
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

    class ViewHolderSubGameFour(binding: ItemSubGameFourTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemSubGameFourTypeBinding = binding
        fun bind(model: String) {
            itemRowBinding.executePendingBindings()
        }
    }
}