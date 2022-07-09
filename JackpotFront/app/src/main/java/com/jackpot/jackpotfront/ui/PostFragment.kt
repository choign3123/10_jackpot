package com.jackpot.jackpotfront.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.jackpot.jackpotfront.R
import com.jackpot.jackpotfront.adapter.ListAdapterGrid
import com.jackpot.jackpotfront.databinding.FragmentPostBinding
import com.jackpot.jackpotfront.retrofit.data.AllPostsObject



class PostFragment : Fragment() {

    val binding by lazy {FragmentPostBinding.inflate(layoutInflater)}
    var userIdx: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var testObject: ArrayList<AllPostsObject>? = null


        ////// 그리드 뷰로 화면 출력 //////
        val listManager = GridLayoutManager(context,1)
        var listAdapter = ListAdapterGrid(context, userIdx, testObject)

        var recyclerList = binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = listManager
            adapter = listAdapter
        }
        ////// 그리드 뷰로 화면 출력 //////

        return inflater.inflate(R.layout.fragment_post, container, false)
    }


}