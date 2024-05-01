package com.pezesha.agent.ui.auth

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.pezesha.agent.PezeshaAgentApp
import com.pezesha.agent.R
import com.pezesha.agent.databinding.ActivityAuthBinding
import com.pezesha.agent.utils.Constants.NAVIGATION_ANIMATION_DURATION
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        initSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavGraph()
    }

    private fun initNavGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.auth_nav_host) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph_auth)

        val startDest = if (PezeshaAgentApp.sharedPrefsManager.appFirstTimeUse) R.id.welcomeFragment
        else R.id.loginFragment

        graph.setStartDestination(startDest)
        val navController = navHostFragment.navController
        navController.setGraph(graph, intent.extras)
    }

    private fun initSplashScreen() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }

            setOnExitAnimationListener { splashScreen ->
//                splashScreen.iconView
                ObjectAnimator.ofFloat(
                    splashScreen.view,
                    View.TRANSLATION_Y,
                    0f,
                    splashScreen.view.height.toFloat()
                ).apply {
                    interpolator = DecelerateInterpolator()
                    duration = NAVIGATION_ANIMATION_DURATION
                    doOnEnd { splashScreen.remove() }
                    start()
                }
            }
        }
    }

}
