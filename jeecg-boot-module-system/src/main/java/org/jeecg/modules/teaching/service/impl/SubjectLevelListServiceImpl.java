package org.jeecg.modules.teaching.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.jeecg.modules.teaching.mapper.SubjectLevelListMapper;
import org.jeecg.modules.teaching.model.ExamData;
import org.jeecg.modules.teaching.model.SubjectExamType;
import org.jeecg.modules.teaching.model.SubjectLevelList;
import org.jeecg.modules.teaching.service.SubjectExamTypeService;
import org.jeecg.modules.teaching.service.SubjectLevelListService;
import org.jeecg.modules.teaching.utils.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public String getExamLists(String dirt) {
        String title="202009Python一级真题";
        List<SubjectLevelList> subjectLevelLists = baseMapper.selectList(new QueryWrapper<>());
        ExamData examData=new ExamData();
        List<String[]> list = new ArrayList<>();
        List<String> examInfo=new ArrayList<>();
        //添加日期
        String date=title.substring(0,6);
        examInfo.add(date);
        //添加编程语言类型
        list.add(examData.getProgramTypes());
        //添加编程语言等级
        list.add(examData.getLevelTypes());
        //添加考试类型
        list.add(examData.getExamTypes());
        for(int i= 0;i<list.size();i++){

            for(int j=0;j<list.get(i).length;j++){
                if(title.contains(list.get(i)[j])){
                    examInfo.add(list.get(i)[j]);
                }
            }

        }
        return date;
    }

    @Override
    public List<SubjectLevelList> getExam(String previousExamName) {
        QueryWrapper queryWrapper=new QueryWrapper();
        //通过试卷名查询
        queryWrapper.eq("previous_examName",previousExamName);
        //正序排列
        queryWrapper.orderByAsc("id");
        List list = baseMapper.selectList(queryWrapper);
        return list;
    }
}
