package com.precticle.androidarchitecture.view.view.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.precticle.androidarchitecture.BuildConfig
import com.precticle.androidarchitecture.R
import com.precticle.androidarchitecture.data.api.ApiHelper
import com.precticle.androidarchitecture.data.api.ServiceClass
import com.precticle.androidarchitecture.paytm.CartItem
import com.precticle.androidarchitecture.paytm.User
import com.precticle.androidarchitecture.paytm.networking.ApiClient
import com.precticle.androidarchitecture.paytm.networking.ApiService
import com.precticle.androidarchitecture.paytm.networking.model.*
import com.precticle.androidarchitecture.view.viewmodel.PaytmViewModel
import com.precticle.test.util.SharedPrefrence
import kotlinx.android.synthetic.main.activity_pay_tm.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PaytmActivity : BaseActivity() {

    lateinit var paytmViewModel: PaytmViewModel
    lateinit var appConfig: AppConfig
    lateinit var user: User
    private var mApi: ApiClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_tm)
        // setToolbar()
        enableToolbarUpNavigation()
        supportActionBar!!.setTitle(getString(R.string.title_preparing_order))
        changeStatusBarColor()


        setupViewModel()
        prepareOrder()
        appConfig =
            AppConfig()
        appConfig.id = 0

        user = User()
        user.id = 2765
        user.name = "mala"
        user.email = "rupareliya.mala@gmail.com"

    }
    fun getApi(): ApiClient? {
        if (mApi == null) {
            mApi = ApiService.getClient(this).create(ApiClient::class.java)
        }
        return mApi
    }
    fun login(){
        val request = LoginRequest()
        request.email = "mala.rupareliya@gmail.com"
        request.password = "Mom@7600303295"

        getApi().login(request).enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                loader.visibility = View.INVISIBLE
                if (!response.isSuccessful) {
                    handleError(response.errorBody())
                    return
                }

                SharedPrefrence.saveAuthToken(response.body()!!.token,this@PaytmActivity)
                
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                loader.visibility = View.INVISIBLE
                handleError(t)
            }
        })
    }

    private fun setUpObservers(request: PrepareOrderRequest) {

        getApi().prepareOrder(request).enqueue(object : Callback<PrepareOrderResponse?> {
            override fun onResponse(
                call: Call<PrepareOrderResponse?>,
                response: Response<PrepareOrderResponse?>
            ) {
                if (!response.isSuccessful) {
                    handleUnknownError()
                    //showOrderStatus(false)
                    return
                }
                getChecksum(response.body())
            }

            override fun onFailure(
                call: Call<PrepareOrderResponse?>,
                t: Throwable
            ) {
                handleError(t)
               // showOrderStatus(false)
            }
        })
    }

    private fun setupViewModel() {
        paytmViewModel = ViewModelProviders.of(
            this,
            PaytmViewModel.ViewModelFactory(ApiHelper(ServiceClass.apiService))
        ).get(PaytmViewModel::class.java)

    }

    /**
     * STEP 1: Sending all the cart items to server and receiving the
     * unique order id that needs to be sent to PayTM
     */
    fun prepareOrder() {

        //setStatus(R.string.msg_preparing_order)
        var cartItems: MutableList<CartItem> = ArrayList<CartItem>()
        var cartItemsFirst = CartItem()
        var product =
            Product()
        product.id = 33
        cartItemsFirst.product = product
        cartItemsFirst.quantity = 1


        var cartItemsSecond = CartItem()
        var productobj =
            Product()
        productobj.id = 34
        cartItemsSecond.product = productobj
        cartItemsSecond.quantity = 1


        cartItems.add(cartItemsFirst)
        cartItems.add(cartItemsSecond)

        val orderItems: MutableList<OrderItem> = ArrayList<OrderItem>()
        for ((_, product1, quantity) in cartItems) {
            val orderItem =
                OrderItem()
            orderItem.productId = product1!!.id
            orderItem.quantity = quantity
            orderItems.add(orderItem)
        }

        val request = PrepareOrderRequest()
        request.orderItems = orderItems

        setUpObservers(request)

    }

    /**
     * STEP 2:
     * Sending the params to server to generate the Checksum
     * that needs to be sent to PayTM
     */
    fun getChecksum(response: PrepareOrderResponse?) {
        setStatus(R.string.msg_fetching_checksum)

        if (appConfig == null) {
            // Timber.e("App config is null! Can't place the order. This usually shouldn\'t happen")
            // navigating user to login screen
            //launchLogin(this@PayTMActivity)
            finish()
            return
        }
        val paramMap: Map<String, String?> = preparePayTmParams(response!!)
        //Timber.d("PayTm Params: %s", paramMap)
        /* getApi().getCheckSum(paramMap).enqueue(object : Callback<ChecksumResponse> {
             override fun onResponse(
                 call: Call<ChecksumResponse>,
                 response: Response<ChecksumResponse>
             ) {
                 if (!response.isSuccessful()) {
                     Timber.e("Network call failed")
                     handleUnknownError()
                     showOrderStatus(false)
                     return
                 }
                 Timber.d("Checksum Received: " + response.body().checksum)

                 // Add the checksum to existing params list and send them to PayTM
                 paramMap["CHECKSUMHASH"] = response.body().checksum
                 placeOrder(paramMap)
             }

             override fun onFailure(
                 call: Call<ChecksumResponse>,
                 t: Throwable
             ) {
                 handleError(t)
                 showOrderStatus(false)
             }
         })*/
    }

    //CUST_ID -> CUSTOMER_2765
    //CHANNEL_ID -> WAP
    //ORDER_ID -> f1365b20-cb32-11ea-a29a-5d1b6d221296
    //CALLBACK_URL -> https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=f1365b20-cb32-11ea-a29a-5d1b6d221296
    //MID -> Androi78288874845632
    //INDUSTRY_TYPE_ID -> Retail
    //WEBSITE -> APPSTAGING


    //f1365b20-cb32-11ea-a29a-5d1b6d221296
    //850
    fun preparePayTmParams(response: PrepareOrderResponse): Map<String, String?> {
        val paramMap: MutableMap<String, String?> =
            HashMap()
        paramMap["CALLBACK_URL"] =
            java.lang.String.format(BuildConfig.PAYTM_CALLBACK_URL, response.orderGatewayId)
        paramMap["CHANNEL_ID"] = appConfig.channel
        paramMap["CUST_ID"] = "CUSTOMER_" + user.id
        paramMap["INDUSTRY_TYPE_ID"] = appConfig.industryType
        paramMap["MID"] = appConfig.merchantId
        paramMap["WEBSITE"] = appConfig.website
        paramMap["ORDER_ID"] = response.orderGatewayId
        paramMap["TXN_AMOUNT"] = response.amount
        return paramMap
    }

    // override fun handleUnknownError() {
    //  showErrorDialog(getString(R.string.msg_unknown))
    // }

    private fun setStatus(message: Int) {
        lbl_status.setText(message)
    }

}