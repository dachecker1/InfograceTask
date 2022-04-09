package com.vk.dachecker.infogracetask.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vk.dachecker.infogracetask.data.entity.ItemDbModel
import com.vk.dachecker.infogracetask.domain.SidePanelItem
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.NotNull

@Dao
interface ItemListDao {

    @Query("SELECT * FROM item")
    fun getItemsList() : List<ItemDbModel>

    @Query("SELECT * FROM item")
    fun getItemsListLiveData() : LiveData<List<ItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(itemDbModel: ItemDbModel)

    @Query("DELETE FROM item WHERE id=:itemId")
    suspend fun deleteItem(itemId : Int)

    @Query("SELECT * FROM item WHERE id=:itemId LIMIT 1")
    suspend fun getItem(itemId : Int) : ItemDbModel
}