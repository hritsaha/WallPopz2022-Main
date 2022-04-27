package com.example.animewallz2022

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.animewallz2022.databinding.ActivityMainBinding
import com.example.animewallz2022.fragments.CategoriesFragment
import com.example.animewallz2022.fragments.DownloadsFragment
import com.example.animewallz2022.fragments.HomeFragment
import com.example.animewallz2022.fragments.TrendingFragment

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.root)


        //Fragments
        val homeFragment : Fragment = HomeFragment()
        val downloadsFragment : Fragment = DownloadsFragment()
        val categoriesFragment : Fragment = CategoriesFragment()
        val trendingFragment : Fragment = TrendingFragment()

        setCurrentFragment(homeFragment)
        //yaha se

//        Bottom Navigation Bar Working set UP
//        binding.myBottomNav.setOnNavigationItemSelectedListener {
//            when(it.itemId){
//                R.id.homeF -> setCurrentFragment(homeFragment)
//                R.id.categoriesF->setCurrentFragment(categoriesFragment)
//                else -> setCurrentFragment(downloadsFragment)
//            }
//            true
//        }

        //yaha tak
        binding.myBottomNav.setItemSelected(R.id.homeF)
        binding.myBottomNav.setOnItemSelectedListener {
            when(it){
                R.id.homeF -> setCurrentFragment(homeFragment)
                R.id.categoriesF->setCurrentFragment(categoriesFragment)
                R.id.trendingF->setCurrentFragment(trendingFragment)
                else -> setCurrentFragment(downloadsFragment)

            }
        }


    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_main,fragment)
            commit()
        }

    }

}