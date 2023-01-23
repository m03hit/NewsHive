package mohit.newshive.ui.fragments

import android.R
import android.app.Dialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


class NewsSourceSelectDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
        val listItems = arrayOf("C", "C++", "JAVA", "PYTHON")
        val list = arrayListOf<String>()
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Choose NewsSources").
        setCancelable(false).setMultiChoiceItems(listItems, null,
            OnMultiChoiceClickListener { dialogInterface, i, b ->
                list.add(listItems[i])
            }).
        setNegativeButton("Cancel",null).
        setPositiveButton("Save",null)
        builder.create()
        val alertDialog = builder.create()
        alertDialog.show()
    }


}