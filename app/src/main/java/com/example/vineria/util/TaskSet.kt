package com.example.vineria.util

import com.example.vineria.clases.Tasks

// Representa un conjunto de tareas que cambia en el tiempo.
// Cada tarea puede tener cierto número de ejecuciones en un momento dado.

class TaskSet {
    private val runsOf: MutableMap<Tasks, Int> = mutableMapOf()
    private var total: Int = 0

    // Retorna true si la cantidad activa de ese task era 0.
    fun add(task: Tasks): Boolean {
        val prevVal = runsOf[task] ?: 0
        runsOf[task] = prevVal + 1
        total += 1
        return (prevVal == 0)
    }

    // Retorna true si la cantidad activa de ese task era 1.
    fun remove(task: Tasks): Boolean {
        val prevVal = runsOf[task]?: throw Exception("Invalid usage.")
        runsOf[task] = prevVal - 1
        total -= 1
        return (prevVal == 1)
    }

    fun isNotEmpty(): Boolean = total > 0

    operator fun contains(task: Tasks): Boolean = (runsOf[task] ?: 0) > 0

    override fun toString(): String {
        var r = "{"
        runsOf.forEach {
            if (it.value > 0) r += it.key.toString() + " "
        }
        return r + "}"
    }

    companion object {
        private val TAG = "TaskSet"
    }
}