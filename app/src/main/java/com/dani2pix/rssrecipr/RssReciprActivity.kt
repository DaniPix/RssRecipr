package com.dani2pix.rssrecipr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.dani2pix.rssrecipr.dashboard.view.DashboardFragment
import com.dani2pix.rssrecipr.dashboard.view.GroupFragment

import kotlinx.android.synthetic.main.activity_rss_recipr.*


class RssReciprActivity : AppCompatActivity() {

    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rss_recipr)
        setSupportActionBar(toolbar)

        val name: String = intent.getStringExtra(EXTRA_FRAGMENT) ?: FRAGMENT_DASHBOARD
        val title: Int = intent.getIntExtra(EXTRA_TITLE_RES, R.string.title_dashboard)
        val articleId: String = intent.getStringExtra(EXTRA_ARGS) ?: ""

        fragment = Fragment.instantiate(this, name)
        val bundle = Bundle()
        bundle.putString(EXTRA_ARGS, articleId)
        fragment.arguments = bundle

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
        val EXTRA_ARGS: String = "extraArgs"
        val FRAGMENT_DASHBOARD: String = DashboardFragment::class.java.name
        val FRAGMENT_GROUP: String = GroupFragment::class.java.name

        fun startActivity(context: Context, title: Int, name: String) {
            val intent: Intent = newIntent(context, title, name)
            context.startActivity(intent)
        }

        fun startActivityWithArgs(context: Context, title: Int, name: String, args: String) {
            val intent: Intent = newIntent(context, title, name)
            intent.putExtra(EXTRA_ARGS, args)
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
