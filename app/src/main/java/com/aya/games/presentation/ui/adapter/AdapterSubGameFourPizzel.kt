package com.aya.games.presentation.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemSubGameFourBinding
import com.aya.games.presentation.ui.interfaces.OnClickSubGameFour
import com.aya.games.presentation.ui.interfaces.OnClickSubGameFourMemory
import com.aya.games.presentation.utils.setGlideImageUrl

class AdapterSubGameFourPizzel(
    private var list: ArrayList<String>,
    private var list_index: ArrayList<Int>,
    val onClick : OnClickSubGameFourMemory,
    val  back_check : Drawable

) : RecyclerView.Adapter<AdapterSubGameFourPizzel.ViewHolderSubGameFour>() {

    var dataList : ArrayList<Int> = ArrayList<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubGameFour {
        val binding: ItemSubGameFourBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_sub_game_four, parent, false
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

            if(dataList.isEmpty()) {
                holder.itemRowBinding.image.background = back_check
                onClick.onClickChooseGames(list_index[position],model)
                dataList.add(position)
            }else{
                 if (!dataList.contains(position)){
                     holder.itemRowBinding.image.background = back_check
                     onClick.onClickChooseGames(list_index[position],model)
                     dataList.add(position)
                 }
            }
        }
    }

    class ViewHolderSubGameFour(binding: ItemSubGameFourBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemSubGameFourBinding = binding
        fun bind(model: String) {
            itemRowBinding.executePendingBindings()
        }
    }
}