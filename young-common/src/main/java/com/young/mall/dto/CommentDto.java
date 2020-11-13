package com.young.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 评论
 * @Author: yqz
 * @CreateDate: 2020/11/13 21:58
 */
@Data
public class CommentDto implements Serializable {

    @NotNull(message = "评论ID不能为空")
    @ApiModelProperty("评论ID")
    private Integer commentId;

    @NotEmpty(message = "评论内容不能为空")
    @ApiModelProperty("评论内容")
    private String content;
}
