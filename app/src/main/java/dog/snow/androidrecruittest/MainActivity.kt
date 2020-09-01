package dog.snow.androidrecruittest

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_banner.*
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.main_activity){


    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))

        registerConectivityCallback()


    }

    private fun registerConectivityCallback() {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerDefaultNetworkCallback(NetworkCallback{ connected ->
            Log.d(TAG, "registerConectivityCallback:$connected ")

            CoroutineScope(Dispatchers.Main).launch{
                banner.isVisible=!connected
            }
        })

    }

    class NetworkCallback(val notifyConnectedState:(connected:Boolean)->Unit): ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            notifyConnectedState(true)
            //I think I need to find the connected SSID in here.
        }

        override fun onLost(network: Network) {
            notifyConnectedState(false)
        }
    }

//    class ConnectivityCallback(val notifyConnectedState:(connected:Boolean)->Unit) : ConnectivityManager.NetworkCallback() {
//        override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
//            val connected = capabilities.hasCapability(NET_CAPABILITY_INTERNET)
//            notifyConnectedState(connected)
//        }
//
//        override fun onLost(network: Network) {
//            notifyConnectedState(false)
//        }
//
//    }



}