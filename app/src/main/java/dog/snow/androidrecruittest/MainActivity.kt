package dog.snow.androidrecruittest

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import dog.snow.androidrecruittest.databinding.MainActivityBinding
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private val TAG = "MainActivity"

    private val connectionFlag by lazy {
        MutableLiveData<Boolean>(false)
    }



    lateinit var binding:MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding= DataBindingUtil.setContentView(this,R.layout.main_activity)
        binding.lifecycleOwner=this
        binding.connectionFlag=connectionFlag

        setSupportActionBar(findViewById(R.id.toolbar))

        registerConnectivityCallback()
    }

    private fun registerConnectivityCallback() {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerDefaultNetworkCallback(ConnectivityCallback{ connected ->
            Log.d(TAG, "registerConectivityCallback:$connected ")

            CoroutineScope(Dispatchers.Main).launch{
                connectionFlag.value=connected
            }
        })
    }
    class ConnectivityCallback(val notifyConnectedState:(connected:Boolean)->Unit) : ConnectivityManager.NetworkCallback() {
        override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
            val connected = capabilities.hasCapability(NET_CAPABILITY_INTERNET)
            notifyConnectedState(connected)
        }


        override fun onUnavailable() {
            notifyConnectedState(false)
        }

        override fun onLost(network: Network) {
            notifyConnectedState(false)
        }

    }


}