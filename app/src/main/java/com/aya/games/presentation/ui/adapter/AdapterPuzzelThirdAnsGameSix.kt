package com.aya.games.presentation.ui.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemPuzzelThirdAnsSubGameSixBinding
import com.aya.games.presentation.ui.interfaces.OnClickPuzzelGameSix
import com.aya.games.presentation.utils.setGlideImageUrl

class AdapterPuzzelThirdAnsGameSix(
    private var list: ArrayList<String>,
    val onClick : OnClickPuzzelGameSix

) : RecyclerView.Adapter<AdapterPuzzelThirdAnsGameSix.ViewHolderSubGameSix>() {

    var row_position = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubGameSix {
        val binding: ItemPuzzelThirdAnsSubGameSixBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_puzzel_third_ans_sub_game_six, parent, false
        )
        return ViewHolderSubGameSix(binding)
    }

    override fun getItemCount(): Int {
        return if (list.size > 0) list.size else 0
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolderSubGameSix, position: Int) {
        val model = list[position]

        holder.itemRowBinding.image.setGlideImageUrl(model ,holder.itemRowBinding.progress)

        if(row_position == position){
            holder.itemRowBinding.image.setBackgroundResource(R.drawable.background_border_pink)
        }
        else
        {
            holder.itemRowBinding.image.setBackgroundResource(R.drawable.background_border_trans)
        }

        holder.itemRowBinding.image.setOnClickListener {

            if(list[position].isEmpty()) {
                row_position = position
               onClick.onClickChooseGames(position)
                holder.itemRowBinding.image.setBackgroundResource(R.drawable.background_border_pink)
                notifyDataSetChanged()
            }
        }

    }


    class ViewHolderSubGameSix(binding: ItemPuzzelThirdAnsSubGameSixBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemPuzzelThirdAnsSubGameSixBinding = binding
        fun bind(model: String) {
            itemRowBinding.executePendingBindings()
        }
    }
}