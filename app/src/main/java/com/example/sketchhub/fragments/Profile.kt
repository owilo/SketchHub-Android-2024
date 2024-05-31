package com.example.sketchhub.fragments

import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.sketchhub.PageChangeListener
import com.example.sketchhub.R

class Profile : Fragment() {

    interface ProfileListener {
        fun onPremiumUpgradeClick()
    }

    private lateinit var profileListener: ProfileListener

    private lateinit var changePasswordAlertDialog: AlertDialog
    private lateinit var invitationAlertDialog: AlertDialog

    private var pageChangeListener: PageChangeListener? = null

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        return inflater?.inflate(R.layout.profile, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        profileListener = context as ProfileListener
        pageChangeListener = context as PageChangeListener

        changePasswordAlertDialog = AlertDialog.Builder(context)
            .setTitle(getString(R.string.passwordChange))
            .setMessage(R.string.changePasswordEmailSent)
            .setPositiveButton(android.R.string.ok, null)
            .create()

        invitationAlertDialog = AlertDialog.Builder(context)
            .setTitle(getString(R.string.invitations))
            .setView(R.layout.invitation_dialog)
            .setCancelable(true)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok, null)
            .create()
    }

    override fun onDetach() {
        super.onDetach()

        pageChangeListener = null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pageChangeListener?.onPageChanged(getString(R.string.profile))

        view?.findViewById<Button>(R.id.passwordChangeButton)?.setOnClickListener {
            changePasswordAlertDialog.show()
        }

        view?.findViewById<Button>(R.id.premiumButton)?.setOnClickListener {
            profileListener.onPremiumUpgradeClick()
        }

        view?.findViewById<View>(R.id.notificationBadge)?.setOnClickListener {
            invitationAlertDialog.show()
        }
    }

}