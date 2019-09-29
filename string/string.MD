### String 对象的实现

### String 对象的创建方式
#### 1、通过字符串常量的方式
``String str= "pingtouge";``的形式，这种形式在创建时 JVM 会先检查在字符串常量池中是否存在该对象，如果存在，返回该对象的引用地址，如果不存在，则在字符串常量池中创建该字符串对象。
这种方式创建的好处是：避免了相同值的字符串重复创建，节约了内存

#### new 的方式创建
``String str = new String("pingtouge")`` 的形式，这种形式的创建过程就比较复杂了，首先在文件类编译时，字符串"pingtouge"会被加入到常量结构中，
在类加载时，字符串"pingtouge"就会在常量池中创建，然后，在调用new()时，JVM 命令将会调用 String 的构造函数，同时引用常量池中的"pingtouge"字符串，
在堆内存中创建一个 String 对象。str得到的是堆中"pingtouge"字符串的引用

知道了String 对象两种创建方式之后，我们来下面这段代码，使用两种方式创建"pingtouge"字符串，试着分析下这两行代码在 JVM 中的运行过程。
```java
      String str = "pingtouge";
      String str1 = new String("pingtouge");
```
首先``String str = "pingtouge";``使用的是字符串常量的方式创建字符串，这时候JVM会去常量池中查找是否存在该字符串，答案是肯定没有的，JVM将会在常量池中创建该字符串对象。这行代码就运行完了
然后就是``String str1 = new String("pingtouge");``这行代码的执行，我们已经知道了通过`new`的方式创建字符串时，JVM先会在常量池中创建字符串，在常量池中创建"pingtouge"字符串时，
由于第一行代码已经在常量池中创建了字符串"pingtouge"，所以这里将不会创建对象。然后就是调用`new`的时候会在堆中创建一个字符串"pingtouge"。这就是上面两行代码的创建过程，问个问题``str ==str1``吗？
答案是肯定不相等的，因为str指向的是常量池地址引用，str2指向的是堆中的地址引用


### String 对象的不可变性
我们都知道String 对象是不可变的，你知道是为什么吗？先来看看 String 对象的部分源码
```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    /** The value is used for character storage. */
    private final char value[];

    /** Cache the hash code for the string */
    private int hash; // Default to 0

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -6849794470754667710L;
    }
```
从这一小段源码中我们可以看出`String`类被final修饰，这表明`String`类不能被继承，value[]也被private final修饰，这表明了`String`类不可变更。这就形成了String 对象是不可变的特性，这样做有什么好处呢？

第一，保证 String 对象的安全性。假设 String 对象是可变的，那么 String 对象将可能被恶意修改。

第二，保证 hash 属性值不会频繁变更，确保了唯一性，使得类似 HashMap 容器才能实现相应的 key-value 缓存功能。

第三，可以实现字符串常量池

### String 对象的优化
#### 构建超大字符串
在项目开发中，字符串拼接是比较常见的，由于String 对象是不可变的，所以在潜意识里,我们不能使用`+`号进行字符串拼接，因为它会产生多个对象，真的是这样吗？我们来看看下面这一段代码
```java
String str8 = "ping" +"tou"+"ge";
```     
这段代码会产生多少个对象呢？我们一起来分析一下：首先会创建"ping"对象，然后创建"pingtou"对象，最后创建"pingtouge"对象，一共创建了三个对象。实际运行时是这样吗？
查看编译后的class之后，你会发现编译器对它进行了优化，编译之后直接优化成了``String str8 = "pingtouge";``对象。这是我们使用`+`号拼接常量对象，那使用`+`号动态拼接字符串可行吗？我们来看一下下面这段代码
```java
String str = "pingtouge";

for(int i=0; i<1000; i++) {
      str = str + i;
}

```
这段代码编译器同样会对他进行优化，优化成下面这段代码
```java

String str = "pingtouge";

for(int i=0; i<1000; i++) {
        	  str = (new StringBuilder(String.valueOf(str))).append(i).toString();
}

```
所以即使使用 + 号作为字符串的拼接，也一样可以被编译器优化成 StringBuilder 的方式。其实在编译器优化的代码中，你会发现每次循环都会生成一个新的 StringBuilder 实例，同样也会降低系统的性能。
所以平时做字符串拼接的时候，我们还是要显示地使用 String Builder 来提升系统性能。在多线程涉及到拼接安全的情况下，我们可以使用StringBuffer。
#### 巧妙的使用 intern() 方法
```java
     * <p>
     * When the intern method is invoked, if the pool already contains a
     * string equal to this {@code String} object as determined by
     * the {@link #equals(Object)} method, then the string from the pool is
     * returned. Otherwise, this {@code String} object is added to the
     * pool and a reference to this {@code String} object is returned.
     * <p>
     public native String intern();
```
这是intern()的定义，大概意思就是intern用来返回常量池中的某字符串，如果常量池中已经存在该字符串，则直接返回常量池中该对象的引用。否则，在常量池中加入该对象，然后 返回引用。

#### 字符串的分割方法

最后我想跟你聊聊字符串的分割，这种方法在编码中也很最常见。Split() 方法使用了正则表达式实现了其强大的分割功能，而正则表达式的性能是非常不稳定的，使用不恰当会引起回溯问题，很可能导致 CPU 居高不下。
所以我们应该慎重使用 Split() 方法，我们可以用 String.indexOf() 方法代替 Split() 方法完成字符串的分割。如果实在无法满足需求，你就在使用 Split() 方法时，对回溯问题加以重视就可以了。     
     
