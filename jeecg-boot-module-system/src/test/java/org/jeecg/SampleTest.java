package org.jeecg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.demo.mock.MockController;
import org.jeecg.modules.demo.test.entity.JeecgDemo;
import org.jeecg.modules.demo.test.mapper.JeecgDemoMapper;
import org.jeecg.modules.demo.test.service.IJeecgDemoService;
import org.jeecg.modules.system.service.ISysDataLogService;
import org.jeecg.modules.system.service.ISysDepartService;
import org.jeecg.modules.teaching.mapper.SubjectExamTypeMapper;
import org.jeecg.modules.teaching.model.ExamData;
import org.jeecg.modules.teaching.model.SubjectExamType;
import org.jeecg.modules.teaching.service.SubjectExamTypeService;
import org.jeecg.modules.teaching.service.impl.SubjectExamTypeServiceImpl;
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
	@Autowired
	private SubjectExamTypeServiceImpl subjectExamTypeService;

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
	public void testDate() {
		//创建page对象，传递当前页，每页记录数
		long current=1;
		long limit=10;
		//创建测试数据样本
		SubjectExamType subjectExamType=new SubjectExamType();
		subjectExamType.setProgramType("Python");
		subjectExamType.setLevelType("一级");
		//设置分页数
		Page<SubjectExamType> page=new Page<SubjectExamType>(current,limit);
		//设置查询条件
		QueryWrapper queryWrapper=new QueryWrapper();
		queryWrapper.eq("program_type",subjectExamType.getProgramType());
		queryWrapper.eq("level_type",subjectExamType.getLevelType());
		queryWrapper.orderByDesc("date");
		//查询分页数据
		IPage<SubjectExamType> ipage = subjectExamTypeService.page(page,queryWrapper);
		System.out.println(ipage.getRecords());

	}
	@Test
	public void testDateList(){
		//202009Python一级真题
		ExamData examData=new ExamData();



		List<SubjectExamType> subjectExamTypes = subjectExamTypeMapper.selectList(new QueryWrapper<SubjectExamType>());
		Iterator<SubjectExamType> iterator = subjectExamTypes.iterator();
		while (iterator.hasNext()){
			SubjectExamType subjectExamType=iterator.next();
			String title=subjectExamType.getPreviousExamname();
			List<String[]> list = new ArrayList<>();

			//添加其它信息
			String programType="";
			String levelType="";
			String examType="";
			//添加日期
//			String date=title.substring(0,6);
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


//			subjectExamType.setDate(date);
			subjectExamType.setProgramType(programType);
			subjectExamType.setLevelType(levelType);
			subjectExamType.setExamType(examType);
			subjectExamTypeMapper.updateById(subjectExamType);

		}

	}
}
