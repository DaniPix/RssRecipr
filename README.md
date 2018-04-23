# RssRecipr


# Kotlin Key Notes

Java
```public int sum(int a, int b){
 return a+b;
}```

Kotlin
```fun sum(a: Int, b: Int): Int {
 return a + b
}```

Java
```final int a = 2;
final Integer b = 2;
final int c;
c = 3;
//Mutable variable
int x = 5;
x+=1;```

Kotlin
```val a: Int = 1 // immediate assignment
val b = 2 // `Int` type is inferred
val c: Int // Type required when no initializer is provided
c = 3 // deferred assignment
//Mutable variable
var x = 5 // `Int` type is inferred
x += 1```

Java
```class Article {
 String title;
 public Article(String title){
  this.title = title
 }
}```

Kotlin

```class Article constructor(title: String) { }```



