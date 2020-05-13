3
https://raw.githubusercontent.com/harry-xqb/rent-house/master/src/main/java/com/harry/renthouse/web/form/HouseForm.java
package com.harry.renthouse.web.form;

import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.List;

/**
 * 房屋表单
 * @author Harry Xu
 * @date 2020/5/9 14:45
 */
@Data
public class HouseForm {

    public interface Edit  extends Default {};

    @NotNull(groups = {Edit.class}, message = "房屋id不能为空")
    private Long id;

    @NotBlank(message = "房源标题不能为空")
    private String title;

    @NotNull(message = "房源价格不能为空")
    private Integer price;

    @NotNull(message = "房源面积不能为空")
    private Integer area;

    @NotNull(message = "房间数量不能为空")
    private Integer room;

    @NotNull(message = "房源楼层不能为空")
    private Integer floor;

    @NotNull(message = "总楼层不能为空")
    private Integer totalFloor;

    @NotNull(message = "房源年限不能为空")
    private Integer buildYear;

    @NotNull(message = "房源城市不能为空")
    private String cityEnName;

    @NotNull(message = "房源区县不能为空")
    private String regionEnName;

    private String cover;

    @NotNull(message = "房屋朝向不能为空")
    private Integer direction;

    /* 到地铁的距离 */
    @NotNull(message = "房源到地铁距离不能为空")
    private Integer distanceToSubway;

    /** 客厅数量 **/
    @NotNull(message = "房源客厅数量不能为空")
    @Min(value = 0, message = "客厅数量非法")
    private Integer parlour;

    /* 小区名称 */
    @NotNull(message = "房源小区名称不能为空")
    private String district;

    @NotNull(message = "房源卫生间数量不能为空")
    @Min(value = 0, message = "卫生间数量非法")
    private Integer bathroom;

    /* 街道 */
    @NotNull(message = "房源街道名称不能为空")
    private String street;

    /************* 房屋详情 *******************/
    private String description;

    /* 户型介绍 */
    private String layoutDesc;

    /* 交通出行介绍 */
    private String traffic;

    /* 周边配套设施 */
    private String roundService;

    /* 出租方式, 1:整租  2: 合租 */
    @NotNull(message = "出租方式不能为空")
    private Integer rentWay;

    @NotNull(message = "房源地址不能为空")
    private String address;

    /* 地铁线路id */
    private Long subwayLineId;

    /* 地铁线路名称 */
    private String subwayLineName;

    private Long subwayStationId;

    private String subwayStationName;

    /************ 房屋标签 *****************/
    private List<String> tags;

    /*房屋照片*/
    private List<PictureForm> pictures;

}
