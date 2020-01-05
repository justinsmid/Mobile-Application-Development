package com.example.gamebacklog.database.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.gamebacklog.database.GameRoomDatabase
import com.example.gamebacklog.database.dao.GameDao
import com.example.gamebacklog.model.Game

class GameRepository(context: Context) {
    private var gameDao: GameDao

    init {
        val gameRoomDatabase = GameRoomDatabase.getDatabase(context)
        gameDao = gameRoomDatabase!!.gameDao()
    }

    fun getAllGames(): LiveData<List<Game>> {
        return gameDao.getAllGames()
    }

    suspend fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }

    suspend fun deleteGame(game: Game) {
        gameDao.deleteGame(game)
    }

    suspend fun updateGame(game: Game) {
        gameDao.updateGame(game)
    }

}