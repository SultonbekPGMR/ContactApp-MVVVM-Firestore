package uz.gita.contactappfirestore.app

import android.app.Application
import uz.gita.contactappfirestore.data.repository.impl.ContactRepositoryImpl

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        ContactRepositoryImpl.init()

    }

}