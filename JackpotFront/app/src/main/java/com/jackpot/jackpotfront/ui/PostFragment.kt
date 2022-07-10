package com.jackpot.jackpotfront.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jackpot.jackpotfront.R
import com.jackpot.jackpotfront.adapter.ListAdapterGrid
import com.jackpot.jackpotfront.databinding.FragmentPostBinding
import com.jackpot.jackpotfront.retrofit.RetrofitService
import com.jackpot.jackpotfront.retrofit.data.GetAllPostResult
import com.jackpot.jackpotfront.retrofit.data.UserIdxObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostFragment : Fragment() {

    lateinit var binding :FragmentPostBinding
    val retro = RetrofitService.create()

    var adapter: Adapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(layoutInflater,container,false)


//        var testObject: ArrayList<TestPostObject> = ArrayList()
//        var postObject: TestPostObject = TestPostObject(UserIdxObject.userIdx!!,
//            "https://hana-umc.shop/test/display/1657267165243img.jpg",
//        "testStringqfi;ljqwqwfj;jiwqfj;wfq;jilqwflfuawfhawklff;lqwfl;qwjifqfwafawfajil;fwa;fjil;wf")
//        testObject?.add(postObject)

        retro.getAllPost(UserIdxObject.userIdx, 0).enqueue(object : Callback<GetAllPostResult> {
            override fun onResponse(
                call: Call<GetAllPostResult>,
                response: Response<GetAllPostResult>
            ) {
                ////// 그리드 뷰로 화면 출력 //////
                val listManager = GridLayoutManager(context,1)
                var listAdapter = ListAdapterGrid(context, UserIdxObject.userIdx, response.body()!!.result)

                var recyclerList = binding.recyclerView.apply {
                    setHasFixedSize(true)
                    layoutManager = listManager
                    adapter = listAdapter
                }

                ////// 그리드 뷰로 화면 출력 //////
            }

            override fun onFailure(call: Call<GetAllPostResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }
        })



        binding.fab.setOnClickListener {
            val addIntent = Intent(activity,AddPostsActivity::class.java)
            startActivity(addIntent)
        }


        return binding.root
    }


}