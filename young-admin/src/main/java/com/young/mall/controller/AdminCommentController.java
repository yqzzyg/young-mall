package com.young.mall.controller;

import cn.hutool.json.JSONUtil;
import com.young.db.entity.YoungComment;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.dto.CommentDto;
import com.young.mall.service.MallCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 评论管理
 * @Author: yqz
 * @CreateDate: 2020/11/13 21:27
 */
@Api(tags = "CommentController", description = "评论管理")
@RestController
@RequestMapping("/admin/comment")
public class AdminCommentController extends BaseController {

    @Autowired
    private MallCommentService youngCommentService;

    @ApiOperation("分页查询评论")
    @GetMapping("/list")
    public ResBean list(String userId, String valueId,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size,
                        @RequestParam(defaultValue = "add_time") String sort,
                        @RequestParam(defaultValue = "desc") String order) {
        logger.info("userId:{},valueId:{},page:{},size:{},sort:{},order:{}", userId, valueId, page, size, sort, order);

        Optional<List<YoungComment>> optionalList = youngCommentService.querySelective(userId, valueId, page, size, sort, order);
        if (!optionalList.isPresent()) {
            return ResBean.failed("查询失败");
        }
        List<YoungComment> commentList = optionalList.get();
        CommonPage<YoungComment> restPage = CommonPage.restPage(commentList);
        return ResBean.success(restPage);
    }

    @ApiOperation("回复订单评论")
    @PostMapping("/reply")
    public ResBean reply(@Valid @RequestBody CommentDto commentDto) {
        logger.info("回复评论入参：{}", JSONUtil.toJsonStr(commentDto));
        ResBean reply = youngCommentService.reply(commentDto);
        logger.info("回复评论出参：{}", JSONUtil.toJsonStr(reply));
        return reply;
    }

    @ApiOperation("删除评论")
    @PostMapping("/delete")
    public ResBean delete(@RequestBody YoungComment youngComment) {
        logger.info("删除评论入参：{}", JSONUtil.toJsonStr(youngComment));
        Integer id = youngComment.getId();
        if (id == null) {
            return ResBean.failed("删除失败，参数错误");
        }

        Integer count = youngCommentService.delete(id);
        return ResBean.success(count);
    }
}
