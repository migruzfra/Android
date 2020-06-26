package es.iessaladillo.miguelangelruz.androrecetas.ui.profile.edit

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.miguelangelruz.androrecetas.base.Event
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.UserDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.User
import kotlin.concurrent.thread

class EditProfileViewModel (private val userDao: UserDao, application: Application) : ViewModel() {
    private val _onBack : MutableLiveData<Event<Boolean>> = MutableLiveData()
    val onBack : LiveData<Event<Boolean>> get()=_onBack

    private val _message : MutableLiveData<Event<String>> = MutableLiveData()
    val message : LiveData<Event<String>> get()=_message

    private val _currentPlayerAvatar : MutableLiveData<Int> = MutableLiveData()
    val currentPlayerAvatar : LiveData<Int>
        get()=_currentPlayerAvatar

    fun updateUser(userId: Long, userName: String, password: String, imageUser: Int, shoppingList: String){
        thread {
            try {
                userDao.updateUser(User(userId, userName, password, imageUser, "", shoppingList))
                _onBack.postValue(Event(true))

            }catch (e: Exception){
                _message.postValue(Event("ERROR"))
            }
        }
    }

    fun queryUser(userId: Long): User {
        return userDao.queryUserFromId(userId)
    }

    fun setCurrentPlayerAvatar(avatar : Int){
        _currentPlayerAvatar.value=avatar
    }
}