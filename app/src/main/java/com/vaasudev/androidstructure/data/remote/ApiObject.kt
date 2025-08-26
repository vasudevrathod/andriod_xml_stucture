package com.vaasudev.androidstructure.data.remote

class ApiObject {

    object ApiHeaderKey {
        const val LANG = "lang"
        const val X_API_KEY = "x-api-key"
        const val KEY = "key"
    }

    object ApiHeaderValue {
        const val LANG_EN = "en"
        const val LANG_AR = "ar"
        const val KEY_VALUE = "SG*#2025@Gate#"
    }

    object MiddlePoint {
        const val AUTH = "customer/Auth/"
    }

    object EndPoint {
        const val INIT =
            MiddlePoint.AUTH.plus("init_new")
    }

    object Param {
        const val ID = "id"
    }

    object DefaultParamValue {
        const val DEVICE_TYPE_ANDROID = "android"
        const val DEVICE_TYPE_INT = "1"
    }
}