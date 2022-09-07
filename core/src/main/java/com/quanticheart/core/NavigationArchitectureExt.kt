package com.quanticheart.core

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.ref.WeakReference

//
// Created by Jonn Alves on 31/08/22.
//
fun Activity.navigateTo(navigationHost: Int, navigationResID: Int, bundle: Bundle? = null) {
    try {
        bundle?.let {
            Navigation.findNavController(this, navigationHost)
                .navigate(navigationResID, it)
        } ?: run {
            Navigation.findNavController(this, navigationHost)
                .navigate(navigationResID)
        }
    } catch (e: Exception) {
    }
}

fun Fragment.navigateTo(navigationResID: Int, bundle: Bundle? = null) {
    try {
        view?.let { view ->
            bundle?.let { bundle ->
                view.findNavController().navigate(navigationResID, bundle)
            } ?: run {
                view.findNavController().navigate(navigationResID)
            }
        }
    } catch (e: Exception) {
    }
}

fun Fragment.navigateTo(navigationHost: Int, navigationResID: Int, bundle: Bundle? = null) {
    try {
        bundle?.let {
            Navigation.findNavController(requireActivity(), navigationHost)
                .navigate(navigationResID, it)
        } ?: run {
            Navigation.findNavController(requireActivity(), navigationHost)
                .navigate(navigationResID)
        }
    } catch (e: Exception) {
    }
}

fun BottomNavigationView.setupNavigation(navController: NavController, menuIDs: Set<Int>) {

    val activity = (context as AppCompatActivity)
    val appBarConfiguration = AppBarConfiguration(menu)
    activity.setupActionBarWithNavController(
        navController,
        appBarConfiguration
    )

    val weakReference = WeakReference(this)
    weakReference.get()?.setOnItemSelectedListener {
        NavigationUI.onNavDestinationSelected(it, navController)
        navController.popBackStack(it.itemId, inclusive = false)
        true
    }
    navController.addOnDestinationChangedListener(
        object : NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                if (weakReference.get() == null) {
                    navController.removeOnDestinationChangedListener(this)
                    return
                }
                weakReference.get()?.menu?.forEach { item ->
                    if (destination.hierarchy.any { it.id == item.itemId }) {
                        item.isChecked = true
                    }
                }
            }
        })
}
