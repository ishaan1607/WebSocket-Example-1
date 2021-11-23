package com.ishaan.websocketexample

interface MessageListener {
    fun onConnectSuccess()
    fun onConnectFailure()
    fun onClose()
    fun onMessage(text: String)
}