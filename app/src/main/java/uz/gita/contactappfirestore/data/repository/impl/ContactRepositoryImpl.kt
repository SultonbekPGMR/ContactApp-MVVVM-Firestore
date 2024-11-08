package uz.gita.contactappfirestore.data.repository.impl

import androidx.lifecycle.MutableLiveData
import uz.gita.contactappfirestore.data.model.ContactData
import uz.gita.contactappfirestore.data.repository.ContactRepository
import uz.gita.contactappfirestore.data.source.remote.FireStoreDb
import uz.gita.contactappfirestore.data.source.remote.impl.FireStoreDbImpl

class ContactRepositoryImpl private constructor() : ContactRepository {

    companion object {
        private lateinit var repoository: ContactRepository

        fun init() {
            repoository = ContactRepositoryImpl()
        }

        fun getRepository(): ContactRepository = repoository

    }

    private val fireStoreDb: FireStoreDb = FireStoreDbImpl()

    override val contactsLiveData = MutableLiveData<List<ContactData>>()



    init {
        fireStoreDb.contactsLiveData.observeForever {
            contactsLiveData.value = it
        }
    }

    override fun getAllContacts() = fireStoreDb.getAllContacts()

    override fun deleteContact(id: String) = fireStoreDb.deleteContact(id)



    override fun addContact(contactData: ContactData,onComplete:((Boolean)->Unit)) {
        fireStoreDb.addContact(contactData.name, contactData.phoneNumber, onComplete)
    }

    override fun updateContact(contactData: ContactData, onComplete:((Boolean)->Unit)) {
        fireStoreDb.updateContact(contactData)
    }

}