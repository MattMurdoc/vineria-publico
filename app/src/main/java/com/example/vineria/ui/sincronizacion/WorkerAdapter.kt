package com.example.vineria.ui.sincronizacion

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.modelos.SyncStatus

class WorkerAdapter(private val dataSet: HashMap<Int, Pair<String, MutableLiveData<SyncStatus>>>, private val owner: LifecycleOwner) :
    RecyclerView.Adapter<WorkerAdapter.ViewHolder>() {

    class ViewHolder(view: View, owner: LifecycleOwner) : RecyclerView.ViewHolder(view) {
        val entidad: TextView = view.findViewById(R.id.entidad)
        val circle: ImageView = view.findViewById(R.id.circle)
        val estado: TextView = view.findViewById(R.id.estado)
        val owner: LifecycleOwner

        init {
            this.owner = owner
        }

        fun observeWork(syncStatus: MutableLiveData<SyncStatus>) {
            syncStatus.observe(owner, {
                val shape = ContextCompat.getDrawable(itemView.context, R.drawable.circle)!!
                shape.mutate()
                if (it == null || it.name == SyncStatus.NONE.name) {
                    shape.setTint(Color.parseColor("#666666"))
                    estado.text = "Sin estado"
                } else {
                    if (it.name == SyncStatus.SYNC.name) {
                        shape.setTint(Color.parseColor("#E6E600"))
                        estado.text = "Sincronizando"
                    } else if (it.name == SyncStatus.SUCCESS.name) {
                        shape.setTint(Color.parseColor("#007300"))
                        estado.text = "Completado"
                    } else if (it.name == SyncStatus.ERROR.name) {
                        shape.setTint(Color.parseColor("#E50000"))
                        estado.text = "Error"
                    }
                    circle.background = shape
                }
            })
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_worker, viewGroup, false)

        return ViewHolder(view, owner)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.entidad.text = dataSet[position]!!.first
        viewHolder.observeWork(dataSet[position]!!.second)
    }

    override fun getItemCount() = dataSet.size

}