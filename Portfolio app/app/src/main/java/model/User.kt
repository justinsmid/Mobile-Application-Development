package model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import database.Converters
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "profile_image")
    var profileImageUri: String?,

    @ColumnInfo(name = "favoriteArtworks")
    @TypeConverters(Converters::class)
    var favoriteArtworks: ArrayList<Artwork>? = null
) : Parcelable