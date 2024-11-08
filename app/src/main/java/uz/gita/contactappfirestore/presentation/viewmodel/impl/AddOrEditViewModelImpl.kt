package uz.gita.contactappfirestore.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.contactappfirestore.data.model.ContactData
import uz.gita.contactappfirestore.data.repository.ContactRepository
import uz.gita.contactappfirestore.data.repository.impl.ContactRepositoryImpl
import uz.gita.contactappfirestore.presentation.viewmodel.AddOrEditViewModel

class AddOrEditViewModelImpl : AddOrEditViewModel, ViewModel() {
    private val repository: ContactRepository = ContactRepositoryImpl.getRepository()

    override val loadingLiveData = MutableLiveData<Boolean>()
    override val onFailLiveData = MutableLiveData<Unit>()
    override val onSuccessLiveData = MutableLiveData<Unit>()

    private val onCompleteListener: (isSuccessFul: Boolean) -> Unit = { isSuccessFul ->
        if (isSuccessFul) onSuccessLiveData.value = Unit
        else onFailLiveData.value = Unit

        loadingLiveData.value = false

    }



    override fun btnAddClicked(contactData: ContactData) {
        loadingLiveData.value = true
        repository.addContact(contactData, onCompleteListener)


    }

    override fun btnUpdateClicked(contactData: ContactData) {
        loadingLiveData.value = true

        repository.updateContact(contactData, onCompleteListener)


    }


}