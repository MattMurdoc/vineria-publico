package com.example.vineria.persistance.sqlserver

class Queries {
    companion object {
        val SELECT_CLIENTES = "SELECT clie_codigo, clie_nombre, esta_codigo, clie_Ndocumento, tiva_codigo, clie_CUIT FROM ge_cliente";
        val SELECT_ARTICULOS = "SELECT arti_codigo, arti_nombre, arti_imagen, arti_precioCompra, arti_precioLista1, arti_precioLista2, arti_precioLista3, arti_porcLista1, arti_porcLista2, arti_porcLista3, arti_vigente, arti_codigoBarra, arti_envases, unid_codigo, prve_codigo, arti_inhiL1, arti_inhiL2, arti_inhiL3, enva_codigo, arti_modificado FROM ge_articulo WHERE arti_vigente = 1";
        val SELECT_EMPLEADOS = "SELECT empl_codigo, empl_nombre FROM tb_empleado";
        val SELECT_ENVASES = "SELECT enva_codigo, enva_nombre FROM ge_envase";
        val SELECT_FACTURAS = "SELECT fact_codigo, fact_numero, fact_numeron, fact_punto, fact_fecha, fact_estado, clie_codigo, fact_descuento, fact_total, fact_estadocobro, fact_fechavencimiento, fact_iva, fact_tipo, fact_fechaA, fact_emplA, empl_codigo, fact_estacion, fact_cliente, fact_impresora FROM fa_factura WHERE fact_estado = 1 AND fact_estadocobro IN ('1', '2')";
        val SELECT_FACTURA_DETALLE = "SELECT deta_codigo, fact_codigo, arti_codigo, deta_cantidad, deta_importeST, deta_importeU, deta_Lista FROM fa_detalleXfactura WHERE fact_codigo = %d";
        val SELECT_PAGO_X_FACTURA = "SELECT pfac_codigo, fact_codigo, pfac_fecha, form_codigo, pfac_importe, pfac_empleado, pfac_banco, pfac_Ntransferencia, pfac_observaciones, pfac_fechavenc, pfac_Ncheque, tarj_codigo, pfac_Ntarjeta, pfac_Nlote, pfac_estado, pfac_fechaAsiento, pfac_pasado, pfac_tablet, pfac_fechaPaso FROM fa_pagoXfactura WHERE fact_codigo = %d";
        val SELECT_PRESUPUESTOS = "SELECT pres_codigo, pres_fecha, pres_cliente, pres_total, pres_fechavencimiento, pres_observaciones, pres_estado, pres_estadocobro, clie_codigo FROM fa_presupuesto WHERE pres_estado = 1 AND pres_estadocobro IN ('1', '2')";
        val SELECT_PRESUPUESTO_DETALLE = "SELECT deps_codigo, pres_codigo, arti_codigo, deps_cantidad, deps_importeST, deps_importeU, deps_Lista FROM fa_presupuestoXdetalle WHERE pres_codigo = %d";
        val SELECT_PAGO_X_PRESUPUESTO = "SELECT ppre_codigo, pres_codigo, ppre_fecha, form_codigo, ppre_importe, empl_codigo, ppre_observaciones, ppre_estado, ppre_fechaAsiento, ppre_pasado, ppre_tablet, ppre_fechaPaso FROM fa_pagoXpresupuesto WHERE pres_codigo = %d";
        val INSERT_PAGO_X_FACTURA = "insert into fa_pagoXfactura(fact_codigo, pfac_fecha, form_codigo," +
                                        "pfac_importe, pfac_empleado, pfac_banco," +
                                        "pfac_Ntransferencia, pfac_observaciones, pfac_fechavenc," +
                                        "pfac_Ncheque, tarj_codigo, pfac_Ntarjeta, pfac_Nlote, pfac_estado," +
                                        "pfac_fechaAsiento, pfac_pasado, pfac_tablet, pfac_fechaPaso)" +
                                        "values (:fact_codigo:, :pfac_fecha:, :form_codigo:," +
                                        ":pfac_importe:, :pfac_empleado:, :pfac_banco:," +
                                        ":pfac_Ntransferencia:, :pfac_observaciones:, :pfac_fechavenc:," +
                                        ":pfac_Ncheque:, :tarj_codigo:, :pfac_Ntarjeta:, :pfac_Nlote:, :pfac_estado:," +
                                        ":pfac_fechaAsiento:, :pfac_pasado:, :pfac_tablet:, :pfac_fechaPaso:)"

        val INSERT_PAGO_X_PRESUPUESTO = "insert into fa_pagoXpresupuesto(ppre_codigo, pres_codigo, ppre_fecha," +
                " form_codigo, ppre_importe, empl_codigo, ppre_observaciones, ppre_estado, ppre_fechaAsiento," +
                " ppre_pasado, ppre_tablet, ppre_fechaPaso) VALUES (:ppre_codigo:, :pres_codigo:, :ppre_fecha:," +
                " :form_codigo:, :ppre_importe:, :empl_codigo:, :ppre_observaciones:, :ppre_estado:, :ppre_fechaAsiento:," +
                " :ppre_pasado:, :ppre_tablet:, :ppre_fechaPaso:)"
    }
}