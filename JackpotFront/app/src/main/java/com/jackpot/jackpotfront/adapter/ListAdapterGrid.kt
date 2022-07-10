package com.jackpot.jackpotfront.adapter

import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jackpot.jackpotfront.R
import com.jackpot.jackpotfront.databinding.ItemGridBinding
import com.jackpot.jackpotfront.retrofit.RetrofitService
import com.jackpot.jackpotfront.retrofit.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListAdapterGrid(val context: Context?, var userIdx: Int?, val img_list: List<GetAllPostObject>)
    : RecyclerView.Adapter<ListAdapterGrid.GridAdapter>() {

    class GridAdapter(val binding: ItemGridBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridAdapter {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)

        return GridAdapter(ItemGridBinding.bind(view))
    }

    override fun onBindViewHolder(holder: GridAdapter, position: Int) {
        val img = holder.binding.gridViewImg
        val retrofit = RetrofitService.create()

//        img.setImageURI(img_list[position].clthImgUrl.toUri())
        Glide.with(context!!)
            .load("https://test-domains.shop/posts/img/display/" + img_list[position].imgUrl)
            .thumbnail(0.1f)
            .into(img)

        holder.binding.content.text = img_list[position].content
        holder.binding.textView.text = img_list[position].userName
        setViewMore(holder.binding.content, holder.binding.viewMore)

        holder.binding.deleteIv.setOnClickListener {
            Log.d("delete btn", "눌렸음 ")

            val builder = AlertDialog.Builder(context)
            builder.setTitle("삭제")
                .setMessage("정말 삭제합니까?")

            builder.setPositiveButton("OK") { dialogInterface, i ->

                retrofit.deletePost(UserIdxObject.userIdx, img_list[position].postIdx)
                    .enqueue(object : Callback<DeletePostResult> {
                        override fun onResponse(
                            call: Call<DeletePostResult>,
                            response: Response<DeletePostResult>
                        ) {
                            Log.d("삭제 성공", "{${response.body()}}")
                        }

                        override fun onFailure(call: Call<DeletePostResult>, t: Throwable) {
                            Log.d("삭제 실패 래요 ", "실패한 삭제 ")
                        }
                    })
            }
            builder.setNegativeButton("EXIT") { dialogInterface, i ->
            }
            val dialog = builder.create()
            dialog.show()

        }

        holder.binding.imageView.setOnClickListener {

            val builder = AlertDialog.Builder(context)
            builder.setTitle("신고")
                .setMessage("정말 신고합니까?")

            builder.setPositiveButton("OK") { dialogInterface, i ->

                retrofit.patchReport(UserIdxObject.userIdx, img_list[position].postIdx)
                    .enqueue(object : Callback<PatchReportResult> {
                        override fun onResponse(
                            call: Call<PatchReportResult>,
                            response: Response<PatchReportResult>
                        ) {
                            Log.d("MYTAG", "SUCCESS")
                            Log.d("MYTAG", response.body()?.result.toString())

                        }

                        override fun onFailure(call: Call<PatchReportResult>, t: Throwable) {
                            Log.d("MYTAG", t.message.toString())
                            Log.d("MYTAG", "FAIL")
                        }
                    })
            }
            builder.setNegativeButton("EXIT") { dialogInterface, i ->
            }
            val dialog = builder.create()
            dialog.show()



        }

        if(img_list[position].checkEmoji[0]){
            holder.binding.favButton1.setImageResource(R.drawable.img21)
        }else{
            holder.binding.favButton1.setImageResource(R.drawable.img11)
        }
        if(img_list[position].checkEmoji[1]){
            holder.binding.favButton2.setImageResource(R.drawable.img22)
        }else{
            holder.binding.favButton2.setImageResource(R.drawable.img12)
        }
        if(img_list[position].checkEmoji[2]){
            holder.binding.favButton3.setImageResource(R.drawable.img23)
        }else{
            holder.binding.favButton3.setImageResource(R.drawable.img13)
        }
        if(img_list[position].checkEmoji[2]){
            holder.binding.favButton4.setImageResource(R.drawable.img24)
        }else{
            holder.binding.favButton4.setImageResource(R.drawable.img14)
        }


        holder.binding.favButton1.setOnClickListener {
            if (img_list[position].checkEmoji[0]) {
                retrofit.deletePostsEmoji(
                    PostsEmoji(
                        UserIdxObject.userIdx!!,
                        img_list[position].postIdx,
                        0
                    )
                ).enqueue(object : Callback<PostEmojiRes> {
                    override fun onResponse(
                        call: Call<PostEmojiRes>,
                        response: Response<PostEmojiRes>
                    ) {
                        holder.binding.favButton1.setImageResource(R.drawable.img11)
                    }

                    override fun onFailure(call: Call<PostEmojiRes>, t: Throwable) {
                        Log.d("이모지 통신 실패", "실패")
                    }
                })
            } else {
                retrofit.postPostsEmoji(
                    PostsEmoji(
                        UserIdxObject.userIdx!!,
                        img_list[position].postIdx,
                        0
                    )
                ).enqueue(object : Callback<PostEmojiRes> {
                    override fun onResponse(
                        call: Call<PostEmojiRes>,
                        response: Response<PostEmojiRes>
                    ) {
                        holder.binding.favButton1.setImageResource(R.drawable.img21)
                    }

                    override fun onFailure(call: Call<PostEmojiRes>, t: Throwable) {
                        Log.d("이모지 통신 실패", "실패")
                    }
                })
            }
            holder.binding.favButton2.setOnClickListener {
                if (img_list[position].checkEmoji[1]) {
                    retrofit.deletePostsEmoji(
                        PostsEmoji(
                            UserIdxObject.userIdx!!,
                            img_list[position].postIdx,
                            1
                        )
                    ).enqueue(object : Callback<PostEmojiRes> {
                        override fun onResponse(
                            call: Call<PostEmojiRes>,
                            response: Response<PostEmojiRes>
                        ) {
                            holder.binding.favButton2.setImageResource(R.drawable.img12)
                        }

                        override fun onFailure(call: Call<PostEmojiRes>, t: Throwable) {
                            Log.d("이모지 통신 실패", "실패")
                        }
                    })
                } else {
                    retrofit.postPostsEmoji(
                        PostsEmoji(
                            UserIdxObject.userIdx!!,
                            img_list[position].postIdx,
                            1
                        )
                    ).enqueue(object : Callback<PostEmojiRes> {
                        override fun onResponse(
                            call: Call<PostEmojiRes>,
                            response: Response<PostEmojiRes>
                        ) {
                            holder.binding.favButton2.setImageResource(R.drawable.img22)
                        }

                        override fun onFailure(call: Call<PostEmojiRes>, t: Throwable) {
                            Log.d("이모지 통신 실패", "실패")
                        }
                    })
                }
                holder.binding.favButton3.setOnClickListener {
                    if (img_list[position].checkEmoji[2]) {
                        retrofit.deletePostsEmoji(
                            PostsEmoji(
                                UserIdxObject.userIdx!!,
                                img_list[position].postIdx,
                                2
                            )
                        ).enqueue(object : Callback<PostEmojiRes> {
                            override fun onResponse(
                                call: Call<PostEmojiRes>,
                                response: Response<PostEmojiRes>
                            ) {
                                holder.binding.favButton3.setImageResource(R.drawable.img13)
                            }

                            override fun onFailure(call: Call<PostEmojiRes>, t: Throwable) {
                                Log.d("이모지 통신 실패", "실패")
                            }
                        })
                    } else {
                        retrofit.postPostsEmoji(
                            PostsEmoji(
                                UserIdxObject.userIdx!!,
                                img_list[position].postIdx,
                                2
                            )
                        ).enqueue(object : Callback<PostEmojiRes> {
                            override fun onResponse(
                                call: Call<PostEmojiRes>,
                                response: Response<PostEmojiRes>
                            ) {
                                holder.binding.favButton3.setImageResource(R.drawable.img23)
                            }

                            override fun onFailure(call: Call<PostEmojiRes>, t: Throwable) {
                                Log.d("이모지 통신 실패", "실패")
                            }
                        })
                    }
                }
                holder.binding.favButton4.setOnClickListener {
                    if (img_list[position].checkEmoji[3]) {
                        retrofit.deletePostsEmoji(
                            PostsEmoji(
                                UserIdxObject.userIdx!!,
                                img_list[position].postIdx,
                                3
                            )
                        ).enqueue(object : Callback<PostEmojiRes> {
                            override fun onResponse(
                                call: Call<PostEmojiRes>,
                                response: Response<PostEmojiRes>
                            ) {
                                holder.binding.favButton4.setImageResource(R.drawable.img14)
                            }

                            override fun onFailure(call: Call<PostEmojiRes>, t: Throwable) {
                                Log.d("이모지 통신 실패", "실패")
                            }
                        })
                    } else {
                        retrofit.postPostsEmoji(
                            PostsEmoji(
                                UserIdxObject.userIdx!!,
                                img_list[position].postIdx,
                                3
                            )
                        ).enqueue(object : Callback<PostEmojiRes> {
                            override fun onResponse(
                                call: Call<PostEmojiRes>,
                                response: Response<PostEmojiRes>
                            ) {
                                holder.binding.favButton4.setImageResource(R.drawable.img24)
                            }

                            override fun onFailure(call: Call<PostEmojiRes>, t: Throwable) {
                                Log.d("이모지 통신 실패", "실패")
                            }
                        })
                    }
                }
            }
        }}


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
    }

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


