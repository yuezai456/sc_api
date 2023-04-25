package org.jeecg.modules.teaching.controller;



import org.jeecg.modules.teaching.service.SubjectLevelListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("getexamdata")
    public String getExamData(@RequestParam("id") String id,@RequestParam("type") String type,@RequestParam("index") int index){
        String token="jwt_eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3NTYzMDJhOS1jZGUwLTRlODItOTZhMS1kZWFjYTg5NGM3MzAiLCJpYXQiOjE2Nzg4Nzg1NzIsInN1YiI6IjE4NjI2MzAxMTEyLTI2MDMyNjAzIn0.ySO-yofIzK4sGev5-NWECq2TtkGA14Xaq9UpPNEqeI0";
        System.out.println(token);
        return subjectLevelListService.getExamList(id,type,token,index);
    }
    @GetMapping("getexamlist")
    public String getExamList(@RequestParam("dirt") String dirt){

        return subjectLevelListService.getExamLists(dirt);
    }
}

