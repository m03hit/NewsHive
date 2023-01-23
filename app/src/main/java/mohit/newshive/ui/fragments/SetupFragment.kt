package mohit.newshive.ui.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import mohit.newshive.R
import mohit.newshive.databinding.FragmentSetupBinding
import mohit.newshive.model.NewsSource
import mohit.newshive.utils.Constants
import mohit.newshive.viewmodel.NewsViewModel


@AndroidEntryPoint
class SetupFragment : Fragment() {

    private var _binding: FragmentSetupBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by viewModels()

    private var selectedCountryIsoCodes = ArrayList<String>()
    private var filteredListOfSourceNames = arrayListOf<String>()
    private lateinit var newsSource:NewsSource
    private val arrayListNewsSources = arrayListOf<String>()
    private lateinit var newsSourcesArray: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference =
            this.activity?.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)
        if((sharedPreference?.contains("countryIsoCodes") == true && (sharedPreference?.contains("newsSources") == true)))
            NavHostFragment.findNavController(this).navigate(R.id.action_setupFragment_to_topNewsFragment)

        getNewsSources()
        binding.selectNewsSourcesButtonSetupFragment.setOnClickListener {
            filterSources()
            // loading here until getNewsSources isn't completed
            if(filteredListOfSourceNames.isEmpty())
                Snackbar.make(view,"Please Select country First",Snackbar.LENGTH_LONG).show()
            else
                alertDialogForNewsSources()
        }
        binding.selectCountryCodesButtonSetupFragment.setOnClickListener {
            alertDialogForCountrySelection()
        }
        binding.savePreferencesSetupFragment.setOnClickListener {
            val sharedPreference = this.activity?.getSharedPreferences(Constants.PREFERENCES_NAME, MODE_PRIVATE)
            if((sharedPreference?.contains("countryIsoCodes") == true && (sharedPreference?.contains("countryIsoCodes") == true)))
                Navigation.findNavController(view).navigate(R.id.topNewsFragment)
            else
            {
                val builder =
                    AlertDialog.Builder(requireActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
                builder.setTitle("Please Select your preferences before proceeding forward")
                builder.setNegativeButton("Cancel", null)
                builder.setCancelable(false)

                builder.create().show()
            }
        }
    }


    private fun getNewsSources() {
        // up niche

        newsViewModel.newsSources.observe(viewLifecycleOwner) {
            arrayListNewsSources.clear()
            for (i in it.sources) {
                newsSource=it
                arrayListNewsSources.add(i.name)
            }
            Log.d(TAG, "getNewsSources: $it")
        }
        newsViewModel.getNewsSources()
    }

    private fun alertDialogForCountrySelection() {
        val arrayCountryName: Array<String> = resources.getStringArray(R.array.country_name_array)
        val arrayCountryIsoCode: Array<String> =
            resources.getStringArray(R.array.country_iso_code_array)
        val selectedList = ArrayList<Int>()
        val builder =
            AlertDialog.Builder(requireActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
        builder.setTitle("Choose Countries ")
        builder.setMultiChoiceItems(
            arrayCountryName, null
        ) { _, which, isChecked ->
            if (isChecked) {
                selectedList.add(which)
            } else if (selectedList.contains(which)) {
                selectedList.remove(Integer.valueOf(which))
            }
        }
        builder.setNegativeButton("Cancel", null)
        builder.setPositiveButton("Save") { _, _ ->
            selectedCountryIsoCodes.clear()
            for (j in selectedList.indices) {
                selectedCountryIsoCodes.add(arrayCountryIsoCode[selectedList[j]])
            }
            val gson = Gson()
            val textList: List<String> = ArrayList<String>(selectedCountryIsoCodes)
            val jsonText: String = gson.toJson(textList)

            val sharedPreference = this.activity?.getSharedPreferences(Constants.PREFERENCES_NAME, MODE_PRIVATE)

            val edit = sharedPreference?.edit()
            if (edit != null) {
                edit.putString("countryIsoCodes", jsonText)
                edit.apply()
            }
            Log.d(TAG, "alertDialogForCountrySelection: $selectedCountryIsoCodes")
        }
        builder.show()
    }


    private fun filterSources(){
        if(selectedCountryIsoCodes.isEmpty())
            Log.d(TAG, "filterSources: emptyArray")
        else
        {   filteredListOfSourceNames.clear()
            newsSource.sources.forEach { x->
                if(selectedCountryIsoCodes.contains(x.country))
                    filteredListOfSourceNames.add(x.id)
            }
        }
    }

    private fun alertDialogForNewsSources() {
        newsSourcesArray=filteredListOfSourceNames.toTypedArray()
        val selectedList = ArrayList<Int>()
        val builder =
            AlertDialog.Builder(requireActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
        builder.setTitle("This is list choice dialog box")
        builder.setMultiChoiceItems(
            newsSourcesArray, null
        ) { _, which, isChecked ->
            if (isChecked) {
                selectedList.add(which)
            } else if (selectedList.contains(which)) {
                selectedList.remove(Integer.valueOf(which))
            }
        }

        builder.setPositiveButton("Save") { _, _ ->
            val selectedNewsSources = ArrayList<String>()

            for (j in selectedList.indices) {
                selectedNewsSources.add(newsSourcesArray[selectedList[j]])
            }
            val gson = Gson()
            val textList: List<String> = ArrayList<String>(selectedNewsSources)
            val jsonText: String = gson.toJson(textList)

            val sharedPreference = this.activity?.getSharedPreferences(Constants.PREFERENCES_NAME, MODE_PRIVATE)

            val edit = sharedPreference?.edit()
            if (edit != null) {
                edit.putString("newsSources", jsonText)
                edit.apply()
            }
            Log.d(TAG, "alertDialogForNewsSources: $selectedNewsSources")
        }

        builder.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}