package database.repositories

import DAO.UserDao
import android.content.Context
import database.MuseumAppDatabase
import model.User

class UserRepository(context: Context) {

    private var userDao: UserDao

    init {
        val database = MuseumAppDatabase.getDatabase(context)
        userDao = database!!.userDao()
    }

    fun getUsers(): List<User> {
        return userDao.getAllUsers()
    }

    fun insertUser(reminder: User) {
        userDao.insertUser(reminder)
    }

    fun deleteUser(reminder: User) {
        userDao.deleteUser(reminder)
    }

    fun updateUser(reminder: User) {
        userDao.updateUser(reminder)
    }

}
