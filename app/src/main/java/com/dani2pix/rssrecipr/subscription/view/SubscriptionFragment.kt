package com.dani2pix.rssrecipr.subscription.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dani2pix.rssrecipr.R


/**
 * A simple [Fragment] subclass.
 */
class SubscriptionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscription, container, false)
    }

}// Required empty public constructor
