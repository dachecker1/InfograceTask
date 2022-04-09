package com.vk.dachecker.infogracetask.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.vk.dachecker.infogracetask.SidePanelItemFactory
import com.vk.dachecker.infogracetask.data.ListItemRepositoryImpl
import com.vk.dachecker.infogracetask.domain.*
import com.vk.dachecker.infogracetask.domain.usecase.*
import kotlinx.coroutines.*
import kotlin.properties.Delegates

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

    private val _elements =
        MutableLiveData<List<SidePanelItem>>(emptyList()) //список всех элементов, подключен к БД
    val elements: LiveData<List<SidePanelItem>>
        get() = _elements

    private val _elementItem = MutableLiveData<SidePanelItem>() //один элемент. подключен к БД
    val elementItem: LiveData<SidePanelItem>
        get() = _elementItem

    //метод сохранения изменений в элементе, запись изменений в БД
    fun editItem(copyForReplace : SidePanelItem) {
        viewModelScope.launch {
            editItemUseCase.editItem(copyForReplace)
        }
    }

//    fun setItems() {
//        viewModelScope.launch {
//            if(_elements.value.isNullOrEmpty()) {
//                val tempList = item.getItems()
//                tempList.forEach {
//                    addItemUseCase.addItem(it)
//                }
//                val list = itemList.value
//                _elements.postValue(list!!)
//                counter = tempList.size
//            } else {
//                counter = itemList.value?.size!!
//
//            }
//        }
//    }

    //метод изменения статуса переключателей
    fun changeSwitcherStatus() {
        _switcherManager.value = _switcherManager.value != true
    }

    suspend fun getItemFlow(): List<SidePanelItem> {
        val res = CompletableDeferred<List<SidePanelItem>>()
        viewModelScope.launch {
            if (_elements.value.isNullOrEmpty()) {
                _elements.postValue(itemList.value)
                delay(1000)
                res.complete(_elements.value!!) // value
            } else {
                res.complete(_elements.value!!)
            }
        }
        val result = res.await()
        Log.d("MyTag", result.toString())
        return result
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
