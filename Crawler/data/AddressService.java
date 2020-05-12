3
https://raw.githubusercontent.com/harry-xqb/rent-house/master/src/main/java/com/harry/renthouse/service/house/AddressService.java
package com.harry.renthouse.service.house;

import com.harry.renthouse.entity.SupportAddress;
import com.harry.renthouse.web.dto.SubwayDTO;
import com.harry.renthouse.web.dto.SubwayStationDTO;
import com.harry.renthouse.web.dto.SupportAddressDTO;
import com.harry.renthouse.service.ServiceMultiResult;

import java.util.List;
import java.util.Map;

/**
 *  支持的地区service
 * @author Harry Xu
 * @date 2020/5/8 17:10
 */
public interface AddressService {

    /**
     * 查找所有城市
     * @return 行政单位列表
     */
    ServiceMultiResult<SupportAddressDTO> findAllCities();

    /**
     *  通过所属单位与当前层级查找区域列表
     * @param belongTo 所属单位
     * @param level 行政单位级别
     * @return  行政单位列表
     */
    ServiceMultiResult<SupportAddressDTO> findAreaByBelongToAndLevel(String belongTo, String level);

    /**
     * 查找所有给定的enName地址
     * @param enNameList 城市/区域 enName
     * @return 行政单位列表
     */
    ServiceMultiResult<SupportAddressDTO> findAreaInEnName(List<String> enNameList);

    /**
     * 通过城市英文名获取所有地铁线路
     * @param cityEnName 城市英文名
     * @return 地铁线路列表
     */
    ServiceMultiResult<SubwayDTO> findAllSubwayByCityEnName(String cityEnName);

    /**
     * 通过地铁线路id查找地铁站
     * @param subwayId 地铁站id
     * @return 地铁站列表
     */
    ServiceMultiResult<SubwayStationDTO> findAllSubwayStationBySubwayId(Long subwayId);

    /**
     * 获取城市和区县
     * @param cityEnName 城市英文简称
     * @param regionEnName 区县英文简称
     */
    Map<SupportAddress.AddressLevel, SupportAddressDTO> findCityAndRegion(String cityEnName, String regionEnName);

    /**
     * 获取地铁站
     * @param subwayStationId 地铁站id
     */
    SubwayStationDTO findSubwayStation(Long subwayStationId);

    /**
     * 获取地铁线路信息
     * @param subwayId 地铁线路id
     */
    SubwayDTO findSubway(Long subwayId);
}
