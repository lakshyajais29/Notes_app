package com.example.notes_app.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    val noteTitle : String ="",
    val noteDesc : String =""
):Parcelable
