package com.young.db.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 查询角色和权限的关系
 * @Author: yqz
 * @CreateDate: 2020/11/20 11:19
 */
@Data
public class RolePermissionPojo implements Serializable {

    @ApiModelProperty("父级")
    private String id;
    @ApiModelProperty("父级标签")
    private String label;
    @ApiModelProperty("第一级children")
    private List<RolePermissionChildren> children;

}
