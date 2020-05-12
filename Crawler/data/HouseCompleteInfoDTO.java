3
https://raw.githubusercontent.com/harry-xqb/rent-house/master/src/main/java/com/harry/renthouse/web/dto/HouseCompleteInfoDTO.java
package com.harry.renthouse.web.dto;

import lombok.Data;

/**
 * @author Harry Xu
 * @date 2020/5/12 16:46
 */
@Data
public class HouseCompleteInfoDTO {

    private HouseDTO house;

    private SupportAddressDTO city;

    private SupportAddressDTO region;

    private SubwayDTO subway;

    private SubwayStationDTO subwayStation;

}
