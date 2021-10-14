#!/bin/sh

/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Your!Passw0rd -d master -i create_tables.sql
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Your!Passw0rd -d master -i datos.sql