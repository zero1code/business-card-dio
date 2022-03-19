package com.dio.businesscard.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dio.businesscard.App
import com.dio.businesscard.R
import com.dio.businesscard.data.BusinessCard
import com.dio.businesscard.databinding.ActivityAddBusinessCardBinding
import com.google.android.material.snackbar.Snackbar

class AddBusinessCardActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddBusinessCardBinding.inflate(layoutInflater) }
    private val addCardViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        insertListeners()
    }

    private fun insertListeners() {
        binding.btClose.setOnClickListener {
            finish()
        }

        binding.btConfirm.setOnClickListener {
            val businessCard = BusinessCard(
                name = binding.tileName.text.toString().trim(),
                companyName = binding.tileCompanyName.text.toString().trim(),
                site = binding.tileSite.text.toString().trim(),
                email = binding.tileEmail.text.toString().trim(),
                phone = binding.tilePhone.text.toString().trim(),
                cardBackground = binding.tileCardColor.text.toString().trim()
            )
            addCardViewModel.insert(businessCard)
            Snackbar.make(
                binding.root,
                getString(R.string.label_card_success),
                Snackbar.LENGTH_LONG
            ).show()
            finish()
        }
    }
}