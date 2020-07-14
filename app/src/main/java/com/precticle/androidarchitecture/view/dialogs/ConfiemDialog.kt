package no.dynamicelements.blueskyme.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.precticle.androidarchitecture.R
import com.precticle.androidarchitecture.utils.CoreConstants
import com.precticle.test.util.Utils
import kotlinx.android.synthetic.main.dialog_activity.view.*


class ConfiemDialog : DialogFragment() {

    private var negativeButton: String? = null
    private var positiveButton: String? = null
    private var title: String? = null
    private var mListner: DialogToFragment? = null
    private var dialogTitle:String = ""

    interface DialogToFragment {
        fun dialogDismiss()
        fun dialogSave(str:String)
    }

    fun setListener(mListener: DialogToFragment, title: String? = null, positiveButton: String? = null, negativeButton: String? = null
    ,dialogTitle:String="") {
        this.mListner = mListener
        this.title = title
        this.positiveButton = positiveButton
        this.negativeButton = negativeButton
//       this.dialogTitle = if(dialogTitle.isBlank()) getString(R.string.delete_activity) else dialogTitle

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window?.attributes?.windowAnimations = R.style.DialogAnimation
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mListner!!.dialogDismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_activity, null)
        builder.setView(view)

        val bundle = arguments

        if (bundle != null) {
           var boolean=arguments!!.getBoolean(CoreConstants.INTENT_EXTRA_STRING)
           // Utils.showAlertDialogue(this@ConfiemDialog.requireContext(),boolean.toString())

        }


        view.tvMessage!!.text = String.format(resources.getString(R.string.txt_delete_activity), "title")
        if (negativeButton != null) {
            view.btnDelete.text = positiveButton
        }
        if (negativeButton != null) {
            view.btnCancel.text = negativeButton
        }

        view.btnDelete.setOnClickListener {
            dismiss()
            mListner!!.dialogSave("Save")
        }
        view.btnCancel.setOnClickListener {
            dismiss()
        }

        return builder.create()
    }


}
