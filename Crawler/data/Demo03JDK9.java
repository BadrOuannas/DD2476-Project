2
https://raw.githubusercontent.com/liuminchao123/JavaWeb_Learning/master/02.%20java/Java/%E9%BB%91%E9%A9%AC%E6%95%99%E7%A8%8B/16.%E3%80%90%E5%BC%82%E5%B8%B8%E3%80%81%E7%BA%BF%E7%A8%8B%E3%80%91-%E7%AC%94%E8%AE%B0/20.%E3%80%90%E5%AD%97%E8%8A%82%E6%B5%81%E3%80%81%E5%AD%97%E7%AC%A6%E6%B5%81%E3%80%91-%E7%AC%94%E8%AE%B0/code/09_IOAndProperties/src/com/itheima/demo06/trycatch/Demo03JDK9.java
package com.itheima.demo06.trycatch;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
    JDK9新特性
    try的前边可以定义流对象
    在try后边的()中可以直接引入流对象的名称(变量名)
    在try代码执行完毕之后,流对象也可以释放掉,不用写finally
    格式:
        A a = new A();
        B b = new B();
        try(a,b){
            可能会产出异常的代码
        }catch(异常类变量 变量名){
            异常的处理逻辑
        }
 */
public class Demo03JDK9 {
    public static void main(String[] args) throws IOException {
        //1.创建一个字节输入流对象,构造方法中绑定要读取的数据源
        FileInputStream fis = new FileInputStream("c:\\1.jpg");
        //2.创建一个字节输出流对象,构造方法中绑定要写入的目的地
        FileOutputStream fos = new FileOutputStream("d:\\1.jpg");

        try(fis;fos){
            //一次读取一个字节写入一个字节的方式
            //3.使用字节输入流对象中的方法read读取文件
            int len = 0;
            while((len = fis.read())!=-1){
                //4.使用字节输出流中的方法write,把读取到的字节写入到目的地的文件中
                fos.write(len);
            }
        }catch (IOException e){
            System.out.println(e);
        }

        //fos.write(1);//Stream Closed

    }
}