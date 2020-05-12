3
https://raw.githubusercontent.com/harry-xqb/rent-house/master/src/main/java/com/harry/renthouse/service/ServiceMultiResult.java
package com.harry.renthouse.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *  服务端统一列表返回格式
 * @author Harry Xu
 * @date 2020/5/8 17:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceMultiResult<T> {

    private Integer total;

    private List<T> list;

    public int getResultSize(){
        return Optional.ofNullable(list).orElse(Collections.emptyList()).size();
    }
}
