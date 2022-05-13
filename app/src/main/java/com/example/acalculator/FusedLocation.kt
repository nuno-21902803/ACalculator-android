package com.example.acalculator

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*

@SuppressLint("MissingPermission")
class FusedLocation private constructor(context: Context) :
    LocationCallback() {

    private val TAG = FusedLocation::class.java.simpleName

    // Intervalos de tempo em que a localização é verificada, 20 segundos
    private val TIME_BETWEEN_UPDATES = 20 * 1000L

    // Configurar a precisão e os tempos entre atualizações da localização
    private var locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = TIME_BETWEEN_UPDATES
    }

    // Este atributo será utilizado para acedermos à API da Fused Location
    @SuppressLint("VisibleForTests")
    private var client = FusedLocationProviderClient(context)

    init {
        // Instanciar o objeto que permite definir as configurações
        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()

        // Aplicar as configurações ao serviço de localização
        LocationServices.getSettingsClient(context)
            .checkLocationSettings(locationSettingsRequest)

        client.requestLocationUpdates(locationRequest, this, Looper.getMainLooper())
    }

    override fun onLocationResult(locationResult: LocationResult) {
        Log.i(TAG, locationResult.lastLocation.toString())
        notifyListener(locationResult)
    }

    companion object {
        // Se quisermos ter vários listeners isto tem de ser uma lista
        //private val listeners = mutableListOf<OnLocationChangedListener>()
        private var listeners: OnLocationChangedListener? = null
        private var instance: FusedLocation? = null

        fun registerListener(listener: OnLocationChangedListener) {
            // listeners.add(listener)
            this.listeners = listener
        }

        fun unregisterListener() {
            listeners = null
            // listeners.remove(listener)
        }

        // Se tivermos vários listeners, temos de os notificar com um forEach
        fun notifyListener(locationResult: LocationResult) {
            val location = locationResult.lastLocation
            listeners?.onLocationChanged(location.latitude,location.longitude)
        //listeners.forEach { it.onLocationChanged(location.latitude, location.longitude) }
        }

        // Só teremos uma instância em execução
        fun start(context: Context) {
            instance = if (instance == null) FusedLocation(context) else instance
        }
    }
}

