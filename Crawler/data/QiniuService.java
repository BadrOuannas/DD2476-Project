3
https://raw.githubusercontent.com/harry-xqb/rent-house/master/src/main/java/com/harry/renthouse/service/house/QiniuService.java
package com.harry.renthouse.service.house;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.File;
import java.io.InputStream;

/**
 * 七牛云service
 * @author Harry Xu
 * @date 2020/5/9 17:39
 */
public interface QiniuService {

    Response uploadFile(File file) throws QiniuException;

    Response uploadFile(InputStream inputStream) throws QiniuException;

    Response deleteFile(String key) throws QiniuException;
}
