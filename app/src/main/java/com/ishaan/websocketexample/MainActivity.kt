package com.ishaan.websocketexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ishaan.websocketexample.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), MessageListener {

    private lateinit var binding: ActivityMainBinding
    private val serverUrl = "ws://echo.websocket.org"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WebSocketManager.init(serverUrl, this)
        initListeners()
    }

    private fun initListeners() {
        binding.btnConnect.setOnClickListener {
            thread {
                kotlin.run {
                    WebSocketManager.connect()
                }
            }
        }

        binding.btnClientSend.setOnClickListener {
            if (WebSocketManager.sendMessage("Client send")) {
                addText("Send from the client\n")
            }
        }

        binding.btnConnectionClose.setOnClickListener {
            WebSocketManager.close()
        }
    }

    private fun addText(text: String?) {
        runOnUiThread {
            binding.etContent.text.append(text)
        }
    }

    override fun onConnectSuccess() {
        addText(" Connected successfully \n ")
    }

    override fun onConnectFailure() {
        addText(" Connection failed \n ")
    }

    override fun onClose() {
        addText(" Closed successfully \n ")
    }

    override fun onMessage(text: String) {
        addText(" Receive message: $text \n ")
    }

    override fun onDestroy() {
        WebSocketManager.close()
        super.onDestroy()
    }
}