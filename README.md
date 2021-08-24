# kafka-connect-logminer

#1.Enable Table-level Supplemental Log
#以table owner 執行
#for project streaming PartyContact(PCR420669)
SQL> ALTER TABLE LS_EBAO.T_POLICY_HOLDER ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;
SQL> ALTER TABLE LS_EBAO.T_INSURED_LIST ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;
SQL> ALTER TABLE LS_EBAO.T_CONTRACT_BENE ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;
SQL> ALTER TABLE LS_EBAO.T_POLICY_HOLDER_LOG ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;
SQL> ALTER TABLE LS_EBAO.T_INSURED_LIST_LOG ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;
SQL> ALTER TABLE LS_EBAO.T_CONTRACT_BENE_LOG ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;
SQL> ALTER TABLE LS_EBAO.T_ADDRESS ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;

#for project streaming ODS
SQL> ALTER TABLE LS_EBAO.T_COMMISION_FEE ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;
SQL> ALTER TABLE LS_EBAO.T_CONTRACT_EXTEND_CX ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;
SQL> ALTER TABLE LS_EBAO.T_CONTRACT_EXTEND_LOG ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;
SQL> ALTER TABLE LS_EBAO.T_CONTRACT_PRODUCT_LOG ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;
SQL> ALTER TABLE LS_EBAO.T_IMAGE ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;
SQL> ALTER TABLE LS_EBAO.JBPM_VARIABLEINSTANCE ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;
SQL> ALTER TABLE LS_EBAO.T_POLICY_CHANGE ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;
SQL> ALTER TABLE LS_EBAO.T_POLICY_PRINT_JOB ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;
SQL> ALTER TABLE LS_EBAO.T_PRODUCT_COMMISION ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;
SQL> ALTER TABLE LS_EBAO.T_PRODUCTION_DETAIL ADD SUPPLEMENTAL LOG DATA (ALL) COLUMNS;


#2.privileges
#以sysdba 執行
SQL>CREATE USER tglminer IDENTIFIED BY tglminerpass default tablespace users;
SQL>GRANT CREATE session,execute_catalog_role,select any transaction,select any dictionary TO tglminer;
SQL>ALTER USER tglminer QUOTA UNLIMITED ON users;
SQL>GRANT CREATE PROCEDURE TO tglminer;
SQL>GRANT CREATE TABLE TO tglminer;
SQL>GRANT CREATE SESSION TO tglminer;
SQL>GRANT EXECUTE ON SYS.DBMS_LOGMNR TO tglminer;
SQL>GRANT EXECUTE ON SYS.DBMS_LOGMNR_D TO tglminer;
SQL>GRANT LOGMINING TO tglminer;


#3.read only user for DB

#######################################################################################
select current_scn from v$database;


########################################## Oracle Logminer ################################
archive log mod : disabled



########################################## Connector #######################################
curl http://localhost:8083

