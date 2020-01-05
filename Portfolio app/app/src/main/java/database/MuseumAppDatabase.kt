package database

import DAO.ArtworkDao
import DAO.CommentDao
import DAO.UserDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.museumapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Artwork
import model.Comment
import model.User
import toByteArray

@Database(
    entities = [
        User::class,
        Artwork::class,
        Comment::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MuseumAppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun artworkDao(): ArtworkDao
    abstract fun commentDao(): CommentDao

    companion object {
        private const val DATABASE_NAME = "ROOM_DATABASE"

        @Volatile
        private var databaseInstance: MuseumAppDatabase? = null

        fun getDatabase(context: Context): MuseumAppDatabase? {
            if (databaseInstance == null) {
                synchronized(MuseumAppDatabase::class.java) {
                    if (databaseInstance == null) {
                        databaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            MuseumAppDatabase::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    databaseInstance?.let { database ->
                                        CoroutineScope(Dispatchers.IO).launch {
                                            database.artworkDao().insertArtworks(listOf(
                                                Artwork(title = "The Milkmaid", creator = "Johannes Vermeer", imageData = toByteArray(context.getDrawable(
                                                    R.drawable.arrow_right_black)!!)),
                                                Artwork(title = "The Starry Night", creator = "Van Gogh", imageData = toByteArray(context.getDrawable(
                                                    R.drawable.ic_launcher_background)!!)),
                                                Artwork(title = "The Night Watch", creator = "Rembrandt van Rijn", imageData = toByteArray(context.getDrawable(
                                                    R.drawable.ic_launcher_foreground)!!))
                                            ))
                                        }
                                    }
                                }
                            })
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return databaseInstance
        }
    }

}
