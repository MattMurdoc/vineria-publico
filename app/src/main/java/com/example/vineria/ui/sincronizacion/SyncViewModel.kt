package com.example.vineria.ui.sincronizacion

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.vineria.clases.ApplicationViewModel
import com.example.vineria.clases.Tasks
import com.example.vineria.modelos.SyncStatus
import com.example.vineria.repository.SyncRepository


class SyncViewModel(app: Application) : ApplicationViewModel(app) {

    private val syncRepository = SyncRepository(app)

    val clienteWorkStatus: MutableLiveData<SyncStatus> by lazy { MutableLiveData<SyncStatus>() };
    val empleadoWorkStatus: MutableLiveData<SyncStatus> by lazy { MutableLiveData<SyncStatus>() };
    val articuloWorkStatus: MutableLiveData<SyncStatus> by lazy { MutableLiveData<SyncStatus>() };
    val envaseWorkStatus: MutableLiveData<SyncStatus> by lazy { MutableLiveData<SyncStatus>() };
    val facturaWorkStatus: MutableLiveData<SyncStatus> by lazy { MutableLiveData<SyncStatus>() };
    val presupuestoWorkStatus: MutableLiveData<SyncStatus> by lazy { MutableLiveData<SyncStatus>() };

    init {
        clienteWorkStatus.postValue(SyncStatus.NONE)
        empleadoWorkStatus.postValue(SyncStatus.NONE)
        articuloWorkStatus.postValue(SyncStatus.NONE)
        envaseWorkStatus.postValue(SyncStatus.NONE)
        facturaWorkStatus.postValue(SyncStatus.NONE)
        presupuestoWorkStatus.postValue(SyncStatus.NONE)
    }

    fun sync() = launchTask(Tasks.SyncClientes) {
        sincronizar()
    }

    internal suspend fun sincronizar() {
        changeStatusToSync()

        try {
            syncRepository.deleteWork()
        } catch (throwable: Throwable) {
            sendToastToActivity("Ocurrió un error al sincronizar")
            changeStatusToError()
            return
        }

        try {
            syncRepository.clienteWork()
            clienteWorkStatus.postValue(SyncStatus.SUCCESS)
        } catch (throwable: Throwable) {
            sendToastToActivity("Ocurrió un error al sincronizar")
            clienteWorkStatus.postValue(SyncStatus.ERROR)
        }

        try {
            syncRepository.empleadoWork()
            empleadoWorkStatus.postValue(SyncStatus.SUCCESS)
        } catch (throwable: Throwable) {
            sendToastToActivity("Ocurrió un error al sincronizar")
            empleadoWorkStatus.postValue(SyncStatus.ERROR)
        }

        try {
            syncRepository.articuloWork()
            articuloWorkStatus.postValue(SyncStatus.SUCCESS)
        } catch (throwable: Throwable) {
            sendToastToActivity("Ocurrió un error al sincronizar")
            articuloWorkStatus.postValue(SyncStatus.ERROR)
        }

        try {
            syncRepository.envaseWork()
            envaseWorkStatus.postValue(SyncStatus.SUCCESS)
        } catch (throwable: Throwable) {
            sendToastToActivity("Ocurrió un error al sincronizar")
            envaseWorkStatus.postValue(SyncStatus.ERROR)
        }

        try {
            syncRepository.facturaWork()
            facturaWorkStatus.postValue(SyncStatus.SUCCESS)
        } catch (throwable: Throwable) {
            sendToastToActivity("Ocurrió un error al sincronizar")
            facturaWorkStatus.postValue(SyncStatus.ERROR)
        }

        try {
            syncRepository.presupuestoWork()
            presupuestoWorkStatus.postValue(SyncStatus.SUCCESS)
        } catch (throwable: Throwable) {
            sendToastToActivity("Ocurrió un error al sincronizar")
            presupuestoWorkStatus.postValue(SyncStatus.ERROR)
        }
    }

    internal fun changeStatusToSync() {
        clienteWorkStatus.postValue(SyncStatus.SYNC)
        empleadoWorkStatus.postValue(SyncStatus.SYNC)
        articuloWorkStatus.postValue(SyncStatus.SYNC)
        envaseWorkStatus.postValue(SyncStatus.SYNC)
        facturaWorkStatus.postValue(SyncStatus.SYNC)
        presupuestoWorkStatus.postValue(SyncStatus.SYNC)
    }

    internal fun changeStatusToError() {
        clienteWorkStatus.postValue(SyncStatus.ERROR)
        empleadoWorkStatus.postValue(SyncStatus.ERROR)
        articuloWorkStatus.postValue(SyncStatus.ERROR)
        envaseWorkStatus.postValue(SyncStatus.ERROR)
        facturaWorkStatus.postValue(SyncStatus.ERROR)
        presupuestoWorkStatus.postValue(SyncStatus.ERROR)
    }

    companion object {
        private val TAG = "SyncViewModel"
    }
}