package com.example.museumapp.Services

import android.content.Context
import database.repositories.UserRepository
import model.User

object UserService {

    var user : User? = null

    fun isLoggedIn(context: Context) : Boolean {
        return attemptGetUser(context) != null
    }

    fun attemptGetUser(context: Context) : User? = when {
        user != null -> user
        else -> getUserFromDb(context)
    }

    fun createUser(vararg data: String) : User {
        val user = User(
            id = null,
            email = data[0],
            username = data[1],
            profileImageUri = null
        )
        this.user = user

        return user
    }

    fun updateUser(context: Context, user: User) {
        this.user = user
        UserRepository(context).updateUser(user)
    }

    private fun getUserFromDb(context: Context) : User? {
        val users = UserRepository(context).getUsers()
        return when {
            users.isEmpty() -> null
            else -> {
                user = users.first()
                return user
            }
        }
    }
}