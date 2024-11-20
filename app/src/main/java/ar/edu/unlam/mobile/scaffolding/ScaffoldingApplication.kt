package ar.edu.unlam.mobile.scaffolding

import android.app.Application
import com.google.android.gms.maps.MapsInitializer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ScaffoldingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicializa el SDK de Google Maps
        MapsInitializer.initialize(this)
    }
}
