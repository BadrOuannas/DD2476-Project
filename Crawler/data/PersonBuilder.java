2
https://raw.githubusercontent.com/liuminchao123/JavaWeb_Learning/master/02.%20java/Java/%E9%BB%91%E9%A9%AC%E6%95%99%E7%A8%8B/24.%E3%80%90Stream%E6%B5%81%E3%80%81%E6%96%B9%E6%B3%95%E5%BC%95%E7%94%A8%E3%80%91%E7%AC%94%E8%AE%B0/code/13_StreamAndMethodReference/src/com/itheima/demo09/ConstructorMethodReference/PersonBuilder.java
package com.itheima.demo09.ConstructorMethodReference;
/*
    定义一个创建Person对象的函数式接口
 */
@FunctionalInterface
public interface PersonBuilder {
    //定义一个方法,根据传递的姓名,创建Person对象返回
    Person builderPerson(String name);
}