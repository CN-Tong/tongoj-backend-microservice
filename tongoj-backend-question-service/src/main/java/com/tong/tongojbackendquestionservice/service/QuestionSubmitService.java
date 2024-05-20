package com.tong.tongojbackendquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tong.tongojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.tong.tongojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.tong.tongojbackendmodel.model.entity.QuestionSubmit;
import com.tong.tongojbackendmodel.model.entity.User;
import com.tong.tongojbackendmodel.model.vo.QuestionSubmitVO;

/**
* @author Tong
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-05-08 15:54:39
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
