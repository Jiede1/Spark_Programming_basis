object Test2{
	def main(args:Array[String]):Unit={
		var a:Int = args(0)
		var b:Int = args(1)
		println("a+b"+add(a,b))
	}
	def add(a:Int,b:Int):Int={
		var sum:Int=1
		sum = a+b
		return sum
	}
}
