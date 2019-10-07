* scala函数式编程  

1. 函数的定义和使用  

首先看方法定义：def 方法名(参数列表)：结果类型={方法体}  
函数定义： 在Scala中，函数被定义为变量，遵从变量的定义，即是 val/var 函数名：函数类型={函数体}  

```
scala> val add:(Int,Int)=>Int=(a:Int,b:Int)=>a+b  
add: (Int, Int) => Int = <function2>  

scala> add(1,2)  
res153: Int = 3  

//类型自动推断机制  
scala> val add=(a:Int,b:Int)=>a+b  
add: (Int, Int) => Int = <function2>  

scala> add(1,2)  
res154: Int = 3  

```

2. 高阶函数  
将函数当作函数参数使用  

3. 闭包  
函数使用外部变量，这样的函数称为闭包。在函数内部对外部变量的改动，会真正影响到外部变量，外部变量在外部改动，闭包也能捕获到。  

4.偏应用函数  
```
scala> def sum(a:Int,b:Int,c:Int)=a+b+c
sum: (a: Int, b: Int, c: Int)Int

scala> sum(1,2,3)
res155: Int = 6

scala> val ad=sum(1,_:Int,_:Int)
ad: (Int, Int) => Int = <function2>

scala> ad(1,2)
res157: Int = 4

```
上面的ad称为偏应用函数  

4. Curry化  
curry化的函数是指那种含有多个参数列表且每个参数列表只包含一个参数的函数。  
```
scala> val multi(factor:Int)(x:Int)=factor*x
<console>:1: error: '=' expected but '(' found.
val multi(factor:Int)(x:Int)=factor*x
                     ^

scala> def multi(factor:Int)(x:Int)=factor*x
multi: (factor: Int)(x: Int)Int

scala> multi(4)(5)
res159: Int = 20

scala> multi(4,5)
<console>:26: error: too many arguments for method multi: (factor: Int)(x: Int)Int
       multi(4,5)
            ^
# 保留multi第二个参数的偏应用函数，第一个参数固定为2
scala> val bytwo=multi(2)_
bytwo: Int => Int = <function1>

scala> bytwo(3)
res161: Int = 6
```

下面是curry过程展示  

```
scala> def plainMulti(x:Int,y:Int)=x*y
plainMulti: (x: Int, y: Int)Int

scala> val curriedMulti=(plainMulti _).curried
curriedMulti: Int => (Int => Int) = <function1>

scala> curr
current_date   current_timestamp   curriedMulti

scala> curriedMulti(3)(3)
res162: Int = 9

scala> curriedMulti(3,3)
<console>:28: error: too many arguments for method apply: (v1: Int)Int => Int in trait Function1
       curriedMulti(3,3)

```

5. 针对容器的操作  

* foreach  
```
scala> val uni=Map("xmu"->"dads","da"->"dweew")
uni: scala.collection.immutable.Map[String,String] = Map(xmu -> dads, da -> dweew)

scala> uni.foreach(kv=>println(kv._1+":"+kv._2))
xmu:dads
da:dweew
```

* map / faltMap  
map针对单个元素，返回一个与原容器类型大小都相同的新容器  
flatMap方法稍有不同，它将某个函数应用到容器中的元素时，对每个元素会返回一个容器，然后，会对这生成的多个容器进行拍扁形成一个单一容器返回  
```
scala> val books=List("x","e","r")
books: List[String] = List(x, e, r)

scala> books.map(s=>s.toUpperCase)
res165: List[String] = List(X, E, R)

scala> books flatMap (s=>s++"rt")
res166: List[Char] = List(x, r, t, e, r, t, r, r, t)
```

* filter  
过滤操作  
```
scala> val books=List("ddx","see","dfrr")
books: List[String] = List(ddx, see, dfrr)

scala> books filter (x=>x.length>3)
res173: List[String] = List(dfrr)

```
* reduce / fold  
规约操作  
reduce 对容器中元素进行两两计算，将其规约成一个值。  
fold类似reduce 但接受初始值输入。也正是这个特性，使得fold可以允许返回一个新的容器  

reduceLeft  从左开始规约  
reduceRight  从右开始
```
scala> val list=List(1,2,3,4)
list: List[Int] = List(1, 2, 3, 4)

scala> list reduce (_*_)
res178: Int = 24

scala> list reduceLeft (_-_)
res184: Int = -8

scala> val s = list.map(_.toString)
s: List[String] = List(1, 2, 3, 4)

scala> s reduceLeft ((accu,x)=>s"($accu-$x)")
res186: String = (((1-2)-3)-4)

scala> list reduceRight (_-_)
res185: Int = -2

scala> s reduceRight ((x,accu)=>s"($x-$accu)")
res188: String = (1-(2-(3-4)))
                
scala> (list fold 10) (_*_)
res180: Int = 240

// y一开始为空List，然后从右往左进行连接
scala> (list foldRight List.empty[Int]) {(x,y)=>x*2::y}
res182: List[Int] = List(2, 4, 6, 8)

```

* 拆分操作  
partition 传入布尔函数，分为两个分区  
groupBy  利用函数划分，相同函数返回的放在一起  
grouped  从左到右分区，传入长度  
sliding  滑动分区 输入分区长度  

```
scala> val xs=List(1,2,3,4,5)
xs: List[Int] = List(1, 2, 3, 4, 5)

scala> xs.group
groupBy   grouped

scala> xs.groupBy(x=>x%2)
res189: scala.collection.immutable.Map[Int,List[Int]] = Map(1 -> List(1, 3, 5), 0 -> List(2, 4))

scala> xs.par
par   partition

scala> xs.partition(_<3)
res190: (List[Int], List[Int]) = (List(1, 2),List(3, 4, 5))

scala> xs.group
groupBy   grouped

scala> xs.grouped(3)
res191: Iterator[List[Int]] = non-empty iterator

scala> val ges=xs.grouped(3)
ges: Iterator[List[Int]] = non-empty iterator

scala> ges.hasNext
res193: Boolean = true

scala> ges.next
res194: List[Int] = List(1, 2, 3)

scala> ges.next
res196: List[Int] = List(4, 5)

scala> ges.next
java.util.NoSuchElementException: next on empty iterator
  at scala.collection.Iterator$GroupedIterator.next(Iterator.scala:1138)
  at scala.collection.Iterator$GroupedIterator.next(Iterator.scala:1019)
  at scala.collection.Iterator$$anon$11.next(Iterator.scala:409)
  ... 48 elided

scala> val sl=xs.sli
slice   sliding

scala> val sl=xs.sliding(3)
sl: Iterator[List[Int]] = non-empty iterator

scala> sl.next
res198: List[Int] = List(1, 2, 3)

```




