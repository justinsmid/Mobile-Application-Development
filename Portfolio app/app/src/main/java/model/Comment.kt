package model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "comment"
)
data class Comment (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,

    @ColumnInfo(name = "value")
    val value: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "artwork_id")
    val artworkId: Long?
): Parcelable