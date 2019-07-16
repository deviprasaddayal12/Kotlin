package com.myprojects.testapp.baseclasses

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener{

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

        setUpToolbar()

        initialiseViews()
        initialiseListeners()
        setDataToViews()

        setUpViewPager()
        setUpRecycler()
    }

    open fun setUpToolbar() {}

    open fun initialiseViews() {}

    open fun initialiseListeners() {}

    open fun setDataToViews() {}

    open fun setUpViewPager(){}

    open fun setUpRecycler(){}

    override fun onClick(v: View?) {
        // to be implemented in extending classes
    }

    fun getAppString(resourceId: Int) : String {
        return resources.getString(resourceId)
    }

    fun hideSoftKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }

    fun showSoftKeyBoard(editText: EditText) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInputFromWindow(editText.applicationWindowToken, InputMethodManager.SHOW_FORCED, 0)
    }
}