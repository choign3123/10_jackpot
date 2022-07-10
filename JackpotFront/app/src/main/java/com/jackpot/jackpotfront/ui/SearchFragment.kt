package com.jackpot.jackpotfront.ui

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.jackpot.jackpotfront.R
import com.jackpot.jackpotfront.adapter.ListAdapterGrid
import com.jackpot.jackpotfront.databinding.FragmentPostBinding
import com.jackpot.jackpotfront.databinding.FragmentSearchBinding
import com.jackpot.jackpotfront.retrofit.RetrofitService
import com.jackpot.jackpotfront.retrofit.data.GetAllPostResult
import com.jackpot.jackpotfront.retrofit.data.SearchPostResult
import com.jackpot.jackpotfront.retrofit.data.UserIdxObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var binding:FragmentSearchBinding
    val retro = RetrofitService.create()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater,container,false)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("MYTAG", query)

                                retro.searchPost(UserIdxObject.userIdx,query,0).enqueue(object : Callback<GetAllPostResult>{
                    override fun onResponse(
                        call: Call<GetAllPostResult>,
                        response: Response<GetAllPostResult>
                    ) {
                        ////// 그리드 뷰로 화면 출력 //////
                        val listManager = GridLayoutManager(context, 1)
                        var listAdapter =
                            ListAdapterGrid(context, UserIdxObject.userIdx, response.body()!!.result)

                        var recyclerList = binding.recyclerView.apply {
                            setHasFixedSize(true)
                            layoutManager = listManager
                            adapter = listAdapter
                        }
                        ////// 그리드 뷰로 화면 출력 //////
                        Log.d("MYTAG", "SUCCESS")
                        Log.d("MYTAG", response.body()?.result.toString())

                    }

                    override fun onFailure(call: Call<GetAllPostResult>, t: Throwable) {
                        Log.d("MYTAG", t.message.toString())
                        Log.d("MYTAG", "FAIL")
                    }
                })
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })


//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//
//                        override fun onQueryTextChange(p0: String?): Boolean {
//                            return false
//
//            }
//            override fun onQueryTextSubmit(s: String): Boolean {
//
//                retro.searchPost(UserIdxObject.userIdx,s,0).enqueue(object : Callback<GetAllPostResult>{
//                    override fun onResponse(
//                        call: Call<GetAllPostResult>,
//                        response: Response<GetAllPostResult>
//                    ) {
//                        ////// 그리드 뷰로 화면 출력 //////
//                        val listManager = GridLayoutManager(context, 1)
//                        var listAdapter =
//                            ListAdapterGrid(context, UserIdxObject.userIdx, response.body()!!.result)
//
//                        var recyclerList = binding.recyclerView.apply {
//                            setHasFixedSize(true)
//                            layoutManager = listManager
//                            adapter = listAdapter
//                        }
//                        ////// 그리드 뷰로 화면 출력 //////
//                        Log.d("MYTAG", "SUCCESS")
//                        Log.d("MYTAG", response.body()?.result.toString())
//
//                    }
//
//                    override fun onFailure(call: Call<GetAllPostResult>, t: Throwable) {
//                        Log.d("MYTAG", t.message.toString())
//                        Log.d("MYTAG", "FAIL")
//                    }
//                })
//                return true
//            }
//        })
//
//        binding.searchBtn.setOnClickListener{
//            val query = binding.searchView.query.toString()
//
//            Log.d("MYTAG", query)
//
//            retro.searchPost(UserIdxObject.userIdx,query,0).enqueue(object : Callback<GetAllPostResult>{
//                override fun onResponse(
//                    call: Call<GetAllPostResult>,
//                    response: Response<GetAllPostResult>
//                ) {
//                    ////// 그리드 뷰로 화면 출력 //////
//                    val listManager = GridLayoutManager(context, 1)
//                    var listAdapter =
//                        ListAdapterGrid(context, UserIdxObject.userIdx, response.body()!!.result)
//
//                    var recyclerList = binding.recyclerView.apply {
//                        setHasFixedSize(true)
//                        layoutManager = listManager
//                        adapter = listAdapter
//                    }
//                    ////// 그리드 뷰로 화면 출력 //////
//                    Log.d("MYTAG", "SUCCESS")
//                    Log.d("MYTAG", response.body()?.result.toString())
//
//                }
//
//                    override fun onFailure(call: Call<GetAllPostResult>, t: Throwable) {
//                        Log.d("MYTAG", t.message.toString())
//                        Log.d("MYTAG", "FAIL")
//                    }
//                })
        return binding.root
    }
}

