package org.jeecg.modules.teaching.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Fitz
 * @since 2023-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SubjectLevelList对象", description="")
public class SubjectLevelList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;
    private String questionId;
    @TableField("previous_examTime")
    private String previousExamtime;

    @TableField("previous_examName")
    private String previousExamname;

    private String createUser;

    private String createTime;

    @TableLogic
    private String isDeleted;

    private String updateUser;

    private String updateTime;

    private String version;

    private String type;

    private String title;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String optionE;

    private String answer;

    private String userAnswer;

    private String analyzeContent;

    private String analyzeVideo;

    private String subjectId;

    private String examDots;

    private String sprintId;

    private String subjectNumber;

    private String images;

    private String weType;

    private String wxAnswerType;

    private String isMySubject;

    private String isDelete;


}
