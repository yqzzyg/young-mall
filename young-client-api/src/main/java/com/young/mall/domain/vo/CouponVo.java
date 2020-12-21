package com.young.mall.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/11 17:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponVo implements Serializable {

    private Integer id;
    private String name;
    private String desc;
    private String tag;
    private String min;
    private String discount;
    private LocalDate startTime;
    private LocalDate endTime;

}
