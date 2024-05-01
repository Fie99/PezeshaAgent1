package com.pezesha.agent.ui.main

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.pezesha.agent.R
import com.pezesha.agent.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainNavHostContainer) as NavHostFragment

        with(navHostFragment.navController) {
            binding.bottomNavigationView.setupWithNavController(this)

            addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.homeFragment
                    || destination.id == R.id.customersListFragment
                    || destination.id == R.id.loansListFragment
                    || destination.id == R.id.addSmeFragment
                ) binding.bottomNavigationView.visibility = VISIBLE
                else binding.bottomNavigationView.visibility = GONE
            }
        }
    }

}
