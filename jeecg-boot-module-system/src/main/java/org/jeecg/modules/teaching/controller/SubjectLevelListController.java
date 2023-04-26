package org.jeecg.modules.teaching.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.teaching.model.ExamData;
import org.jeecg.modules.teaching.model.SubjectExamType;
import org.jeecg.modules.teaching.model.SubjectLevelList;
import org.jeecg.modules.teaching.service.SubjectExamTypeService;
import org.jeecg.modules.teaching.service.SubjectLevelListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Fitz
 * @since 2023-03-15
 */

@RestController
@RequestMapping("/subject")

public class SubjectLevelListController {
    @Autowired
    SubjectLevelListService subjectLevelListService;
    @Autowired
    SubjectExamTypeService subjectExamTypeService;
    @GetMapping("getexamdata")
    public String getExamData(@RequestParam("id") String id,@RequestParam("type") String type,@RequestParam("index") int index){
        String token="jwt_eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3NTYzMDJhOS1jZGUwLTRlODItOTZhMS1kZWFjYTg5NGM3MzAiLCJpYXQiOjE2Nzg4Nzg1NzIsInN1YiI6IjE4NjI2MzAxMTEyLTI2MDMyNjAzIn0.ySO-yofIzK4sGev5-NWECq2TtkGA14Xaq9UpPNEqeI0";
        return subjectLevelListService.getExamList(id,type,token,index);
    }
    @GetMapping("getprogramnames")
    public List getProgramNames(@RequestParam("programtype") String programType, @RequestParam("leveltype") String levelType){
        QueryWrapper queryWrapper=new QueryWrapper();
        //查询结果
        queryWrapper.select("previous_examname");
        //查询条件
        queryWrapper.eq("program_type",programType);
        queryWrapper.eq("level_type",levelType);
        List<String> programNames=new ArrayList<>();
        for (Object o : subjectExamTypeService.list(queryWrapper)) {
            SubjectExamType subjectExamType= (SubjectExamType) o;
            programNames.add(subjectExamType.getPreviousExamname());
        }

        return programNames;
    }
    @GetMapping("getprogramtypes")
    public String[] getProgramTypes(){
        ExamData examData=new ExamData();
        return examData.getProgramTypes();
    }
    @GetMapping("getleveltypes")
    public String[] getLevelTypes(){
        ExamData examData=new ExamData();
        return examData.getLevelTypes();
    }
    @GetMapping("getexamlist")
    public String getExamList(@RequestParam("dirt") String dirt){

        return subjectLevelListService.getExamLists(dirt);
    }
    @PostMapping("getPage")
    public IPage getPage(@PathVariable long current, @PathVariable long limit,
                         @RequestBody(required = false) String programType,@RequestBody String levelType){
        //创建page对象，传递当前页，每页记录数
        //创建测试数据样本
        //设置分页数
        Page<SubjectExamType> page=new Page<SubjectExamType>(current,limit);
        //设置查询条件
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("program_type",programType);
        if(levelType!=null){queryWrapper.eq("level_type",levelType);}
        queryWrapper.orderByDesc("date");
        //查询分页数据
        IPage<SubjectExamType> ipage = subjectExamTypeService.page(page,queryWrapper);
        return ipage;
    }

    @GetMapping("getexam")
    public List<SubjectLevelList> getExam(@RequestParam("previousExamName") String previousExamName){
        return subjectLevelListService.getExam(previousExamName);
    }
}

