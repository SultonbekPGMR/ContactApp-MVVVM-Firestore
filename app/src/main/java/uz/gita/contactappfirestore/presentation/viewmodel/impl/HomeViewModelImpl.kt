package uz.gita.contactappfirestore.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.contactappfirestore.data.model.ContactData
import uz.gita.contactappfirestore.data.repository.ContactRepository
import uz.gita.contactappfirestore.data.repository.impl.ContactRepositoryImpl
import uz.gita.contactappfirestore.presentation.viewmodel.HomeViewModel
import uz.gita.contactappfirestore.utils.Event

class HomeViewModelImpl : HomeViewModel, ViewModel() {
    private val repository: ContactRepository = ContactRepositoryImpl.getRepository()

    override val contactsLiveData = MutableLiveData<List<ContactData>>()
    override val editContactLiveData = MutableLiveData<Event<ContactData>>()
    override val showDialogLiveData = MutableLiveData<Event<Unit>>()

    init {
        repository.contactsLiveData.observeForever {
            contactsLiveData.value = it
        }

    }

    override fun btnEditClicked(contactData: ContactData) {
        editContactLiveData.value = Event(contactData)
    }

    override fun btnDeleteClicked(contactData: ContactData) {
        repository.deleteContact(contactData.id)
    }

    override fun btnAddClicked() {
        showDialogLiveData.value = Event(Unit)
    }

}