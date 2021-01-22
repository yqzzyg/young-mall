package com.young.mall.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Description: Minio Bucket访问策略配置
 * @Author: yqz
 * @CreateDate: 2020/11/6 21:21
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class BucketPolicyConfigDto {
    private String Version;
    private List<Statement> Statement;

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Builder
    public static class Statement {
        private String Effect;
        private String Principal;
        private String Action;
        private String Resource;

    }
}
