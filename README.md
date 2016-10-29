# taotao商城
## day01
  1. spring+springmvc+mybatis整合
  2. 后台工程的商品分页查询实现 
	- 技术要点：
		1. 使用了[Mybatis-PageHelper](https://github.com/pagehelper/Mybatis-PageHelper)分页插件
		2. EasyUI请求的url：`http://localhost:8080/item/list?page=1&rows=30`
		3. 分页查询的结果迎合EasyUi分页属性（json类型的rows，和整型的total），因而创建一个具有如下属性的pojo类：
        ```java
        private long total;
        private List<?> rows;
        ```

## day02
1. 商品类目选择
	- 技术要点：
		- 为了迎合EasyUi树形菜单的特点需要创建如下pojo 
		```java
        private long id;
		private String text;
		private String state;
        ```
2. 搭建ftp服务器（使用nginx）
3. 完成商品图片上传功能
	- 技术要点：
		1. 使用了commons-net包中的FTPClient类进行图片上传(相关知识点参见PictureService.java和FtpUtil.java)
		2. 迎合[kindeditor上传图片的特点](http://kindeditor.net/docs/upload.html)创建如下pojo：
		```java
        private int error;
		private String url;
		private String message;
        ```
		并设置两个静态方法方便返回结果是调用
		```java
		//成功时调用的方法
		public static PictureResult ok(String url) {
			return new PictureResult(0, url, null);
		}
		//失败时调用的方法
		public static PictureResult error(String message) {
			return new PictureResult(1, null, message);
		}
		```

## day03
1. 商品添加
2. 商品更新
3. 商品批量删除
	- 技术要点：参见 item-*.jsp、ItemServiceImpl.java


## day04
1. 规格参数模板的创建、列表查询
2. 商品规格参数的添加
3. 更新商品添加功能
	- 技术要点：
		1. 前端参见WEF-INF下jsp相关文件，common.js
		2. 后端参见ItemParamServiceImpl.java等
		3. 查询商品规格参数时，用到了简单的双表连接，用mybatis简单实现：
	```xml
	<resultMap id="BaseResultMap" type="cn.eden.taotao.pojo.ItemParam">
		<id column="id" property="id" jdbcType="BIGINT" />
		<result column="item_cat_id" property="itemCatId" jdbcType="BIGINT" />
		<result column="created" property="created" jdbcType="TIMESTAMP" />
		<result column="updated" property="updated" jdbcType="TIMESTAMP" />
		<result column="name" property="name" jdbcType="VARCHAR" />
		<result column="param_data" property="paramData" jdbcType="LONGVARCHAR" />
	</resultMap>	
	<select id="getItemParams" resultMap="BaseResultMap">
		SELECT
			p.*, c.`name`
		FROM
			tb_item_param p
		LEFT JOIN tb_item_cat c ON p.item_cat_id = c.id
	</select>
	```


## day05
1. 创建了portal前台门户工程，rest服务层工程
2. 商品分类展示
	- 技术要点：
		1. 由于portal只负责页面展示，它请求的数据必须要向rest中取。所以这里遇到了ajax访问json数据的跨域问题，这里采用jsonp技术。
		2. **什么是jsonp？**Json跨域请求数据是不可以的，但是js跨域请求js脚本是可以的。可以把json数据封装成一个js语句，做一个方法的调用。  
		3. json数据格式大致：![](http://i.imgur.com/GEu4560.png)
		4. 创建两个pojo，一个是返回值pojo，另一个分类节点pojo
			```java
				public class CatResult {
					private List<?> data;	
				}
				public class CatNode {
					@JsonProperty("n")
					private String name;
					@JsonProperty("u")
					private String url;
					@JsonProperty("i")
					private List<?> item;
				}
			```
		5. 具体业务实现参见ItemCatServiceImpl.java
		6. 为了防止数据的乱码问题，在Controller的requestMapping中添加如下：
		`@RequestMapping(value="/itemcat/all", produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")`

## day06

 1. 内容分类管理：实现内容分类节点增删改
	  - 技术要点： 
         1. 内容分类也是 迎合EasyUITree菜单的特点，另外业务中的分类节点必须要添加父节点（parentId），所以重新创建了pojo(TreeNode是树形菜单的基本节点结构)：
         
	         ```java
	        public class ContentCatTreeNode extends TreeNode {
	        	private Long parentId;
	        	public Long getParentId() {
	        		return parentId;
	        	}
	        	public void setParentId(Long parentId) {
	        		this.parentId = parentId;
	        	}
	        }
			```
 2. 内容管理：内容管理分页查询（分页查询还是运用到了分页插件）及内容的增加。内容主要是给前台的大广告位提供数据服务。
	- 技术要点：
		- 内容管理分页查询业务代码:
   
		```java
		@Override
		public DataGridResult getContentsByPage(long page, long pageSize) {
		    TbContentExample example = new TbContentExample();
		    // 开始分页
		    PageHelper.startPage((int) page, (int) pageSize);
		    // 获取查询结果
		    List<TbContent> rows = contentMapper.selectByExample(example);
		    DataGridResult dgr = new DataGridResult();
		    dgr.setRows(rows);
		    // 获取分页信息 商品总数信息
		    PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(rows);
		    dgr.setTotal(pageInfo.getTotal());
		    return dgr;
		}
		```     
 3. rest服务层发布内容（大广告位）的数据服务：参见taotao-rest中ContentServiceImpl.java
 4. 前台portal层通过httpClient来调用rest服务层的服务:
	 - 技术要点：
		 1. httpClientUtil.java封装了httpClient包相关方法。
		 2. 业务具体实现参见taotao-portal的ContentServiceImpl.java
		 3. 控制层，通过调用service层方法获得json数据，创建逻辑视图model，从而在前台jsp界面实现。

			```java
			@RequestMapping("/index")
			public String showIndex(Model model) {
				String adJson = contentService.getContentList();
				model.addAttribute("ad1", adJson);
				return "index";
			}
			```

        


      

  

  

		



