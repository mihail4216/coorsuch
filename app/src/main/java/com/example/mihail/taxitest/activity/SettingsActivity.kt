package com.example.mihail.taxitest.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.mihail.taxitest.R
import com.example.mihail.taxitest.repository.MainRepository


class SettingsActivity : Activity() {

    private lateinit var backBtn: View
    private lateinit var homeTxt: EditText
    private lateinit var phoneTxt: EditText

    private lateinit var moneyView: View
    private lateinit var cardView: View

    private lateinit var cardStroke: View
    private lateinit var moneyStroke: View
    private lateinit var addCard: View


    private fun changeStroke(){
        if (MainRepository.instance.isPayCard){
            cardStroke.visibility = View.VISIBLE
            moneyStroke.visibility = View.GONE
        }else{
            cardStroke.visibility = View.GONE
            moneyStroke.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        backBtn = findViewById(R.id.back)
        homeTxt = findViewById(R.id.home)
        phoneTxt = findViewById(R.id.phone)

        moneyStroke = findViewById(R.id.met_hand)
        cardStroke = findViewById(R.id.met_card)
        cardView = findViewById(R.id.card)
        moneyView = findViewById(R.id.money)
        addCard = findViewById(R.id.add_card)

        cardView.setOnClickListener {
            MainRepository.instance.isPayCard = true
            changeStroke()
        }

        moneyView.setOnClickListener {
            MainRepository.instance.isPayCard = false
            changeStroke()
        }
        addCard.setOnClickListener {
            startActivity(Intent(this,CardActivity::class.java))
        }

        backBtn.setOnClickListener {
            finish()
        }

        homeTxt.text.append(MainRepository.instance.homeAddress)
        phoneTxt.text.append(MainRepository.instance.phone)

    }

    override fun onPause() {
        super.onPause()
        MainRepository.instance.homeAddress = homeTxt.text.toString()
        MainRepository.instance.phone = phoneTxt.text.toString()
    }

    override fun onResume() {
        super.onResume()
        changeStroke()
    }
}