package com.example.vineria.clases

import android.app.Application
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*

import com.example.vineria.util.ActivityUtils.toast
import com.example.vineria.util.TaskSet
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KProperty


abstract class ApplicationViewModel(val app: Application) : AndroidViewModel(app) {
    val tasks: TaskSet = TaskSet()
    val ownLiveData = MediatorLiveData<Unit>()
    private val toastLiveData = MutableLiveData<String>()
    private var ressetationUuid = 0

    fun <T> resettable(initializer: () -> T): ApplicationResetteable<T> =
        ApplicationResetteable({ ressetationUuid }, initializer)

    // Resetea todos los "resettables"
    fun resetResettables() {
        ressetationUuid += 1

        // Para hacer que se triggereen de nuevo cada uno de los sources.
        notifyChange()
    }

    class ApplicationResetteable<T>(
        private val uuidGetter: () -> Int,
        private val initializer: () -> T
    ) {
        var value: T = initializer()
        var ressetationUuid = uuidGetter()

        operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
            if (uuidGetter() != ressetationUuid) {
                value = initializer()
                ressetationUuid = uuidGetter()
            }
            return value
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            this.value = value
            ressetationUuid = uuidGetter()
        }
    }

    fun sendToastToActivity(text: String?) {
        if (text == null) return
        toastLiveData.postValue(text)
    }

    fun observe(activity: AppCompatActivity, onViewModelChange: () -> Unit) {
        toastLiveData.observe(activity, Observer {
            activity.toast(it)
        })
        ownLiveData.observe(activity, Observer {
            onViewModelChange()
        })
    }

    // A esta funcion se le pasas un live data llamado source, y, cuando este source cambia
    // su contenido, notifica a la actividad.
    // El turnOn y turnOff sirve para poder implementar al reseteado de estado del viewModel.
    fun <S : Any?> changeWhen(source: LiveData<S>, block: (S) -> Unit) {
        ownLiveData.addSource(source) {
            block(it)
            notifyChange()
        }
    }

    fun launchTask(
        task: Tasks = Tasks.UndefinedTask,
        coroutineContext: CoroutineContext = Dispatchers.IO,
        block: suspend () -> Unit
    ): Job {
        if (tasks.add(task)) {
            notifyChange()
        }

        return viewModelScope.launch(coroutineContext) {
            try {
                block()
            } catch (e: CancellationException) {
                Log.i(TAG, "Una task fue cancelada") // Todo hacer un toString de la task.
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            } finally {
                if (tasks.remove(task)) {
                    notifyChange()
                }
            }
        }
    }

    fun notifyChange() {
        Log.v(TAG, "Notificando cambios a la actividad...")
        ownLiveData.postValue(null)
    }

    companion object {
        private val TAG = "ApplicationViewModel"

        fun <T : ApplicationViewModel> T.change(block: T.() -> Unit) {
            this.block()
            notifyChange()
        }

        fun <T : ApplicationViewModel> T.changeWithoutNotify(block: T.() -> Unit) {
            this.block()
        }
    }
}

