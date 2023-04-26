package org.jeecg.modules.teaching.model;

import lombok.Data;

@Data
public class ExamData {
    String[] programTypes={"Scratch","Python","机器人","C语言","图形化"};
    String[] levelTypes={"一级","二级","三级","四级","五级","六级","七级","八级"};
    String[] examTypes={"实际操作真题","理论综合真题","级真题","测试卷"};
}
