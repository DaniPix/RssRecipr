package com.dani2pix.rssrecipr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.dani2pix.rssrecipr.dashboard.view.DashboardFragment
import com.dani2pix.rssrecipr.subscription.view.SubscriptionFragment

import kotlinx.android.synthetic.main.activity_rss_recipr.*


class RssReciprActivity : AppCompatActivity() {

    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rss_recipr)
        setSupportActionBar(toolbar)

        val name: String = intent.getStringExtra(EXTRA_FRAGMENT) ?: FRAGMENT_DASHBOARD
        val title: Int = intent.getIntExtra(EXTRA_TITLE_RES, R.string.title_dashboard)

        fragment = Fragment.instantiate(this, name)

        if (supportFragmentManager != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment, name)
                    .commit()
        }

        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbarTitle.setText(title)
    }

    companion object {

        private val EXTRA_FRAGMENT: String = "extraFragment"
        private val EXTRA_TITLE_RES: String = "extraTitleRes"
        val FRAGMENT_DASHBOARD: String = DashboardFragment::class.java.name
        val FRAGMENT_SUBSCRIPTIONS: String = SubscriptionFragment::class.java.name

        fun startActivity(context: Context, title: Int, name: String) {
            val intent: Intent = newIntent(context, title, name)
            context.startActivity(intent)
        }

        private fun newIntent(context: Context, title: Int, name: String): Intent {
            val intent = Intent(context, RssReciprActivity::class.java)
            intent.putExtra(EXTRA_FRAGMENT, name)
            intent.putExtra(EXTRA_TITLE_RES, title)
            return intent
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}
