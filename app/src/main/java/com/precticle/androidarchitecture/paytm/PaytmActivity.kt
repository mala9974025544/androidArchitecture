package com.precticle.androidarchitecture.paytm

import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.liveData
import com.precticle.androidarchitecture.R
import com.precticle.androidarchitecture.data.api.ServiceClass
import com.precticle.androidarchitecture.utils.Resource
import com.precticle.androidarchitecture.view.view.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_pay_tm.*
import kotlinx.coroutines.Dispatchers
import java.util.*

class PaytmActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_pay_tm)
        setToolbar()
        enableToolbarUpNavigation()
        supportActionBar!!.setTitle(getString(R.string.title_preparing_order))
        changeStatusBarColor()
        //init()
    }
    /**
     * STEP 1: Sending all the cart items to server and receiving the
     * unique order id that needs to be sent to PayTM
     */
    fun prepareOrder(){

        setStatus(R.string.msg_preparing_order)
        var cartItems: MutableList<CartItem> = ArrayList<CartItem>()
        var cartItemsFirst=CartItem()
        cartItemsFirst.product!!.id=33
        cartItemsFirst.quantity=1
        cartItemsFirst.product=null

        var cartItemsSecond=CartItem()
        cartItemsSecond.product!!.id=34
        cartItemsSecond.quantity=1
        cartItemsSecond.product=null

        cartItems.add(cartItemsFirst)
        cartItems.add(cartItemsSecond)

        val orderItems: MutableList<OrderItem> = ArrayList<OrderItem>()
        for ((_, product, quantity) in cartItems) {
            val orderItem = OrderItem()
            orderItem.productId = product!!.id
            orderItem.quantity = quantity
            orderItems.add(orderItem)
        }

        val request = PrepareOrderRequest()
        request.orderItems = orderItems

        



    }
    private fun setStatus(message: Int) {
        lbl_status.setText(message)
    }

}