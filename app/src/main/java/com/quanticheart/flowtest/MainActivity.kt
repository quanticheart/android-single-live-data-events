package com.quanticheart.flowtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.quanticheart.core.setupNavigation
import com.quanticheart.flowtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
        navView.setupNavigation(navController, setOf(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
        ))
//        navView.setupNavigation(navController, R.menu.bottom_nav_menu)
//        val weakReference = WeakReference(navView)
//        weakReference.get()?.setOnItemSelectedListener {
//            NavigationUI.onNavDestinationSelected(it, navController)
//            navController.popBackStack(it.itemId, inclusive = false)
//            true
//        }
//        navController.addOnDestinationChangedListener(
//            object : NavController.OnDestinationChangedListener {
//                override fun onDestinationChanged(
//                    controller: NavController,
//                    destination: NavDestination,
//                    arguments: Bundle?
//                ) {
//                    if (weakReference.get() == null) {
//                        navController.removeOnDestinationChangedListener(this)
//                        return
//                    }
//                    navView.menu.forEach { item ->
//                        if (destination.hierarchy.any { it.id == item.itemId }) {
//                            item.isChecked = true
//                        }
//                    }
//                }
//            })

    }
}