package com.example.vineria.modelos.dto

import com.example.vineria.modelos.Cliente
import java.util.*

class PagoDTO {
    lateinit var fechaDate: Date
    lateinit var fecha: String
    lateinit var nombreCliente: String
    lateinit var nroFactura: String
    lateinit var tipoFactura: String
    lateinit var importe: String
    lateinit var total: String
}