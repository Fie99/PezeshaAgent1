package com.pezesha.agent.ui.registersme.documents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.pezesha.agent.R
import com.pezesha.agent.databinding.ActivityDocumentsBinding

class DocumentsActivity : AppCompatActivity() {
    private var _binding: ActivityDocumentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDocumentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.documentsNavHostContainer) as NavHostFragment
        navController = navHostFragment.navController


    }
}