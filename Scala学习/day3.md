* 运算符  
+ 符号是一个运算符，并且是一个中缀运算符。 在 Scala 中你可以定义任何方法为一操作符。 比如 String 的 IndexOf   方法也可以使用操作符的语法来书写。 例如：  
```
scala> val s ="Hello, World"  
s: String = Hello, World  
  
scala> s indexOf 'o'  
res0: Int = 4  
```
eq  引用相等  
==  如果非null 同equals 否则eq  
equals 	值相等  
  
* 类  

跟Java不一样 在定义的时候可以写入类参数  
```
class Rational (n:Int, d:Int) {}  

scala> class Rational (n:Int, d:Int) {
     |    require(d!=0)   //可利用其限定d的值
     |    override def toString = n + "/" +d
     | }
defined class Rational

scala> new Rational(5,0)
java.lang.IllegalArgumentException: requirement failed
  at scala.Predef$.require(Predef.scala:211)
  ... 33 elided
```

类参数不是类的成员变量  
```
class Rational (n:Int, d:Int) {
   require(d!=0)
   val number =n
   val denom =d 
   override def toString = number + "/" +denom 
   def add(that:Rational)  =
     new Rational(
       number * that.denom + that.number* denom,
       denom * that.denom
     )
}
```
  
自身引用this  
```
def lessThan(that:Rational) =
   this.number * that.denom < that.number * this.denom

def lessThan(that:Rational) =
   this.number * that.denom < that.number * this.denom
```

私用成员  
```
class Rational (n:Int, d:Int) {
    require(d!=0)
    private val g =gcd (n.abs,d.abs) 
    val number =n/g 
    val denom =d/g 
    override def toString = number + "/" +denom
    def add(that:Rational)  = 
      new Rational( 
        number * that.denom + that.number* denom,
        denom * that.denom 
      ) 
    def this(n:Int) = this(n,1) 
    private def gcd(a:Int,b:Int):Int =
      if(b==0) a else gcd(b, a % b)
}
```

定义运算符  
```
class Rational (n:Int, d:Int) {
   require(d!=0)
   private val g =gcd (n.abs,d.abs) 
   val numer =n/g 
   val denom =d/g 
   override def toString = numer + "/" +denom
   def +(that:Rational)  =
     new Rational( 
       numer * that.denom + that.numer* denom,
       denom * that.denom 
     ) 
   def * (that:Rational) =
     new Rational( numer * that.numer, denom * that.denom)
   def this(n:Int) = this(n,1) 
   private def gcd(a:Int,b:Int):Int =
     if(b==0) a else gcd(b, a % b)
}
```

隐式类型转换  
```
//将int变成Rational对象，这样就支持Int + Rational了  
implicit def intToRational(x:Int) = new Rational(x)
```

* 异常  
```
try{
	...
}catch{
	...
}finally{
	...
}
```



