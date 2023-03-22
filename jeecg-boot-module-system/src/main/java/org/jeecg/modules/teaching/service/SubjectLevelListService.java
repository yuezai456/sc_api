package org.jeecg.modules.teaching.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.teaching.model.SubjectLevelList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fitz
 * @since 2023-03-15
 */
public interface SubjectLevelListService extends IService<SubjectLevelList> {

    String getExamList(String id, String type, String token, int index);
}
