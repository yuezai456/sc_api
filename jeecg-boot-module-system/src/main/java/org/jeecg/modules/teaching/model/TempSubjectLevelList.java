package org.jeecg.modules.teaching.model;

import lombok.Data;

@Data
public class TempSubjectLevelList {
    public int id;
    public String createUser;
    public String createTime;
    public int isDeleted;
    public String updateUser;
    public String updateTime;
    public int version;
    public int type;
    public String title;
    public String optionA;
    public String optionB;
    public String optionC;
    public String optionD;
    public String optionE;
    public String answer;
    public String userAnswer;
    public String analyzeContent;
    public String analyzeVideo;
    public String subjectId;
    public String examDots;
    public String sprintId;
    public String subjectNumber;
    public String images;
    public String weType;
    public String wxAnswerType;
    public String isMySubject;
}