http://localhost:8083
=>{"version":"2.7.0","commit":"448719dc99a19793","kafka_cluster_id":"lNaoMMWMQxSN1TDBN_2QHA"}
http://localhost:8083/connectors
=>["oracle-logminer-connector"]
http://localhost:8083/connectors/oracle-logminer-connector
=>{"name":"oracle-logminer-connector","config":{"connector.class":"com.transglobe.kafka.connect.oracle.OracleSourceConnector","reset.offset":"true","db.hostname":"10.67.67.63","tasks.max":"1","db.user.password":"ls_ebaopwd","table.blacklist":"","table.whitelist":"ls_ebao.TEST_T_POLICY_HOLDER,ls_ebao.TEST_T_INSURED_LIST,ls_ebao.TEST_T_CONTRACT_BENE,ls_ebao.TEST_T_ADDRESS","db.user":"ls_ebao","start.scn":"","db.fetch.size":"1","db.port":"1521","name":"oracle-logminer-connector","multitenant":"false","topic":"","parse.dml.data":"true","db.name":"ebaouat1","db.name.alias":"ebaouat1"},"tasks":[{"connector":"oracle-logminer-connector","task":0}],"type":"source"}
http://localhost:8083/connectors/oracle-logminer-connector/status
=>{"name":"oracle-logminer-connector","connector":{"state":"RUNNING","worker_id":"127.0.0.1:8083"},"tasks":[{"id":0,"state":"FAILED","worker_id":"127.0.0.1:8083","trace":"org.apache.kafka.connect.errors.ConnectException: Error at database tier, Please check : java.sql.SQLException: ORA-01325: archive log mode must be enabled to build into the logstream\nORA-06512: at \"SYS.DBMS_LOGMNR\", line 58\nORA-06512: at line 2\n\n\tat com.transglobe.kafka.connect.oracle.OracleSourceTask.start(OracleSourceTask.java:225)\n\tat org.apache.kafka.connect.runtime.WorkerSourceTask.execute(WorkerSourceTask.java:232)\n\tat org.apache.kafka.connect.runtime.WorkerTask.doRun(WorkerTask.java:185)\n\tat org.apache.kafka.connect.runtime.WorkerTask.run(WorkerTask.java:234)\n\tat java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)\n\tat java.util.concurrent.FutureTask.run(FutureTask.java:266)\n\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n\tat java.lang.Thread.run(Thread.java:748)\n"}],"type":"source"}
http://localhost:8083/connectors/oracle-logminer-connector/tasks
=>[{"id":{"connector":"oracle-logminer-connector","task":0},"config":{"connector.class":"com.transglobe.kafka.connect.oracle.OracleSourceConnector","reset.offset":"true","db.hostname":"10.67.67.63","tasks.max":"1","db.user.password":"ls_ebaopwd","table.blacklist":"","table.whitelist":"ls_ebao.TEST_T_POLICY_HOLDER,ls_ebao.TEST_T_INSURED_LIST,ls_ebao.TEST_T_CONTRACT_BENE,ls_ebao.TEST_T_ADDRESS","db.user":"ls_ebao","task.class":"com.transglobe.kafka.connect.oracle.OracleSourceTask","start.scn":"","db.fetch.size":"1","db.port":"1521","name":"oracle-logminer-connector","multitenant":"false","topic":"","parse.dml.data":"true","db.name":"ebaouat1","db.name.alias":"ebaouat1"}}]
http://localhost:8083/connectors/oracle-logminer-connector/tasks/0/status
=>{"id":0,"state":"FAILED","worker_id":"127.0.0.1:8083","trace":"org.apache.kafka.connect.errors.ConnectException: Error at database tier, Please check : java.sql.SQLException: ORA-01325: archive log mode must be enabled to build into the logstream\nORA-06512: at \"SYS.DBMS_LOGMNR\", line 58\nORA-06512: at line 2\n\n\tat com.transglobe.kafka.connect.oracle.OracleSourceTask.start(OracleSourceTask.java:225)\n\tat org.apache.kafka.connect.runtime.WorkerSourceTask.execute(WorkerSourceTask.java:232)\n\tat org.apache.kafka.connect.runtime.WorkerTask.doRun(WorkerTask.java:185)\n\tat org.apache.kafka.connect.runtime.WorkerTask.run(WorkerTask.java:234)\n\tat java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)\n\tat java.util.concurrent.FutureTask.run(FutureTask.java:266)\n\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n\tat java.lang.Thread.run(Thread.java:748)\n"}
curl -X POST http://localhost:8083/connectors/oracle-logminer-connector/restart
curl -X POST http://localhost:8083/connectors/oracle-logminer-connector/tasks/0/restart



###########################################  kafka ################################
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic [TOPIC] --from-beginning



kafka-connect-oracle is a Kafka source connector for capturing all row based DML changes from Oracle database and streaming these changes to Kafka. Change data capture logic is based on Oracle LogMiner solution.

Only committed changes are pulled from Oracle which are Insert, Update, Delete operations. All streamed messages have related full "sql_redo" statement and parsed fields with values of sql statements. Parsed fields and values are kept in proper field type in schemas.

