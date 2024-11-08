package uz.gita.contactappfirestore.data.model

import java.io.Serializable

data class ContactData(
    val id: String = "",
    var name: String,
    var phoneNumber: String,
):Serializable
