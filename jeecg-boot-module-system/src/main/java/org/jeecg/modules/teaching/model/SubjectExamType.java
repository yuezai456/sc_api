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
 * @since 2023-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SubjectExamType对象", description="")
public class SubjectExamType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Integer id;

    private Integer subjectId;

    private String examType;

    private String previousExamname;


    private Integer examTime;

    private String programType;
    private String levelType;

    private String date;

    @TableLogic
    private Integer isDeleted;


}
