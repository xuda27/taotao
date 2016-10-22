package cn.eden.taotao.test;

import java.util.List;

import javax.swing.border.TitledBorder;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.eden.taotao.mapper.TbItemMapper;
import cn.eden.taotao.pojo.TbItem;
import cn.eden.taotao.pojo.TbItemExample;

public class PageHelperTest {
	@Test
	public void pageHelperTest() {
		//创建一个spring容器
		ApplicationContext applicationContext =  new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		//从容器中获得mapper的代理对象
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		//执行查询
		TbItemExample itemExample = new TbItemExample();
		//在执行sql查询前分页
		//显示第几页 和数量
		PageHelper.startPage(1, 30);
		List<TbItem> list = itemMapper.selectByExample(itemExample);
		for(TbItem item : list) {
			String title = item.getTitle();
			System.out.println(title);
		}
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list); 
		long total = pageInfo.getTotal();
		System.out.println("总页数是：" + total);
	}
}
