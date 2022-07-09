package com.jackpot.jackpotfront.ui

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.jackpot.jackpotfront.R
import com.jackpot.jackpotfront.adapter.ListAdapterGrid
import com.jackpot.jackpotfront.databinding.FragmentPostBinding
import com.jackpot.jackpotfront.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding:FragmentSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater,container,false)
        binding.searchBtn.setOnClickListener{
            val query = binding.searchView.query

            Log.d("query", "$query")
            // Retrofit 통신 자리
        }

        return binding.root
    }
}
