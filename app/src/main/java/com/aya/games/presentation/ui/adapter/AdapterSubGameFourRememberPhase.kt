package com.aya.games.presentation.ui.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemSubGameFourRememberPhaseBinding
import com.aya.games.presentation.ui.interfaces.OnClickSubGameFourRememberPhase
import com.aya.games.presentation.utils.setGlideImageUrl

class AdapterSubGameFourRememberPhase(
    private var list: ArrayList<String>,
    val onClick : OnClickSubGameFourRememberPhase,
    val img_cover : String

) : RecyclerView.Adapter<AdapterSubGameFourRememberPhase.ViewHolderSubGameFour>() {

    var num_cover = 0
    var answer : ArrayList<Int> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSubGameFour {
        val binding: ItemSubGameFourRememberPhaseBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_sub_game_four_remember_phase, parent, false
        )
        return ViewHolderSubGameFour(binding)
    }

    override fun getItemCount(): Int {
        return if (list.size > 0) list.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolderSubGameFour, position: Int) {
        val model = list[position]

        holder.itemRowBinding.image.setGlideImageUrl(model,holder.itemRowBinding.progress)
        holder.itemRowBinding.cover.setGlideImageUrl(img_cover,holder.itemRowBinding.progress)

        Handler(Looper.getMainLooper()).postDelayed({
            holder.itemRowBinding.cover.visibility = View.VISIBLE
        }, 3000)

        holder.itemRowBinding.cover.setOnClickListener {
            holder.itemRowBinding.cover.visibility = View.GONE
            checkCover(position)
        }

    }

    fun checkCover(ans : Int){
        num_cover ++
        answer.add(ans)
        if(num_cover == 2){
           if(! onClick.onClickChooseGames(answer[0],answer[1])){
               notifyItemChanged(answer[0])
               notifyItemChanged(answer[1])
           }


            num_cover = 0
            answer.clear()
        }
    }
    class ViewHolderSubGameFour(binding: ItemSubGameFourRememberPhaseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemSubGameFourRememberPhaseBinding = binding
        fun bind(model: String) {
            itemRowBinding.executePendingBindings()
        }
    }
}