package com.jackpot.jackpotfront.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.jackpot.jackpotfront.R
import com.jackpot.jackpotfront.databinding.ItemGridBinding
import com.jackpot.jackpotfront.retrofit.RetrofitService
import com.jackpot.jackpotfront.retrofit.data.DeletePostResult
import com.jackpot.jackpotfront.retrofit.data.TestPostObject
import com.jackpot.jackpotfront.retrofit.data.UserIdxObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListAdapterGrid(val context: Context?, var userIdx: Int?, val img_list: ArrayList<TestPostObject>)
    : RecyclerView.Adapter<ListAdapterGrid.GridAdapter>() {

    class GridAdapter(val binding: ItemGridBinding): RecyclerView.ViewHolder(binding.root)

    // 즐겨찾기 애니메이션
    var isHearting: Boolean = false
    lateinit var lottie: LottieAnimationView


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

        holder.binding.content.text = img_list[position].content
        setViewMore(holder.binding.content,holder.binding.viewMore)

        // 그리드 뷰에서 개별 옷 클릭 시
//        img.setOnClickListener() {
//            val intent = Intent( context ,ClothActivity::class.java)
//            intent.putExtra("userIdx", userIdx)
//            intent.putExtra("clothIdx", img_list[position]!!.clthIdx)
//
//            ContextCompat.startActivity(context, intent, null)
//        }

        holder.binding.deleteIv.setOnClickListener {
            Log.d("delete btn", "눌렸음 ")
            val retrofit = RetrofitService.create()
            val service = retrofit.deletePost(UserIdxObject.userIdx,img_list[position].postIdx).enqueue(object : Callback<DeletePostResult>{
                override fun onResponse(
                    call: Call<DeletePostResult>,
                    response: Response<DeletePostResult>
                ) {
                    Log.d("삭제 성공","{${response.body()}}")
                }

                override fun onFailure(call: Call<DeletePostResult>, t: Throwable) {
                    Log.d("삭제 실패 래요 ","실패한 삭제 ")
                }
            })
        }

        holder.binding.favButton.setOnClickListener {

            if(!isHearting){
                //기본이 false이므로 false가 아닐때 실행한다.
                //애니메이션의 커스텀
                //0f가 0퍼센트, 1F가 100퍼센트
                //ofFloat(시작지점, 종료지점).setDuration(지속시간)
                // Custom animation speed or duration.
                val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(500)
                animator.addUpdateListener {
                    holder.binding.favButton.progress = it.animatedValue as Float
                }
                animator.start()
                isHearting = true // 그리고 트루로 바꾼다.
                Log.d("MYTAG", "MainActivity - onClickButton() called / 좋아요 버튼이 클릭됨")
            }else{
                //트루일때가 실행된다.
                val animator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(500)
                animator.addUpdateListener {
                    holder.binding.favButton.progress = it.animatedValue as Float
                }
                animator.start()
                isHearting = false // 다시 false로 된다.
                Log.d("MYTAG", "MainActivity - onClickButton() called / 좋아요 버튼이 꺼짐")
            }

            Log.d("MYTAG", "어댑터에서는.. " + isHearting.toString())
        }
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

//    fun onClickButton(view: View) {
//
//        val view: View = View.inflate(R.layout.item_grid)
//        if(!isHearting){
//            //기본이 false이므로 false가 아닐때 실행한다.
//            //애니메이션의 커스텀
//            //0f가 0퍼센트, 1F가 100퍼센트
//            //ofFloat(시작지점, 종료지점).setDuration(지속시간)
//            // Custom animation speed or duration.
//            val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(500)
//            animator.addUpdateListener {
//                holder.binding.favButton.progress = it.animatedValue as Float
//            }
//            animator.start()
//            isHearting = true // 그리고 트루로 바꾼다.
//            Log.d("MYTAG", "MainActivity - onClickButton() called / 좋아요 버튼이 클릭됨")
//        }else{
//            //트루일때가 실행된다.
//            val animator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(500)
//            animator.addUpdateListener {
//                holder.binding.favButton.progress = it.animatedValue as Float
//            }
//            animator.start()
//            isHearting = false
//            // 다시 false로 된다.
//            Log.d("MYTAG", "MainActivity - onClickButton() called / 좋아요 버튼이 꺼짐")
//        }
//    }
}

