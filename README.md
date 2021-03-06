
<img src="https://www.bitrise.io/app/78ecb43a6a397d40/status.svg?token=2_tc-1dj7Rrn02Xbh9v7gg&branch=master">

# RssRecipr

# Kotlin Key Notes

Java
```
listOfGames.stream().filter(x -> x.endsWith(“0”))
 .sorted((x,y) -> x.compareTo(y))
 .map(x -> x.toUpperCase())
 .forEach(x -> System.out.println(x));
 ```
 
Kotlin
```
 @Test
    fun testKotlinCollectionsSequences(){
        val listOfGames = mutableListOf<String>()
        for(i in 1..100){
            listOfGames.add("Starcraft$i")
        }

        val listOfUpdatedGames =
                listOfGames
                        .asSequence()
                        .filter { it.endsWith("0") }
                        .take(5)
                        .toList()

        for(game in listOfUpdatedGames){
            println(game)
        }
    }
```

Static variables or methods

```
  companion object {
        private val EXTRA_FRAGMENT: String = "extraFragment"
        private val EXTRA_TITLE_RES: String = "extraTitleRes"
  }
```

Safe calls
```
getActivity()?.getContext()
```

Elvis operator
```
val length = b?.length ?: -1

```

The !! Operator (pretty much the only way to assure an NPE will be thrown)
```
val l = b!!.length
```

Safe casts
```
val aInt: Int? = a as? Int
```


# Coroutines 

RxJava Approach
```
fun initializeObjectsAsync(): Completable {
    return Completable.fromCallable({
            heavyInitialization()
    })
}

```
How do we consume this method in RxJava ?
```
fun initializeObjects() {
    initializeObjectsAsync()
        .subscribeOn(Schedulers.computation()) 
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            // Do whatever UI related changes
        }, {
            // Something went wrong 
        })
}
```

Kotlin Coroutines Approach
```
fun initializeObjects() {
    launch(CommonPool) {
        heavyInitialization()
    }
}
```
In the example above you are just doing something inside a pool thread, we don't know when the execution has finished.
CommonPool is a CoroutineDispatcher and is similar to RxJava computation which will spawn as many threads as cores are available on device minus one. (4 cores devices -> maximum 3 parallel executions on CommonPool)

```
fun initializeObjects() {
    launch(CommonPool) {
        try {
            heavyInitialization()
            // The initialization succeeded!
            withContext(UI) {
                // We can perform UI changes here
            }
        } catch (e: Exception) {
            // An Error occurred!
        }
    }
}
``` 
Since the code inside a Coroutine is executed sequentially (also known as suspending lambda), the line after heavyInitialization() will be executed after has finished. Basically withContext(UI) will be invoked after this line of code is finished. 

withContext(UI) is basically the equivalent of subscribeOn(AndroidSchedulers.mainThread())


How about returning something ?

Given the following
``` 
private fun fib(n: Long): Long {        
    return if (n <= 1) n        
    else fib(n - 1) + fib(n - 2)    
}
``` 

RxJava approach

``` 
fun fibonacciAsync(number: Long): Single<Long> = 
    Single.create({ emitter ->
            val result = fib(number) 
            if (emitter != null && !emitter.isDisposed) {       
                 emitter.onSuccess(result)
            }
})

@OnClick(R.id.generateFibo)
fun onButtonClicked() { 
    fibonacciAsync(numberInputEditText.text.toString().toLong())
       .subscribeOn(Schedulers.computation())
       .observeOn(AndroidSchedulers.mainThread())
       .subscribe({ fibonacciNumber -> 
           //Update UI with the result 
           result.text = fibonacciNumber
       },{
           // Error happened
       })
}
``` 

Kotlin approach

``` 
@OnClick(R.id.generateFibo)
fun onButtonClicked() { 
    launch(CommonPool) {
        val result = fib(
            numberInputEditText.text.toString().toLong()
        )
        withContext(UI) {
            result.text = result
        }
    }
}
``` 
 
 Cancelling Coroutines 
 ``` 
 val job = launch(CommonPool) {
    for (i in 1..5) {
        if (!isActive) { break }
        heavyComputation()
    }
}
// will cancel the job
job.cancel()
``` 


