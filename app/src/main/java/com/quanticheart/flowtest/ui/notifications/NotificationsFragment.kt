package com.quanticheart.flowtest.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.quanticheart.core.navigateTo
import com.quanticheart.core.observe
import com.quanticheart.flowtest.R
import com.quanticheart.flowtest.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.textHome

        ViewModelProvider(this)[NotificationsViewModel::class.java].run {

            text.observe(viewLifecycleOwner) {
                textView.text = it
            }

            booleanEvent.observe(viewLifecycleOwner) {
                if (it) {
                    Log.e("EVENT", "BOOLEAN TRUE")
                } else {
                    Log.e("EVENT", "BOOLEAN FALSE")
                }
            }

            simpleEvent.observe(this@NotificationsFragment) {
                if (it) {
                    Log.e("EVENT SINGLE", "COLLECTED TRUE")
                } else {
                    Log.e("EVENT SINGLE", "COLLECTED FALSE")
                }
            }

            screenNextEvent.observe(this@NotificationsFragment) {
                Log.e("EVENT SCREEN", "CALL SCREEN")
                navigateTo(R.id.nav_host_fragment_activity_main, R.id.navigation_home)
            }

            screenBackEvent.observe(this@NotificationsFragment) {
                Log.e("EVENT SCREEN", "CALL SCREEN")
                navigateTo(R.id.nav_host_fragment_activity_main, R.id.navigation_dashboard)
            }

            _binding?.booleanEvent?.setOnClickListener {
                sendBooleanEvent()
            }

            _binding?.singleEvent?.setOnClickListener {
                sendSingleEvent()
            }

            _binding?.screenEventNext?.setOnClickListener {
                sendScreenNextEvent()
            }

            _binding?.screenEventBack?.setOnClickListener {
                sendScreenBackEvent()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}