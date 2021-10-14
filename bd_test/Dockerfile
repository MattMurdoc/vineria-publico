FROM mcr.microsoft.com/mssql/server:2017-latest 
COPY ./create_tables.sql .
COPY ./datos.sql .
COPY ./start.sh .
COPY ./entrypoint.sh .
ENV ACCEPT_EULA=Y
ENV SA_PASSWORD=Your!Passw0rd
RUN chmod +x ./start.sh
CMD /bin/bash ./entrypoint.sh