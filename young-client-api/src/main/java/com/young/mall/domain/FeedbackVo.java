package com.young.mall.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Description: 意见反馈
 * @Author: yqz
 * @CreateDate: 2020/12/18 14:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackVo {

    @ApiModelProperty("反馈人手机号")
    @NotEmpty(message = "反馈人手机号不能为空")
    private String mobile;

    @ApiModelProperty("反馈类型")
    @NotEmpty(message = "反馈类型不能为空")
    private String feedType;

    @ApiModelProperty("反馈内容")
    @NotEmpty(message = "反馈内容不能为空")
    private String content;

    @ApiModelProperty("是否有图片")
    private Boolean hasPicture;

    @ApiModelProperty("图片集合")
    private List<String> picUrls;
}
