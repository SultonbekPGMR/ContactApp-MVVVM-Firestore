package uz.gita.contactappfirestore.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.contactappfirestore.data.model.ContactData
import uz.gita.contactappfirestore.utils.Event

interface HomeViewModel {
    val contactsLiveData: LiveData<List<ContactData>>
    val editContactLiveData: LiveData<Event<ContactData>>
    val showDialogLiveData: LiveData<Event<Unit>>

    fun btnEditClicked(contactData: ContactData)

    fun btnDeleteClicked(contactData: ContactData)

    fun btnAddClicked()


}