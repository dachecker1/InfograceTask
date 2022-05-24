package com.vk.dachecker.infogracetask.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vk.dachecker.infogracetask.data.entity.ItemDbModel

@Database(entities = [ItemDbModel::class], version = 1, exportSchema = false)
abstract class ItemsDataBase : RoomDatabase() {

    abstract fun listDao(): ItemListDao

    companion object {

        private var INSTANCE: ItemsDataBase? = null
        private val LOCK = Any()
        private const val DB_NAME = "item.db"

        fun getInstance(application: Application): ItemsDataBase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    ItemsDataBase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}