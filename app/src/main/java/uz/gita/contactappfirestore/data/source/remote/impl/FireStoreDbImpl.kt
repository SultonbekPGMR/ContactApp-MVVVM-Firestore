package uz.gita.contactappfirestore.data.source.remote.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import uz.gita.contactappfirestore.data.model.ContactData
import uz.gita.contactappfirestore.data.source.remote.FireStoreDb

class FireStoreDbImpl : FireStoreDb {

    private val firestore = Firebase.firestore

    override val contactsLiveData = MutableLiveData<ArrayList<ContactData>>()

    init {
        firestore.collection(CONTACT_DB).addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot == null || snapshot.isEmpty) {
                Log.d(TAG, "Current data: null")

                contactsLiveData.value = arrayListOf()

                return@addSnapshotListener
            }

            val newList = ArrayList<ContactData>()
            snapshot.documents.forEach {
                newList.add(
                    ContactData(
                        it.id,
                        it.getString(NAME) ?: "",
                        it.getString(PHONE_NUMBER) ?: ""
                    )
                )
            }

            contactsLiveData.value = newList


        }

    }


    override fun getAllContacts() {
        firestore.collection(CONTACT_DB)
            .get()
            .addOnSuccessListener { snapshot ->
                val newList = ArrayList<ContactData>()
                snapshot.documents.forEach {
                    newList.add(
                        ContactData(
                            it.id,
                            it.getString(NAME) ?: "",
                            it.getString(PHONE_NUMBER) ?: ""
                        )
                    )
                }

                contactsLiveData.value = newList

            }
            .addOnFailureListener {
                Log.d(TAG, "getAllContacts: ${it.message}")
            }

    }

    override fun deleteContact(id: String) {
        firestore.collection(CONTACT_DB).document(id).delete()

    }

    override fun addContact(name: String, phoneNumber: String, onComplete: ((Boolean) -> Unit)) {
        val map = mapOf(NAME to name, PHONE_NUMBER to phoneNumber)
        firestore.collection(CONTACT_DB)
            .add(map)
            .addOnFailureListener {
                Log.d(TAG, "addContact: ${it.message}")
            }
            .addOnCompleteListener{
                onComplete.invoke(it.isSuccessful)
            }

    }

    override fun updateContact(contactData: ContactData) {
        val map = mapOf(NAME to contactData.name, PHONE_NUMBER to contactData.phoneNumber)
        firestore.collection(CONTACT_DB).document(contactData.id).set(map)

    }

    companion object {
        const val CONTACT_DB = "contactDb"
        const val NAME = "name"
        const val PHONE_NUMBER = "phoneNumber"
        const val ID = "id"
        const val TAG = "MYTAGTAGTAG"

    }


}