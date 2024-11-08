package uz.gita.contactappfirestore.data.repository

import androidx.lifecycle.LiveData
import uz.gita.contactappfirestore.data.model.ContactData

interface ContactRepository {

    val contactsLiveData: LiveData<List<ContactData>>



    fun getAllContacts()
    fun deleteContact(id: String)
    fun addContact(contactData: ContactData,onComplete:((Boolean)->Unit))
    fun updateContact(contactData: ContactData,onComplete:((Boolean)->Unit))


}