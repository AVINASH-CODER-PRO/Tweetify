package com.example.tweetify.viewmodles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tweetify.repository.ImageRepository
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
): ViewModel() {

    private val _imageUrls = MutableStateFlow<Map<String,String?>>(emptyMap())
    val imageUrls : StateFlow<Map<String,String?>> get() = _imageUrls.asStateFlow()

    fun fetchImage(category: String){
        val myRef = firebaseDatabase.getReference(category)
        myRef.get().addOnSuccessListener {
            val url = it.getValue(String::class.java)
            _imageUrls.update { currentUrls ->
                currentUrls.toMutableMap().apply {
                    this[category] = url
                }
            }
        }.addOnFailureListener{

        }
    }

    fun fetchAllImages(categories: List<String>) {
        categories.forEach { category ->
            fetchImage(category)
        }
    }
}