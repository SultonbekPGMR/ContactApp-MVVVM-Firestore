package uz.gita.contactappfirestore.data.source.remote

import androidx.lifecycle.LiveData
import uz.gita.contactappfirestore.data.model.ContactData

interface FireStoreDb {

    val contactsLiveData:LiveData<ArrayList<ContactData>>

    fun getAllContacts()
    fun deleteContact(id: String)
    fun addContact(name:String, phoneNumber:String, onComplete:((Boolean)->Unit))
    fun updateContact(contactData: ContactData)


}