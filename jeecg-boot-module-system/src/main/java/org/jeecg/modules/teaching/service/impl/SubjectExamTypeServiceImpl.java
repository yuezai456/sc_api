package org.jeecg.modules.teaching.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.teaching.mapper.SubjectExamTypeMapper;
import org.jeecg.modules.teaching.model.SubjectExamType;
import org.jeecg.modules.teaching.service.SubjectExamTypeService;
import org.jeecg.modules.teaching.utils.HttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Fitz
 * @since 2023-03-16
 */
@Service
public class SubjectExamTypeServiceImpl extends ServiceImpl<SubjectExamTypeMapper, SubjectExamType> implements SubjectExamTypeService {

    @Override
    public String searchExam(String subjectId, String token) {
        String s = HttpRequest.sendGet("https://www.liankexue.cn/linkscience/api/subject/previousexamlist", "subjectId="+subjectId+"&type=1&year=0",token);
        Map map= JSONObject.parseObject(s, Map.class);
        List listValue = (List) map.get("value");
        ListIterator listIterator = listValue.listIterator();
        SubjectExamType subjectExamType=new SubjectExamType();
        while (listIterator.hasNext()){
            Map next = (Map) listIterator.next();
            subjectExamType.setId((Integer) next.get("id"));
            subjectExamType.setSubjectId((Integer) next.get("subjectId"));
            subjectExamType.setExamtime((Integer) next.get("examTime"));
            subjectExamType.setPreviousExamname((String) next.get("title"));
            subjectExamType.setExamType((String) next.get("title"));
            try {
                baseMapper.insert(subjectExamType);
            } catch (Exception e) {
                System.out.println("录入重复数据");
            }

        }


//        for (Object list : map.keySet()) {
//            List list1= (List) list;
//            System.out.println(list1);
//        }
//

        return "ok";
    }
}
