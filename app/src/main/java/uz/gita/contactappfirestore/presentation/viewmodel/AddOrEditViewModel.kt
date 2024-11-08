package uz.gita.contactappfirestore.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.contactappfirestore.data.model.ContactData

interface AddOrEditViewModel {
    val loadingLiveData: LiveData<Boolean>
    val onSuccessLiveData: LiveData<Unit>
    val onFailLiveData: LiveData<Unit>

    fun btnAddClicked(contactData: ContactData)

    fun btnUpdateClicked(contactData: ContactData)

}