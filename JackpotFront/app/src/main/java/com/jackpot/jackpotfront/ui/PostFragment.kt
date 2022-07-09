package com.jackpot.jackpotfront.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.jackpot.jackpotfront.adapter.ListAdapterGrid
import com.jackpot.jackpotfront.databinding.FragmentPostBinding
import com.jackpot.jackpotfront.retrofit.data.AllPostsObject



class PostFragment : Fragment() {

    lateinit var binding :FragmentPostBinding
    var userIdx: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        var testObject: ArrayList<AllPostsObject>? = null


        ////// 그리드 뷰로 화면 출력 //////
        val listManager = GridLayoutManager(context,1)
        var listAdapter = ListAdapterGrid(context, userIdx, testObject)

//        var recyclerList = binding.recyclerView.apply {
//            setHasFixedSize(true)
//            layoutManager = listManager
//            adapter = listAdapter
//        }
        ////// 그리드 뷰로 화면 출력 //////

        binding.fab.setOnClickListener {
            val addIntent = Intent(activity,AddPostsActivity::class.java)
            startActivity(addIntent)
        }

        return binding.root
    }


}