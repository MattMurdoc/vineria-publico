# Vineria Virgen de Huachana

Aplicación mobile para preventistas para tomar pedidos, realizar cobros, etc. en los negocios

## Desarrollo

### Deploy de base de datos de prueba

En la carpeta _bd_test_ se encuentran los archivos para levantar un contenedor Docker con una base de datos SQLServer 2017 con datos de prueba.
Para iniciar la misma se debe ejecutar:

```sh
cd bd_test
docker build -t vineriadatabase .
```
Estos comandos crean una imágen docker con la base de datos incluyendo datos de prueba.
Luego cada vez que necesite iniciar el motor de bases de datos deberé ejecutar:

```sh
docker run -d -p 1433:1433 --name vineriadatabase vineriadatabase
```

Este comando iniciará el motor en **localhost:1433**