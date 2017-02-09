# Pré-requisitos
1. Instale o WebSphere MQ 8.0 [Linux 64-bits](https://apps.na.collabserv.com/files/form/api/library/cc6a06b8-592a-455f-b8f1-d8bf78652fbf/document/831a0c3c-099c-4c24-93b9-109e4bcd28e6/media/mqadv_dev80_linux_x86-64.tar.gz) [Windows 64-bits](https://apps.na.collabserv.com/files/form/api/library/cc6a06b8-592a-455f-b8f1-d8bf78652fbf/document/77ad717d-0d28-475b-9c3c-42d10f99d230/media/mqadv_dev80_windows.zip)
1. Instale o client Java MQ no repositório Maven local:
```
mvn install:install-file -Dfile=/opt/mqm/java/lib/com.ibm.mq.allclient.jar -DgroupId=com.ibm -DartifactId=com.ibm.mq.allclient -Dversion=8.0 -Dpackaging=jar
```
1. Instale o IBM Integration Bus for Developers [Linux 64-bits](https://apps.na.collabserv.com/files/form/api/library/cc6a06b8-592a-455f-b8f1-d8bf78652fbf/document/b0c547c4-3bc3-4b21-a541-e69870b00d12/media/10.0.0.3-IIB-LINUX64-DEVELOPER.tar.gz) [Windows](https://apps.na.collabserv.com/files/form/api/library/cc6a06b8-592a-455f-b8f1-d8bf78652fbf/document/2879188f-4e6a-479e-b9ca-ced5110da1fc/media/10.0.0.3-IIB-WIN64-DEVELOPER.exe)
1. Instale o [Microsoft SQL Server 2012 Evaluation](https://www.microsoft.com/pt-br/download/details.aspx?id=29066)
1. Instale o [Microsoft SQL Server JDBC Driver 4.2] (https://www.microsoft.com/pt-br/download/confirmation.aspx?id=11774) [Linux](https://apps.na.collabserv.com/files/form/api/library/cc6a06b8-592a-455f-b8f1-d8bf78652fbf/document/3e3d21a0-9fe3-47f5-b533-9d25946d4f97/media/sqljdbc_4.2.6420.100_ptb.tar.gz) [Windows](https://apps.na.collabserv.com/files/form/api/library/cc6a06b8-592a-455f-b8f1-d8bf78652fbf/document/b6897d11-ebae-47c1-8e0c-572f3fb125bb/media/sqljdbc_4.2.6420.100_ptb.exe)
1. Instale o driver no repositório Maven local:
```
mvn install:install-file -Dfile=sqljdbc4.jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc42 -Dversion=4.2 -Dpackaging=jar
```
1. Baixar o JHipster Registry:
```
git clone https://github.com/jhipster/jhipster-registry
```

# Banco de Dados
1. Crie o banco platform:

```

CREATE DATABASE platform

USE [platform]
create schema platform
CREATE LOGIN [platform] WITH PASSWORD='platform'
CREATE USER [platform] FOR LOGIN [platform] WITH DEFAULT_SCHEMA=[platform]
CREATE LOGIN [exemplo] WITH PASSWORD='exemplo'
CREATE USER [exemplo] FOR LOGIN [exemplo] WITH DEFAULT_SCHEMA=[platform]

exec sp_addsrvrolemember @loginame='platform',@rolename='sysadmin'
exec sp_addsrvrolemember @loginame='exemplo',@rolename='sysadmin'
```

# WebSphere MQ
1. Criar Queue Manager:
```
$ crtmqm QM01
WebSphere MQ queue manager created.
Directory '/var/mqm/qmgrs/QM01' created.
The queue manager is associated with installation 'Installation1'.
Creating or replacing default objects for queue manager 'QM01'.
Default objects statistics : 79 created. 0 replaced. 0 failed.
Completing setup.
Setup completed.
$ strmqm QM01
The system resource RLIMIT_NOFILE is set at an unusually low level for WebSphere MQ.
WebSphere MQ queue manager 'QM01' starting.
The queue manager is associated with installation 'Installation1'.
5 log records accessed on queue manager 'QM01' during the log replay phase.
Log replay for queue manager 'QM01' complete.
Transaction manager state recovered for queue manager 'QM01'.
WebSphere MQ queue manager 'QM01' started using V8.0.0.4.
$ echo 'DEFINE LISTENER(LISTENER) TRPTYPE(TCP) PORT(1414) CONTROL(QMGR)' | runmqsc QM01
$ echo 'START LISTENER(LISTENER)' | runmqsc QM01
$ echo 'ALTER AUTHINFO(SYSTEM.DEFAULT.AUTHINFO.IDPWOS) AUTHTYPE(IDPWOS)  CHCKCLNT(OPTIONAL)' | runmqsc QM01
$ echo 'SET CHLAUTH(SYSTEM.*)  TYPE(ADDRESSMAP) ADDRESS(*) ACTION(REMOVE)' | runmqsc QM01
$ echo 'set CHLAUTH(*) TYPE(BLOCKUSER)  USERLIST(*MQADMIN) ACTION(REMOVE)' | runmqsc QM01
$ echo 'alter channel(SYSTEM.DEF.SVRCONN) CHLTYPE(SVRCONN) MCAUSER('mqm')' | runmqsc QM01
$ echo "define ql('platform/CommandQueue')" | runmqsc QM01
$ echo "define ql('platform/ResultQueue')" | runmqsc QM01
```

# Build
Faça o build na seguinte ordem:
```
cd ons-platform-common/pom.xml/ons-platform-common/
mvn install
cd -
cd ons-exemplo-common/ons-exemplo-common
mvn install
cd -
cd ons-platform
mvn install
```

# Run
1. Subir o SQL Server
1. Subir o Integration Server (procure o respectivo serviço Windows)
1. Implantar a aplicação de integração no Integration Server
1. Subir o JHipster Registry (OBS: utiliza a porta 8761):
```
cd jhipster-registry
mvn
```
1. Subir a plataforma (OBS: utiliza a porta 8081):
```
cd ons-platform
mvn
```
1. Subir o exemplo-write (OBS: utiliza a porta 8082):
```
cd ons-exemplo-write
mvn
```
1. Subir o exemplo-read (OBS: utiliza a porta 8083):
```
cd ons-exemplo-read
mvn
```
1. Subir o portal (OBS: utiliza a porta 8080):
```
cd ons-portal
mvn
```
1. Abrir o browser no endereço http://localhost:8080

# Desenvolvimento

1. Instale o IBM Java Development Kit 1.8 (https://www.ibm.com/developerworks/java/jdk/java8/)
1. Instale o [Spring Tool Suite] (http://spring.io/tools/sts/all)
1. Importe os projetos como Maven Project (File -> Import -> Existing Maven Projects)