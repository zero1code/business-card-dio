package com.dio.businesscard.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BusinessCard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val companyName: String,
    val email: String,
    val phone: String,
    val site: String,
    val cardBackground: String
)