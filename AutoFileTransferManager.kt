package com.example.autofiletransfer

import android.content.Context
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log

class AutoFileTransferManager(
    private val context: Context,
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel
) {

    // Starts peer discovery silently behind the scenes right after camera opens
    fun startAutomaticDiscovery() {
        try {
            manager.discoverPeers(channel, object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    Log.d("AutoTransfer", "Automatic peer discovery initiated successfully.")
                }
                override fun onFailure(reasonCode: Int) {
                    Log.e("AutoTransfer", "Discovery failed: $reasonCode")
                }
            })
        } catch (e: SecurityException) {
            Log.e("AutoTransfer", "Permissions missing for discovery: ${e.message}")
        }
    }

    // Instantly connects using the MAC/ID parsed from the visual matrix scan
    fun autoConnectToTargetDevice(targetDeviceAddress: String, onConnectSuccess: () -> Unit) {
        val config = WifiP2pConfig().apply {
            deviceAddress = targetDeviceAddress
            groupOwnerIntent = 15 // Force highest bandwidth channel allocation (5GHz/6GHz)
        }

        try {
            manager.connect(channel, config, object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    Log.d("AutoTransfer", "Background network tunnel formed completely out of the box.")
                    onConnectSuccess()
                }
                override fun onFailure(reasonCode: Int) {
                    Log.e("AutoTransfer", "Auto-connection failed to execute: $reasonCode")
                }
            })
        } catch (e: SecurityException) {
            Log.e("AutoTransfer", "Security violation: ${e.message}")
        }
    }
}
