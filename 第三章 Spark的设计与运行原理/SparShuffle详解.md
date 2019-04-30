#### Spark shuffle详解



 Spark执行某些操作时，会出现shuffle操作。抽象的讲，spark执行窄依赖时，不会发生shuffle行为，但在执行宽依赖时，会出现。

* 窄依赖： 一个父RDD的分区对应于一个子RDD的分区，或多个父RDD的分区对应于一个子RDD的分区。这种操作有代表性的是：map ，filter，union。

* 宽依赖： 一个父RDD的分区对应于多个子RDD的分区。这种操作典型的是：groupByKey，sortByKey。

* 对于Join操作，两种依赖都有可能产生。

Spark的shuffle过程包含两个部分，shuffle write（发生在map端），shuffle fetch（发生在reduce端）。

 在Hadoop的mapreduce中就已经存在shuffle。Hadoop的shuffle是基于缓存和磁盘的（缓存溢写），比较耗时，IO也很高。Spark中实现shuffle是在Hadoop的基础上，做了优化。

* shuffle write： 
  
  这过程发生在map端。在Spark较低版本时，采用的是Hash Based Shuffle，这过程最核心的做法是，在mapper端，每个map任务会根据reduce任务的个数，生成map*reduce个数的Bucket，然后，基于系统默认的·hash算法，map端的数据会根据每个键值对，把key hash到不同的bucket中。当reduce任务启动时，他·会根据自己的任务id和所依赖的map任务id，回去远端和本地取得自己对应的bucket，进行reduce·操作。
  
  在新版本的Spark中，放弃了这种做法，原因是如果map和reduce任务过多，生成的bucket将非常多，造成很大压力[。因此，Spark逐渐引入Sort Based Shuffle，后面更是引入了Tungsten-Sort Based Shuffle，这个是]()直接使用堆外内存和新的内存管理模型，节省了内存空间和大量的gc， 是为了提升性能。
  
  这里介绍下Sort Based Shuffle：考虑到多个bucket带来了很多问题，因此新版的Spark采用了将多个桶写入一个文件的方式。每个map的任务所有的输出数据都只写入一个文件中，并且生成一个索引文件，其中存储了map任务中数据是如何被分区的信息，这样reduce任务查询这个文件时，就能找出对应自己任务的数据了。

* shuffle fetch：
  
  之后就是reduce的任务开始执行了。在Hadoop中，reduce任务会到各个map任务那里将自己要处理的任务拉到本地，然后对数据进行归并和排序，使得相同key的不同value按序归并到一起。
  
  在Spark中，随上面的逻辑进行了一些优化。首先，Spark认为，在大多数的应用场景中，排序操作都不是必须的，比如词频统计时，强制排序反而会使得性能变差。因此，Spark在reduce端不做归并和排序，而是用一个Aggregator机制（本质上是Hash Map），从map端拉取数据，更新到这个hashmap中，如果数据不存在，插入，如果存在，更新。这样避免了对所有数据进行归并排序，而是来一个处理一个。
