package com.pezesha.agent.ui.registersme.applyLoan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.pezesha.agent.R
import com.pezesha.agent.databinding.ActivityApplyLoanBinding

class ApplyLoanActivity : AppCompatActivity() {
    private var _binding: ActivityApplyLoanBinding? = null

    private val binding get() = _binding!!
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityApplyLoanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.applyLoanNavHostContainer) as NavHostFragment
        navController = navHostFragment.navController
    }
}