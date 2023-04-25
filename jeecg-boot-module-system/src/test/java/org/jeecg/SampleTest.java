package org.jeecg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.demo.mock.MockController;
import org.jeecg.modules.demo.test.entity.JeecgDemo;
import org.jeecg.modules.demo.test.mapper.JeecgDemoMapper;
import org.jeecg.modules.demo.test.service.IJeecgDemoService;
import org.jeecg.modules.system.service.ISysDataLogService;
import org.jeecg.modules.system.service.ISysDepartService;
import org.jeecg.modules.teaching.mapper.SubjectExamTypeMapper;
import org.jeecg.modules.teaching.model.ExamData;
import org.jeecg.modules.teaching.model.SubjectExamType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleTest {

	@Resource
	private JeecgDemoMapper jeecgDemoMapper;
	@Resource
	private IJeecgDemoService jeecgDemoService;
	@Resource
	private ISysDataLogService sysDataLogService;
	@Autowired
	private ISysDepartService sysDepartService;
	@Resource
	private MockController mock;
	@Autowired
	private SubjectExamTypeMapper subjectExamTypeMapper;
	@Test
	public void departTest(){
		List<String> list = sysDepartService.getMySubDepIdsByDepId("57197590443c44f083d42ae24ef26a2c,26c7f056a6b94ae78d736c67cd24baac");
		System.out.println(list.toString());
	}

	@Test
	public void testSelect() {
		System.out.println(("----- selectAll method test ------"));
		List<JeecgDemo> userList = jeecgDemoMapper.selectList(null);
		Assert.assertEquals(5, userList.size());
		userList.forEach(System.out::println);
	}

	@Test
	public void testXmlSql() {
		System.out.println(("----- selectAll method test ------"));
		List<JeecgDemo> userList = jeecgDemoMapper.getDemoByName("Sandy12");
		userList.forEach(System.out::println);
	}

	/**
	 * 测试事务
	 */
	@Test
	public void testTran() {
		jeecgDemoService.testTran();
	}
	
	//author:lvdandan-----date：20190315---for:添加数据日志测试----
	/**
	 * 测试数据日志添加
	 */
	@Test
	public void testDataLogSave() {
		System.out.println(("----- datalog test ------"));
		String tableName = "jeecg_demo";
		String dataId = "4028ef81550c1a7901550c1cd6e70001";
		String dataContent = mock.sysDataLogJson();
		sysDataLogService.addDataLog(tableName, dataId, dataContent);
	}
	//author:lvdandan-----date：20190315---for:添加数据日志测试----
	@Test
	public void testDateList(){
		//202009Python一级真题
		ExamData examData=new ExamData();



		List<SubjectExamType> subjectExamTypes = subjectExamTypeMapper.selectList(new QueryWrapper<SubjectExamType>());
		Iterator<SubjectExamType> iterator = subjectExamTypes.iterator();
		while (iterator.hasNext()){
			SubjectExamType subjectExamType=iterator.next();
			String title=subjectExamType.getExamType();
			List<String[]> list = new ArrayList<>();

			//添加其它信息
			String programType="";
			String levelType="";
			String examType="";
			//添加日期
			String date=title.substring(0,6);
			for(int i=0;i<examData.getProgramTypes().length;i++){
				if(title.contains(examData.getProgramTypes()[i])){
					programType=examData.getProgramTypes()[i];
					break;
				}
			}
			for(int i=0;i<examData.getLevelTypes().length;i++){
				if(title.contains(examData.getLevelTypes()[i])){
					levelType=examData.getLevelTypes()[i];
					break;
				}
			}
			for(int i=0;i<examData.getExamTypes().length;i++){
				if(title.contains(examData.getExamTypes()[i])){
					examType=examData.getExamTypes()[i];
					break;
				}
			}


			subjectExamType.setDate(date);
			subjectExamType.setProgramType(programType);
			subjectExamType.setLevelType(levelType);
			subjectExamType.setExamType(examType);
			subjectExamTypeMapper.updateById(subjectExamType);

		}

	}
}
