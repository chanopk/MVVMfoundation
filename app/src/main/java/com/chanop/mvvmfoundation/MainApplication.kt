package com.chanop.mvvmfoundation

import android.app.Application
import com.chanop.mvvmfoundation.repositiry.RetrofitClientCalling.client
import com.chanop.mvvmfoundation.repositiry.service.ApiHelper
import com.finnomena.project.candidate.repositiry.service.ApiInterface

class MainApplication : Application() {
    companion object {

        private var mApiHelper: ApiHelper? = null
        fun getApiHelperInstance(): ApiHelper {
            if (mApiHelper == null) {
                mApiHelper = ApiHelper(client!!.create(ApiInterface::class.java))
            }
            return mApiHelper!! // TODO refactor for dynamic
        }
    }
}
