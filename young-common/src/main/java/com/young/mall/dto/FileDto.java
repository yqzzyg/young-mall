package com.young.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/5 16:49
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

    private String url;
}
