package com.precticle.androidarchitecture.view.view.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.precticle.androidarchitecture.R
import com.precticle.androidarchitecture.data.api.ApiHelper
import com.precticle.androidarchitecture.data.api.ServiceClass
import com.precticle.androidarchitecture.data.model.User
import com.precticle.androidarchitecture.utils.BroadcastUtils
import com.precticle.androidarchitecture.utils.CoreConstants
import com.precticle.androidarchitecture.utils.Status
import com.precticle.androidarchitecture.view.adapter.MainAdapter
import com.precticle.androidarchitecture.view.view.fragments.SecondFragment
import com.precticle.androidarchitecture.view.viewmodel.MainViewModel
import com.precticle.test.util.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.dynamicelements.blueskyme.dialogs.ConfiemDialog

class MainActivity : BaseActivity(), ConfiemDialog.DialogToFragment {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var mBroadCastReceiver: MyBroadCastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //  AutoStartPermissionHelper.getInstance().getAutoStartPermission(this);
        registerBroadCast()
        setupViewModel()
        setupUI()
        setupObservers()
        Utils.getFireBaseToken(this)
        //navigateToFragment()
        // navigateToActivity();
         //navigateToDiaog()
        /*launch(Dispatchers.IO) {

            withContext(Dispatchers.Main){

            }
        }*/
    }

    private fun navigateToDiaog() {
        val confiemDialog = ConfiemDialog()
        val bundle = Bundle()
        bundle.putBoolean(CoreConstants.INTENT_EXTRA_STRING, true)
        confiemDialog.arguments = bundle
        confiemDialog.setListener(this@MainActivity)
        confiemDialog.show(
            supportFragmentManager,
            ConfiemDialog::class.java.simpleName
        )
    }

    private fun navigateToActivity() {

        var intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("key", "mala")
        startActivity(intent)
    }

    private fun navigateToFragment() {
        var fragment = SecondFragment.getInstance("mala");
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fmChild, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun registerBroadCast() {
        mBroadCastReceiver = MyBroadCastReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(BroadcastUtils.ACTION_SEND)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mBroadCastReceiver, intentFilter)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            MainViewModel.ViewModelFactory(ApiHelper(ServiceClass.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getUsers().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(users: List<User>) {
        adapter.apply {
            addUsers(users)
            notifyDataSetChanged()
        }
    }

    inner class MyBroadCastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            if (intent.action != null && intent.action.equals(
                    BroadcastUtils.ACTION_SEND,
                    true
                )
            ) {
                if (intent.hasExtra(BroadcastUtils.ACTION_SEND_STR)) {
                    val str: String =
                        intent.getStringExtra(BroadcastUtils.ACTION_SEND_STR)
                    Utils.showAlertDialogue(this@MainActivity, str!!)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadCastReceiver)
    }


    override fun dialogDismiss() {

    }

    override fun dialogSave(string: String) {
        Utils.showSnackBar(this, string, recyclerView)
    }
}

