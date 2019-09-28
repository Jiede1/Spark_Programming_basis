* List与Array  

List元素不可下标取 可遍历；Array可变可遍历  
Array：  
可变  
用下标访问，利于随机访问  
是Java数组的一种表示  
------------  
List：  
不可变  
为后进先出做了优化，像栈一样的访问模式  
支持用::在模式匹配中取出head和tail  
```  
 // 字符串列表  
val site = "Runoob" :: ("Google" :: ("Baidu" :: Nil))  
  
// 整型列表  
val nums = 1 :: (2 :: (3 :: (4 :: Nil)))  
  
// 空列表  
val empty = Nil  
  
// 二维列表  
val dim = (1 :: (0 :: (0 :: Nil))) ::  
          (0 :: (1 :: (0 :: Nil))) ::  
          (0 :: (0 :: (1 :: Nil))) :: Nil  

::   队列追加  
:::  连接队列  
:+   尾部追加  
```
----------    

* 元组  
```  
val pair=(99,"Luftballons")  
println(pair._1)  
println(pair._2)  
```
一旦定义了一个元组，可以使用 ._ 和 索引 来访问元组的元素（矢量的分量，注意和数组不同的是，元组的索引从1开始）。  


* Sets Map  
  

