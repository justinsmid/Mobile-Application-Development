package com.example.reminderappcontinued.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.localstorageapp.Reminder
import com.example.reminderappcontinued.ReminderDao
import com.example.reminderappcontinued.databases.ReminderRoomDatabase

class ReminderRepository(context: Context) {
    private var reminderDao: ReminderDao

    init {
        val reminderRoomDatabase = ReminderRoomDatabase.getDatabase(context)
        reminderDao = reminderRoomDatabase!!.reminderDao()
    }

    fun getAllReminders(): LiveData<List<Reminder>> {
        return reminderDao.getAllReminders()
    }

    suspend fun insertReminder(reminder: Reminder) {
        reminderDao.insertReminder(reminder)
    }

    suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteReminder(reminder)
    }

    suspend fun updateReminder(reminder: Reminder) {
        reminderDao.updateReminder(reminder)
    }

}