Messages have old (before change) and new (after change) values of row fields for DML operations. Insert operation has only new values of row tagged as "data". Update operation has new data tagged as "data" and also contains old values of row before change tagged as "before". Delete operation only contains old data tagged as "before".

# News
*   Ability to only capture specified operation like INSERT / UPDATE / DELETE .
*   DDL Capture Support.DDL statements can be captured via this connector.All captured DDL statements are published into <db.name.alias>.<SEGMENT_OWNER>."_GENERIC_DDL" name formatted topic if no topic configuration property is set in property file.If a topic is set for configuration propery , all captured DDL statements are published into this topic with other statements.
*   Partial rollback detection has been implemented
*   With new relases of Oracle database like 19c, CONTINUOUS_MINE option is desupported and Logminer has lost ability mining of redo and archive logs continuously. First release of this connector was based on this property. This Connector now has the ability to capture all changed data(DML changes) without CONTINUOUS_MINE option for new relases of Oracle database. For this change all test have been done on single instances. Working on RAC support
*   Table blacklist configuration property can be used to not capture specified tables or schemas

# Sample Data

**Insert :**


    {    
        "SCN": 768889966828,
        "SEG_OWNER": "TEST",
        "TABLE_NAME": "TEST4",
        "TIMESTAMP": 1537958606000,
        "SQL_REDO": "insert into \"TEST\".\"TEST4\"(\"ID\",\"NAME\",\"PROCESS_DATE\",\"CDC_TIMESTAMP\") values (78238,NULL,NULL,TIMESTAMP ' 2018-09-26 10:43:26.643')",
        "OPERATION": "INSERT",
        "data": {
            "ID": 78238.0,
            "NAME": null,
            "PROCESS_DATE": null,
            "CDC_TIMESTAMP": 1537947806643
        },
        "before": null 
    }

**Update :**

    {
        "SCN": 768889969452,
        "SEG_OWNER": "TEST",
        "TABLE_NAME": "TEST4",
        "TIMESTAMP": 1537964106000,
        "SQL_REDO": "update \"TEST\".\"TEST4\" set \"NAME\" = 'XaQCZKDINhTQBMevBZGGDjfPAsGqTUlCTyLThpmZ' where \"ID\" = 78238 and \"NAME\" IS NULL and \"PROCESS_DATE\" IS NULL and \"CDC_TIMESTAMP\" = TIMESTAMP ' 2018-09-26 10:43:26.643'",
        "OPERATION": "UPDATE",
        "data": {
            "ID": 78238.0,
            "NAME": "XaQCZKDINhTQBMevBZGGDjfPAsGqTUlCTyLThpmZ",
            "PROCESS_DATE": null,
            "CDC_TIMESTAMP": 1537947806643
        },
        "before": {
            "ID": 78238.0,
            "NAME": null,
            "PROCESS_DATE": null,
            "CDC_TIMESTAMP": 1537947806643
        }
    }

**Delete :**

    {
        "SCN": 768889969632,
        "SEG_OWNER": "TEST",
        "TABLE_NAME": "TEST4",
        "TIMESTAMP": 1537964142000,
        "SQL_REDO": "delete from \"TEST\".\"TEST4\" where \"ID\" = 78238 and \"NAME\" = 'XaQCZKDINhTQBMevBZGGDjfPAsGqTUlCTyLThpmZ' and \"PROCESS_DATE\" IS NULL and \"CDC_TIMESTAMP\" = TIMESTAMP ' 2018-09-26 10:43:26.643'",
        "OPERATION": "DELETE",
        "data": null,
        "before": {
            "ID": 78238.0,
            "NAME": "XaQCZKDINhTQBMevBZGGDjfPAsGqTUlCTyLThpmZ",
            "PROCESS_DATE": null,
            "CDC_TIMESTAMP": 1537947806643
        }
    }

# Setting Up

The database must be in archivelog mode and supplemental logging must be enabled.

On database server

    sqlplus / as sysdba    
    SQL>shutdown immediate
    SQL>startup mount
    SQL>alter database archivelog;
    SQL>alter database open;

Enable supplemental logging

    sqlplus / as sysdba    
    SQL>alter database add supplemental log data (all) columns;



