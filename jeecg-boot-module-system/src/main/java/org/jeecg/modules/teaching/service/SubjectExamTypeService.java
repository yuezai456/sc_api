package org.jeecg.modules.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.teaching.model.SubjectExamType;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fitz
 * @since 2023-03-16
 */
public interface SubjectExamTypeService extends IService<SubjectExamType> {


    String searchExam(String subjectId, String token);

}
