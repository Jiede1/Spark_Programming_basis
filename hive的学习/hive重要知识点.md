* hive的分区  
分区表的知识  

* hive内部表外部表  
内部表数据由Hive自身管理，外部表数据由HDFS管理  
内部表数据由Hive自身管理，外部表数据由HDFS管理； 
内部表数据存储的位置是hive.metastore.warehouse.dir（默认：/user/hive/warehouse），外部表数据的存储位置由自己制定；   
删除内部表会直接删除元数据（metadata）及存储数据；删除外部表仅仅会删除元数据，HDFS上的文件并不会被删除；   
对内部表的修改会将修改直接同步给元数据，而对外部表的表结构和分区进行修改，则需要修复（MSCK REPAIR TABLE table_name;）  

