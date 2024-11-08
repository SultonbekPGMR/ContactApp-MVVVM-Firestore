package uz.gita.contactappfirestore.presentation.screen.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.contactappfirestore.R
import uz.gita.contactappfirestore.databinding.ScreenHomeBinding
import uz.gita.contactappfirestore.presentation.adapter.RvAdapter
import uz.gita.contactappfirestore.presentation.viewmodel.HomeViewModel
import uz.gita.contactappfirestore.presentation.viewmodel.impl.HomeViewModelImpl
import uz.gita.contactappfirestore.utils.Event


class HomeScreen : Fragment(R.layout.screen_home) {

    private val binding: ScreenHomeBinding by viewBinding(ScreenHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()

    private val rvAdapter: RvAdapter by lazy { RvAdapter() }
    private val handler = Handler(Looper.getMainLooper())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rv.adapter = rvAdapter

        setListeners()

        observeViewModel()

    }

    private var isClickable = true
    private val showDialogObserver = Observer<Event<Unit>> {
        if (it.getContentIfNotHandled() == null) return@Observer
        if (isClickable) {
            isClickable = false
            handler.postDelayed({
                isClickable = true
            }, 500)

            findNavController().navigate(HomeScreenDirections.actionHomeScreenToAddOrEditScreen())
        }
    }

    private fun observeViewModel() {
        viewModel.contactsLiveData.observeForever {
            rvAdapter.submitList(it)
        }

        viewModel.editContactLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()
                ?.let { findNavController().navigate(HomeScreenDirections.actionHomeScreenToAddOrEditScreen(it)) }
        }

        viewModel.showDialogLiveData.observe(viewLifecycleOwner, showDialogObserver)

    }

    private fun setListeners() {
        rvAdapter.setFunCall {

        }

        rvAdapter.setFunDelete { viewModel.btnDeleteClicked(it) }

        rvAdapter.setFunEdit { viewModel.btnEditClicked(it) }

        binding.btnAdd.setOnClickListener {
            viewModel.btnAddClicked()
        }

    }


}