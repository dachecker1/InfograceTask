package com.vk.dachecker.infogracetask.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.vk.dachecker.infogracetask.SidePanelItemFactory
import com.vk.dachecker.infogracetask.data.ListItemRepositoryImpl
import com.vk.dachecker.infogracetask.domain.*
import com.vk.dachecker.infogracetask.domain.usecase.*
import kotlinx.coroutines.*

class SidePanelViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ListItemRepositoryImpl(application)
    private val editItemUseCase = EditItemUseCase(repository)
    private val getItemUseCase = GetItemUseCase(repository)
    private val getListItemUseCase = GetListItemUseCase(repository)
    private val addItemUseCase = AddItemUseCase(repository)
//    private val getInfoUseCase = GetInfoUseCase(repository)

    val itemList = getListItemUseCase.getItemList()

     val item = SidePanelItemFactory(application) //доступ к производству элементов
    private var counter = MutableLiveData<Int>(0) //счетчик общего переключателя

    private val _switcherManager =
        MutableLiveData<Boolean>() //главный переключатель, на него подписаны все переключатели элементов
    val switcherManager: LiveData<Boolean>
        get() = _switcherManager

    private val _count =
        MutableLiveData<Int>(0)  //счетчик, считает количество активированных переключателей
    val count: LiveData<Int>
        get() = _count

    private val _invisibleElements =
        MutableLiveData<Int>() //считает количество элементов, которые не влезли в экран
    val invisibleElements: LiveData<Int>
        get() = _invisibleElements


    //метод сохранения изменений в элементе, запись изменений в БД
    fun editItem(item: SidePanelItem, change: ElementChange) {
        when(change) {
            ElementChange.IsDetailOpen -> {
                val temp = item.copy(isDetailOpen = !item.isDetailOpen)
                viewModelScope.launch {
                    editItemUseCase.editItem(temp)
                }
            }
            ElementChange.IsActive -> {
                val temp = item.copy(isActive =  !item.isActive)
                viewModelScope.launch {
                    editItemUseCase.editItem(temp)
                }
            }
            ElementChange.IsSwitcherActive -> {
                val temp = item.copy(switcher = !item.switcher!!) // значение может быть null
                viewModelScope.launch {
                    editItemUseCase.editItem(temp)
                }
            }
        }
    }

    sealed class ElementChange {
        object IsDetailOpen: ElementChange()
        object IsActive : ElementChange()
        object IsSwitcherActive : ElementChange()
    }

    //метод изменения статуса переключателей
    fun changeSwitcherStatus() {
        _switcherManager.value = _switcherManager.value != true
    }


    fun changeCount(i: Int) {
        _count.value = _count.value?.plus(i)
        if (_count.value == counter.value) {
            _switcherManager.value = true
        } else if (_count.value == 0) {
            _switcherManager.value = false
        }
    }

    fun amountVisibleElements(first: Int, last: Int, firstVisible: Int, lastVisible: Int) {
        _invisibleElements.value = counter.value?.minus((lastVisible - firstVisible))
    }

//    fun searchInfo(text : String) {
//       val resultSearch = getInfoUseCase.getInfo(text)
//        Log.d("Search", "Search is ${resultSearch.value}")
//    }


}
