package com.example.sketchhub.fragments

import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sketchhub.R

class UserAvatar : Fragment() {

    interface UserAvatarListener {
        fun onClickProfile()
    }

    private lateinit var userAvatarListener : UserAvatarListener

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        return inflater?.inflate(R.layout.user_avatar, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        userAvatarListener = context as UserAvatarListener
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.findViewById<View>(R.id.userAvatar)?.setOnClickListener {
            userAvatarListener.onClickProfile()
        }
    }

}