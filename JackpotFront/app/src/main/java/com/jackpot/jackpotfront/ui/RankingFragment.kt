package com.jackpot.jackpotfront.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jackpot.jackpotfront.R
import com.jackpot.jackpotfront.databinding.FragmentMyPageBinding
import com.jackpot.jackpotfront.databinding.FragmentPostBinding
import com.jackpot.jackpotfront.databinding.FragmentRankingBinding


class RankingFragment : Fragment() {

    lateinit var binding: FragmentRankingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankingBinding.inflate(layoutInflater,container,false)

        return binding.root
    }
}