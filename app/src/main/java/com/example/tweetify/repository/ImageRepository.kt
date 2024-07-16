package com.example.tweetify.repository

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ImageRepository @Inject constructor(private val firebaseDatabase: FirebaseDatabase) {
    // Write a message to the database
    private val database = FirebaseDatabase.getInstance().getReference("Motivation")

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl : StateFlow<String?> get() = _imageUrl

    fun fetchImage(category: String){
        database.child(category).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.child("url").getValue<String>()
                _imageUrl.value = value
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }


}