package com.jatin.practiseandroid.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jatin.practiseandroid.ui.gallery.GalleryFragment
import com.jatin.practiseandroid.ui.home.HomeFragment
import com.jatin.practiseandroid.ui.slideshow.SlideshowFragment

class MyFragmentStateAdapter(fragmentActivity: FragmentActivity):
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
       return 3;
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            1 -> SlideshowFragment()
            2 -> GalleryFragment()
            else -> HomeFragment()
        }
    }
}