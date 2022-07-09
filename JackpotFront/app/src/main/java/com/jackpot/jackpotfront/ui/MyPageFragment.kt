package com.jackpot.jackpotfront.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.jackpot.jackpotfront.R
import com.jackpot.jackpotfront.adapter.ListAdapterGrid
import com.jackpot.jackpotfront.databinding.FragmentMyPageBinding
import com.jackpot.jackpotfront.retrofit.data.TestPostObject

class MyPageFragment : Fragment() {

    lateinit var binding: FragmentMyPageBinding
    var userIdx: Int? = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(layoutInflater,container,false)

//        var testObject: List<TestPostObject> = ArrayList<TestPostObject>()
//        var postObject: TestPostObject = TestPostObject(1,
//            "https://hana-umc.shop/test/display/1657267165243img.jpg",
//            "testStringqfi;ljqwqwfj;jiwqfj;wfq;jilqwflfuawfhawklff;lqwfl;qwjifqfwafawfajil;fwa;fjil;wf")
//        testObject?.add(postObject)

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

}