package com.dicoding.submissionakhir.ui.adapter


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.submissionakhir.ui.fragment.Followers
import com.dicoding.submissionakhir.ui.fragment.Following

class SectionsPagerAdapter(activity: AppCompatActivity, data: Bundle) : FragmentStateAdapter(activity) {

    private var fragmentBundle: Bundle = data

    init {
        fragmentBundle = data
    }
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null

        when(position){
            0 -> fragment = Followers()
            1 -> fragment = Following()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

}