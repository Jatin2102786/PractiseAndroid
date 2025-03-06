package com.jatin.practiseandroid.viewPager

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.jatin.practiseandroid.R

class ViewPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_pager)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
    val viewPager2 = findViewById<ViewPager2>(R.id.pager)
    val myAdapter = MyFragmentStateAdapter(this)

//
//    TabLayoutMediator(tabLayout, viewPager2) { tab: TabLayout.Tab, position: Int ->
//        when (position) {
//            0 -> {
//                tab.text = "Chats"
//                tab.setIcon(R.drawable.ic_chat)
//            }
//            1 -> {
//                tab.text = "Status"
//                tab.setIcon(R.drawable.ic_status)
//            }
//            2 -> {
//                tab.text = "Calls"
//                tab.setIcon(R.drawable.ic_call)
//            }
//        }
//    }.attach()
}
//}