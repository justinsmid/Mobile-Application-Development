package com.example.assignment42rockpaperscissors.database

import androidx.room.*
import com.example.assignment42rockpaperscissors.model.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM game ORDER BY date DESC")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Query("DELETE FROM game")
    suspend fun deleteAllGames()
}