package com.young.db.pojo;

import lombok.Data;
import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/20 12:57
 */
@Data
public class RolePermissionChildren {
    private String id;
    private String label;
    private List<RolePermissionChildrenLabel> children;
}
