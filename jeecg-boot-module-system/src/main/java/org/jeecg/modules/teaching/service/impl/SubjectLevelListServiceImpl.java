package org.jeecg.modules.teaching.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.jeecg.modules.teaching.mapper.SubjectLevelListMapper;
import org.jeecg.modules.teaching.model.SubjectExamType;
import org.jeecg.modules.teaching.model.SubjectLevelList;
import org.jeecg.modules.teaching.service.SubjectExamTypeService;
import org.jeecg.modules.teaching.service.SubjectLevelListService;
import org.jeecg.modules.teaching.utils.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Fitz
 * @since 2023-03-15
 */
@Service
public class SubjectLevelListServiceImpl extends ServiceImpl<SubjectLevelListMapper, SubjectLevelList> implements SubjectLevelListService {
    @Autowired
    SubjectExamTypeService subjectExamTypeService;
    @Override
    public String getExamList(String id, String type, String token, int index) {
        SubjectLevelList subjectLevelList = null;
        try {

            if(baseMapper.selectById(id+"-"+index)==null) {

                String s = HttpRequest.sendGet("https://www.liankexue.cn/linkscience/api/subject/previousexaminfo", "id="+id+"&type="+type,token);
                Map map = JSONObject.parseObject(s, Map.class);
                Map mapValue = (Map) map.get("value");
                List list= (List) mapValue.get("subjectLevelList");
                subjectLevelList = JSON.parseObject(list.get(index).toString(), SubjectLevelList.class);
                subjectLevelList.setPreviousExamname((String) mapValue.get("previousExamName"));
                subjectLevelList.setPreviousExamtime(String.valueOf(mapValue.get("previousExamTime")));
                SubjectExamType subjectExamType=new SubjectExamType();
                subjectExamType.setId(Integer.valueOf(id));
                if(subjectExamTypeService.getById(subjectExamType.getId())!=null) {
                    subjectExamType.setPreviousExamname(subjectLevelList.getPreviousExamname());
                    subjectExamType.setExamType(subjectLevelList.getPreviousExamname());
                    subjectExamType.setSubjectId(Integer.valueOf(subjectLevelList.getId()));
                    subjectExamTypeService.save(subjectExamType);
                }
                subjectLevelList.setQuestionId(subjectLevelList.getId());
                subjectLevelList.setId(id+"-"+index);
                baseMapper.insert(subjectLevelList);
            }
            else {
                System.out.println("命中数据库");
                subjectLevelList=baseMapper.selectById(id+"-"+index);
            }

        } catch (Exception e) {
//            throw new RuntimeException(e);
        }
        return String.valueOf(subjectLevelList);
    }
}
