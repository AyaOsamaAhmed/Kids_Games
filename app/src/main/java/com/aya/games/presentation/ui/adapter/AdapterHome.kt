package com.aya.games.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aya.games.R
import com.aya.games.databinding.ItemHomeBinding
import com.aya.games.domain.model.Home
import com.aya.games.presentation.ui.interfaces.OnClickHome
import com.squareup.picasso.Picasso

class AdapterHome(
    private var list: ArrayList<Home>,
    val onClick : OnClickHome

) : RecyclerView.Adapter<AdapterHome.ViewHolderAppointment>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAppointment {
        val binding: ItemHomeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_home, parent, false
        )
       /* binding.root.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )*/
        return ViewHolderAppointment(binding)
    }

    override fun getItemCount(): Int {
        return if (list.size > 0) list.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolderAppointment, position: Int) {
        val model = list[position]
        holder.bind(model)
      //  holder.itemRowBinding.nameDoctor.text = model.name_doctor

      //  Glide.with(myFragment).load(model.image_doctor).centerCrop().into(holder.itemRowBinding.image);
        Picasso.get().load(model.image).into(holder.itemRowBinding.image)
        holder.itemRowBinding.image.setOnClickListener {
            onClick.onClickChooseGames(model.id!!)
        }
    }

    class ViewHolderAppointment(binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemHomeBinding = binding
        fun bind(model: Home) {
            //itemRowBinding.model = obj as Appointment
           itemRowBinding.model = model
            itemRowBinding.executePendingBindings()
        }
    }
}