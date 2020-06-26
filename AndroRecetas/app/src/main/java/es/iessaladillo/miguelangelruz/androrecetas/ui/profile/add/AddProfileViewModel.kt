package es.iessaladillo.miguelangelruz.androrecetas.ui.profile.add

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.base.Event
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.UserDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.User
import kotlin.concurrent.thread

class AddProfileViewModel(private val userDao: UserDao, private val application: Application) :
    ViewModel() {
    private val _onBack: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val onBack: LiveData<Event<Boolean>> get() = _onBack

    private val _message: MutableLiveData<Event<String>> = MutableLiveData()
    val message: LiveData<Event<String>> get() = _message

    private val _currentPlayerId: MutableLiveData<Long> = MutableLiveData(0)
    val currentPlayerId: LiveData<Long>
        get() = _currentPlayerId

    fun addUser(userName: String, password: String, imageUser: Int) {
        thread {
            try {
                userDao.insertUser(User(0, userName, password, imageUser, "", ""))
                _onBack.postValue(Event(true))

            } catch (e: Exception) {
                _message.postValue(Event("ERROR"))
            }
        }
    }

    fun setCurrentPlayerId(userId: Long) {
        _currentPlayerId.value = userId
    }

    fun errorValidationMessage() {
        _message.postValue(Event(application.resources.getString(R.string.error_validation_message)))
    }
}