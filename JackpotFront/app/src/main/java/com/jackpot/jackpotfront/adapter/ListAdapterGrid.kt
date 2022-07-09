package com.jackpot.jackpotfront.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListAdapterGrid (val context: Context, var userIdx: Int?, val img_list: ArrayList<AllClothObject>)
    : RecyclerView.Adapter<ListAdapterGrid.GridAdapter>() {
    class GridAdapter(val binding: ItemGridBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridAdapter {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)

        return GridAdapter(ItemGridBinding.bind(view))
    }

    override fun onBindViewHolder(holder: GridAdapter, position: Int) {
        val img = holder.binding.gridViewImg
//        img.setImageURI(img_list[position].clthImgUrl.toUri())
        Glide.with(context)
            .load(img_list[position].clthImgUrl.toUri())
            .thumbnail(0.1f)
            .into(img)



        // 그리드 뷰에서 개별 옷 클릭 시
        img.setOnClickListener() {
            val intent = Intent( context ,ClothActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            intent.putExtra("clothIdx", img_list[position]!!.clthIdx)

            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return img_list.size
    }
}