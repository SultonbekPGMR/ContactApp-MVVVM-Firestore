package uz.gita.contactappfirestore.presentation.screen.addOrEdit

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.contactappfirestore.R
import uz.gita.contactappfirestore.data.model.ContactData
import uz.gita.contactappfirestore.databinding.ScreenAddOrEditBinding
import uz.gita.contactappfirestore.presentation.viewmodel.AddOrEditViewModel
import uz.gita.contactappfirestore.presentation.viewmodel.impl.AddOrEditViewModelImpl

class AddOrEditScreen : DialogFragment(R.layout.screen_add_or_edit) {

    private val binding: ScreenAddOrEditBinding by viewBinding(ScreenAddOrEditBinding::bind)

    private val viewModel: AddOrEditViewModel by viewModels<AddOrEditViewModelImpl>()

    private val args: AddOrEditScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        args.contactData?.let {
            binding.edtName.setText(it.name)
            binding.edtPhoneNumber.setText(it.phoneNumber)
            binding.btnSave.text = "Save"
            binding.txtTitle.text = "Update Contact"
        }

        setListeners()

//        observeViewModel()


    }


    private val loadingObserver = Observer<Boolean> {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE

        binding.btnCancel.isEnabled = !it
        binding.btnSave.isEnabled = !it

    }
    private val onFailObserver = Observer<Unit> {
        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
    }

    private val onSuccessObserver = Observer<Unit> {
        Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }


    private fun observeViewModel() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner, loadingObserver)

        viewModel.onFailLiveData.observe(viewLifecycleOwner, onFailObserver)

        viewModel.onSuccessLiveData.observe(viewLifecycleOwner, onSuccessObserver)


    }

    private fun setListeners() {
        binding.btnCancel.setOnClickListener { findNavController().popBackStack() }

        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val phoneNumber = binding.edtPhoneNumber.text.toString().trim()

            if (name.isEmpty()||phoneNumber.isEmpty()) {
                Toast.makeText(context, "Fields should not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (args.contactData == null) {
                viewModel.btnAddClicked(
                    ContactData(
                        name = name,
                        phoneNumber = phoneNumber
                    )
                )
            } else {
                viewModel.btnUpdateClicked(
                    ContactData(
                        id = args.contactData!!.id,
                        name = name,
                        phoneNumber = phoneNumber
                    )
                )
            }
            findNavController().popBackStack()

        }


    }


}