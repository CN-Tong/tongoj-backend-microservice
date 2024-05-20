package com.tong.tongojbackendmodel.model.vo;

import cn.hutool.json.JSONUtil;
import com.tong.tongojbackendmodel.model.dto.question.JudgeConfig;
import com.tong.tongojbackendmodel.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目视图
 * @author Tong
 */
@Data
public class QuestionVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 判题配置（json 对象）
     */
    private JudgeConfig judgeConfig;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 题目创建人信息
     */
    private UserVO userVO;

    /**
     * 包装类转对象
     *
     * @param questionVO
     * @return
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        // tags要从List<String>转成String
        List<String> tagList = questionVO.getTags();
        if(tagList != null){
            question.setTags(JSONUtil.toJsonStr(tagList));
        }
        // judgeConfig要从JudgeConfig转成String
        JudgeConfig judgeConfigVO = questionVO.getJudgeConfig();
        if(judgeConfigVO != null){
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfigVO));
        }
        return question;
    }

    /**
     * 对象转包装类
     *
     * @param question
     * @return
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        // tags要从String转成List<String>
        String tags = question.getTags();
        if(tags != null){
            questionVO.setTags(JSONUtil.toList(tags, String.class));
        }
        // judgeConfig要从String转成JudgeConfig
        String judgeConfig = question.getJudgeConfig();
        if(judgeConfig != null){
            questionVO.setJudgeConfig(JSONUtil.toBean(judgeConfig, JudgeConfig.class));
        }
        return questionVO;
    }
}
