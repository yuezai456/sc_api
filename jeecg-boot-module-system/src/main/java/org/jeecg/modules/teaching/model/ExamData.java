package org.jeecg.modules.teaching.model;

import lombok.Data;

@Data
public class ExamData {
    String[] programTypes={"Scratch","Python","机器人","C语言"};
    String[] levelTypes={"一级","二级","三级","四级","五级"};
    String[] examTypes={"实际操作真题","理论综合真题","级真题","理论综合测试卷"};
}
