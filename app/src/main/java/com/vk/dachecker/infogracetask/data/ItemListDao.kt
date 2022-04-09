package com.vk.dachecker.infogracetask.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vk.dachecker.infogracetask.domain.SidePanelItem
import org.jetbrains.annotations.NotNull

@Dao
interface ItemListDao {

    @Query("SELECT * FROM item")
    fun getItemsList() : LiveData<List<ItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(itemDbModel: ItemDbModel)
//
//    @Insert
//    suspend fun addListItem(list : List<SidePanelItem>)

    @Query("DELETE FROM item WHERE id=:itemId")
    suspend fun deleteItem(itemId : Int)

    @Query("SELECT * FROM item WHERE id=:itemId LIMIT 1")
    suspend fun getItem(itemId : Int) : ItemDbModel
}