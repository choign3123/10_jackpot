package com.jackpot.jackpotfront.ui

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.jackpot.jackpotfront.R
import com.jackpot.jackpotfront.adapter.ListAdapterGrid
import com.jackpot.jackpotfront.databinding.FragmentMyPageBinding
import com.jackpot.jackpotfront.databinding.ItemGridBinding
import com.jackpot.jackpotfront.retrofit.RetrofitService
import com.jackpot.jackpotfront.retrofit.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageFragment : Fragment() {

    lateinit var binding: FragmentMyPageBinding
    val retro = RetrofitService.create()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(layoutInflater,container,false)

        retro.getMyPost(UserIdxObject.userIdx, 0).enqueue(object : Callback<GetMyPostResult>{
            override fun onResponse(
                call: Call<GetMyPostResult>,
                response: Response<GetMyPostResult>
            ) {
                Log.d("MYTAG","SUCCESS")
                Log.d("MYTAG",response.body()?.result?.posts.toString())


                ////// 그리드 뷰로 화면 출력 //////
                val listManager = GridLayoutManager(context,1)
                var listAdapter = ListAdapterGrid(context, UserIdxObject.userIdx,
                response.body()!!.result.posts)

                var recyclerList = binding.recyclerView.apply {
                    setHasFixedSize(true)
                    layoutManager = listManager
                    adapter = listAdapter
                }
                ////// 그리드 뷰로 화면 출력 //////

                binding.textView4.text = response.body()!!.result.id
            }

            override fun onFailure(call: Call<GetMyPostResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }

        })

        ////// 그리드 뷰로 화면 출력 //////
//        val listManager = GridLayoutManager(context,1)
//        var listAdapter = ListAdapterGrid(co ntext, userIdx, testObject)
//
//        var recyclerList = binding.recyclerView.apply {
//            setHasFixedSize(true)
//            layoutManager = listManager
//            adapter = listAdapter
//        }

        return binding.root
    }

    class ListAdapterGrid(val context: Context?, var userIdx: Int?, val img_list: List<PostsObject>)
        : RecyclerView.Adapter<ListAdapterGrid.GridAdapter>() {

        class GridAdapter(val binding: ItemGridBinding): RecyclerView.ViewHolder(binding.root)



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridAdapter {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)

            return GridAdapter(ItemGridBinding.bind(view))
        }

        override fun onBindViewHolder(holder: GridAdapter, position: Int) {
            val img = holder.binding.gridViewImg
//        img.setImageURI(img_list[position].clthImgUrl.toUri())
            Glide.with(context!!)
                .load("https://test-domains.shop/posts/img/display/"+img_list[position].imgUrl)
                .thumbnail(0.1f)
                .into(img)

            holder.binding.content.text = img_list[position].content
            holder.binding.textView.text = img_list[position].userName
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
}