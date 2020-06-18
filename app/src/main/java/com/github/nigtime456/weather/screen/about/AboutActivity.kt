/*
 * Сreated by Igor Pokrovsky. 2020/6/13
 */

package com.github.nigtime456.weather.screen.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.core.text.HtmlCompat
import com.github.nigtime456.weather.R
import com.github.nigtime456.weather.screen.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, AboutActivity::class.java)
        }
    }

    //<editor-fold desc="lifecycle">

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        initViews()
    }

    //</editor-fold>

    //<editor-fold desc="initialization">
    private fun initViews() {
        initToolbar()
        initText()
    }

    private fun initText() {
        activity_about_text.movementMethod = LinkMovementMethod.getInstance()
        activity_about_text.text = HtmlCompat.fromHtml(
            getString(R.string.test),
            HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH
        )
    }

    private fun initToolbar() {
        activity_about_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    //</editor-fold>

}