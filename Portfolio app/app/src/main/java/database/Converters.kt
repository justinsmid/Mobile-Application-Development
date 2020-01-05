package database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import model.Artwork

class Converters {

    @TypeConverter
    fun fromArtworkArrayList(artworks: ArrayList<Artwork>?) : String {
        if (artworks == null || artworks.isEmpty()) return ""
        return Gson().toJson(artworks, object : TypeToken<List<Artwork>>() {}.type)
    }

    @TypeConverter
    fun toArtworkArrayList(value: String?) : ArrayList<Artwork> {
        if (value == null || value.isEmpty()) return arrayListOf()
        return Gson().fromJson(value, object : TypeToken<List<Artwork>>() {}.type)
    }
}