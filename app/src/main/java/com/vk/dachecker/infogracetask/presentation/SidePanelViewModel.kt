package com.vk.dachecker.infogracetask.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vk.dachecker.infogracetask.data.ListItemRepositoryImpl
import com.vk.dachecker.infogracetask.domain.SidePanelItem
import com.vk.dachecker.infogracetask.domain.usecase.EditItemUseCase
import com.vk.dachecker.infogracetask.domain.usecase.GetListItemUseCase
import kotlinx.coroutines.launch

class SidePanelViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ListItemRepositoryImpl(application)
    private val editItemUseCase = EditItemUseCase(repository)
    private val getListItemUseCase = GetListItemUseCase(repository)
    val itemList = getListItemUseCase.getItemList()

    private val _counter = MutableLiveData<Int>(0) //счетчик общего переключателя
    val counter: LiveData<Int>
        get() = _counter

    private val _switcherManager =
        MutableLiveData<Boolean>() //главный переключатель, на него подписаны все переключатели элементов
    val switcherManager: LiveData<Boolean>
        get() = _switcherManager

    private val _countSwitcher =
        MutableLiveData<Int>(0)  //счетчик, считает количество активированных переключателей
    val countSwitcher: LiveData<Int>
        get() = _countSwitcher

    private val _invisibleElements =
        MutableLiveData<Int>() //считает количество элементов, которые не влезли в экран
    val invisibleElements: LiveData<Int>
        get() = _invisibleElements

    //отфильтрованные элементы
    private val _filteredItems = MutableLiveData<List<SidePanelItem>>()
    val filtered: LiveData<List<SidePanelItem>>
        get() = _filteredItems


    //устанавливает количество невидимых элементов
    fun setCounter(i: Int) {
        _counter.value = i
    }

    //метод сохранения изменений в элементе, запись изменений в БД
    fun editItem(item: SidePanelItem, change: ElementChange) {
        when (change) {
            ElementChange.IsDetailOpen -> {
                val temp = item.copy(isDetailOpen = !item.isDetailOpen)
                viewModelScope.launch {
                    editItemUseCase.editItem(temp)
                }
            }
            ElementChange.IsActive -> {
                val temp = item.copy(isActive = !item.isActive)
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

    //метод изменения статуса переключателей
    fun changeSwitcherStatus() {
        _switcherManager.value = _switcherManager.value != true
    }

    fun changeCount(i: Int) {
        _countSwitcher.value = _countSwitcher.value?.plus(i)
        if (_countSwitcher.value == counter.value) {
            _switcherManager.value = true
        } else if (_countSwitcher.value == 0) {
            _switcherManager.value = false
        }
    }

    fun amountVisibleElements(first: Int, last: Int, firstVisible: Int, lastVisible: Int) {
        _invisibleElements.value = counter.value?.minus((lastVisible - firstVisible))
    }

    fun searchInfo(text: String) {
        val list = itemList.value?.filter { s ->
            s.title.contains(text, ignoreCase = true)
        }
        if (!list.isNullOrEmpty()) {
            _filteredItems.value = list!!
        } else {
            _filteredItems.value = itemList.value
        }

    }

    sealed class ElementChange {
        object IsDetailOpen : ElementChange()
        object IsActive : ElementChange()
        object IsSwitcherActive : ElementChange()
    }
}
