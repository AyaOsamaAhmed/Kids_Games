package com.aya.games.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemSubGameFourMemoryBinding
import com.aya.games.presentation.utils.setGlideImageUrl

class AdapterSubGameFourData(
) : RecyclerView.Adapter<AdapterSubGameFourData.ViewHolderSubGameFour>() {


    private var list: MutableList<String> =  ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubGameFour {
        val binding: ItemSubGameFourMemoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_sub_game_four_memory, parent, false
        )
        return ViewHolderSubGameFour(binding)
    }

    override fun getItemCount(): Int = list.size


    fun clearList (){
        list.clear()
    }
    fun addNewImage(image:String){
       list.add(image)
    }
    fun checkSize():Int{
        return list.size
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