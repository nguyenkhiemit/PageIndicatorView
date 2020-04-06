package com.nguyen.indicatorview

import java.lang.Exception

class PagesLessException : Exception() {

    override fun getLocalizedMessage(): String {
        return "Pages must equal or larger than 2"
    }
}