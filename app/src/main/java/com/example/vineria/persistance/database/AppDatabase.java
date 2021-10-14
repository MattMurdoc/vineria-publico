package com.example.vineria.persistance.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.vineria.modelos.Articulo;
import com.example.vineria.modelos.Cliente;
import com.example.vineria.modelos.DetalleFactura;
import com.example.vineria.modelos.DetallePresupuesto;
import com.example.vineria.modelos.Empleado;
import com.example.vineria.modelos.Envase;
import com.example.vineria.modelos.Factura;
import com.example.vineria.modelos.Item;
import com.example.vineria.modelos.PagoPorFactura;
import com.example.vineria.modelos.PagoPorPresupuesto;
import com.example.vineria.modelos.Pedido;
import com.example.vineria.modelos.Presupuesto;
import com.example.vineria.persistance.dao.ArticuloDAO;
import com.example.vineria.persistance.dao.ClienteDAO;
import com.example.vineria.persistance.dao.DetalleFacturaDAO;
import com.example.vineria.persistance.dao.DetallePresupuestoDAO;
import com.example.vineria.persistance.dao.EmpleadoDAO;
import com.example.vineria.persistance.dao.EnvaseDAO;
import com.example.vineria.persistance.dao.FacturaDAO;
import com.example.vineria.persistance.dao.ItemDAO;
import com.example.vineria.persistance.dao.PagoPorFacturaDAO;
import com.example.vineria.persistance.dao.PagoPorPresupuestoDAO;
import com.example.vineria.persistance.dao.PedidoDAO;
import com.example.vineria.persistance.dao.PresupuestoDAO;

@Database(entities = {Cliente.class,
                      Articulo.class,
                      Empleado.class,
                      Pedido.class,
                      Item.class,
                      Envase.class,
                      Factura.class,
                      DetalleFactura.class,
                      PagoPorFactura.class,
                      Presupuesto.class,
                      DetallePresupuesto.class,
                      PagoPorPresupuesto.class}, version = 1)
          @TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ClienteDAO clienteDAO();

    public abstract ArticuloDAO articuloDAO();

    public abstract EmpleadoDAO empleadoDAO();

    public abstract PedidoDAO pedidoDAO();

    public abstract ItemDAO itemDAO();

    public abstract EnvaseDAO envaseDAO();

    public abstract FacturaDAO facturaDAO();

    public abstract DetalleFacturaDAO detalleFacturaDAO();

    public abstract PagoPorFacturaDAO pagoPorFacturaDAO();

    public abstract PresupuestoDAO presupuestoDAO();

    public abstract DetallePresupuestoDAO detallePresupuestoDAO();

    public abstract PagoPorPresupuestoDAO pagoPorPresupuestoDAO();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "preventistadb").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}