# Configuration

## Configuration Properties

|Name|Type|Description|
|---|---|---|
|name|String|Connector name|
|connector.class|String|The name of the java class for this connector.|
|db.name.alias|String|Identifier name for database like Test,Dev,Prod or specific name to identify the database.This name will be used as header of topics and schema names.|
|tasks.max|Integer|Maximum number of tasks to create.This connector uses a single task.|
|topic|String|Name of the topic that the messages will be written to.If it is set a value all messages will be written into this declared topic , if it is not set,  for each database table a topic will be created dynamically.|
|db.name|String|Service name  or sid of the database to connect.Mostly database service name is used.|
|db.hostname|String|Ip address or hostname of Oracle database server.|
|db.port|Integer|Port number of Oracle database server.|
|db.user|String |Name of database user which is used to connect to database to start and execute logminer. This           user must provide necessary privileges mentioned above.|
|db.user.password|String|Password of database user.|
|db.fetch.size|Integer|This config property sets Oracle row fetch size value.|
|table.whitelist|String|A comma separated list of database schema or table names which will be captured.<br />For all schema capture **<SCHEMA_NAME>.*** <br /> For table capture **<SCHEMA_NAME>.<TABLE_NAME>** must be specified.|
|parse.dml.data|Boolean|If it is true , captured sql DML statement will be parsed into fields and values.If it is false only sql DML statement is published.
|reset.offset|Boolean|If it is true , offset value will be set to current SCN of database when connector started.If it is false connector will start from last offset value.
|start.scn|Long|If it is set , offset value will be set this specified value and logminer will start at this SCN.If connector would like to be started from desired SCN , this property can be used.
|multitenant|Boolean|If true, multitenant support is enabled.  If false, single instance configuration will be used.
|table.blacklist|String|A comma separated list of database schema or table names which will not be captured.<br />For all schema capture **<SCHEMA_NAME>.*** <br /> For table capture **<SCHEMA_NAME>.<TABLE_NAME>** must be specified.|
|dml.types|String|A comma separated list of DML operations (INSERT,UPDATE,DELETE). If not specified the default behavior of replicating all DML operations occurs,if specified only specified operations are captured.|
|||



## Example Config

    name=oracle-logminer-connector
    connector.class=com.ecer.kafka.connect.oracle.OracleSourceConnector
    db.name.alias=test
    tasks.max=1
    topic=cdctest
    db.name=testdb
    db.hostname=10.1.X.X
    db.port=1521
    db.user=kminer
    db.user.password=kminerpass
    db.fetch.size=1
    table.whitelist=TEST.*,TEST2.TABLE2
    table.blacklist=TEST2.TABLE3
    parse.dml.data=true
    reset.offset=false
    multitenant=false

## Building and Running

    mvn clean package

    Copy kafka-connect-oracle-1.0.jar and lib/ojdbc7.jar to KAFKA_HOME/lib folder. If CONFLUENT platform is using for kafka cluster , copy ojdbc7.jar and  kafka-connect-oracle-1.0.jar to $CONFLUENT_HOME/share/java/kafka-connect-jdbc folder.

    Copy config/OracleSourceConnector.properties file to $KAFKA_HOME/config . For CONFLUENT copy properties file to $CONFLUENT_HOME/etc/kafka-connect-jdbc

    To start connector

    cd $KAFKA_HOME
    ./bin/connect-standalone.sh ./config/connect-standalone.properties ./config/OracleSourceConnector.properties

    In order to start connector with Avro serialization 
    
    cd $CONFLUENT_HOME

    ./bin/connect-standalone ./etc/schema-registry/connect-avro-standalone.properties ./etc/kafka-connect-jdbc/OracleSourceConnector.properties 

    Do not forget to populate connect-standalone.properties  or connect-avro-standalone.properties with the appropriate hostnames and ports.

# Todo:

    1. Implementation for DDL operations
    2. Support for other Oracle specific data types
    3. New implementations
    4. Performance Tuning
    5. Initial data load
    6. Bug fix    