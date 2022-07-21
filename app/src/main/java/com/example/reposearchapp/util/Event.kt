package com.example.reposearchapp.util

sealed class Event {
    data class Success(val message: String) : Event()
    data class Error(val message: String) : Event()
}