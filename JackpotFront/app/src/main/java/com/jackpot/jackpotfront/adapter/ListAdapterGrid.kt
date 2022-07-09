package com.jackpot.jackpotfront.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jackpot.jackpotfront.R
import com.jackpot.jackpotfront.databinding.ActivityLoginBinding
import com.jackpot.jackpotfront.databinding.ItemGridBinding
import com.jackpot.jackpotfront.retrofit.data.AllPostsObject


class ListAdapterGrid(val context: Context?, var userIdx: Int?, val img_list: ArrayList<AllPostsObject>)
    : RecyclerView.Adapter<ListAdapterGrid.GridAdapter>() {

    class GridAdapter(val binding: ItemGridBinding): RecyclerView.ViewHolder(binding.root)

    // 즐겨찾기 애니메이션
    private var isHearting: Boolean = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridAdapter {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)

        return GridAdapter(ItemGridBinding.bind(view))
    }

    override fun onBindViewHolder(holder: GridAdapter, position: Int) {
        val img = holder.binding.gridViewImg
//        img.setImageURI(img_list[position].clthImgUrl.toUri())
        Glide.with(context!!)
            .load(img_list[position].imgUrl)
            .thumbnail(0.1f)
            .into(img)

        holder.binding.content.text = img_list[position].contents
        setViewMore(holder.binding.content,holder.binding.viewMore)
        // 그리드 뷰에서 개별 옷 클릭 시
//        img.setOnClickListener() {
//            val intent = Intent( context ,ClothActivity::class.java)
//            intent.putExtra("userIdx", userIdx)
//            intent.putExtra("clothIdx", img_list[position]!!.clthIdx)
//
//            ContextCompat.startActivity(context, intent, null)
//        }


    }

    override fun getItemCount(): Int {
        return img_list!!.size
    }
    private fun setViewMore(contentTextView: TextView, viewMoreTextView: TextView){
        // getEllipsisCount()을 통한 더보기 표시 및 구현
        contentTextView.post{
            val lineCount = contentTextView.layout.lineCount
            if (lineCount > 0) {
                if (contentTextView.layout.getEllipsisCount(lineCount - 1) > 0) {
                    // 더보기 표시
                    viewMoreTextView.visibility = View.VISIBLE

                    // 더보기 클릭 이벤트
                    viewMoreTextView.setOnClickListener {
                        contentTextView.maxLines = Int.MAX_VALUE
                        viewMoreTextView.visibility = View.GONE
                    }
                }
            }
        }
    }
}

