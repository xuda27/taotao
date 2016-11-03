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

## day07

1. 使用redis做缓存
2. 将jedis整合到spring中
3. 缓存添加至业务代码中
	1. rest工程发布redis服务，portal工程通过HttpClient调用rest工程业务。
	2. rest工程添加大广告位功能添加redis逻辑，大致：
		- 首先查询redis数据库是否存在数据，如果有直接返回数据
		- 如果没有，则调用mysql查询，查询出的数据在返回前，增至redis数据库中
	3. 后台manager工程redis逻辑：后台添加大广告位内容后，需要删除redis中全部该大广告位内容的内容分类下的所有内容。



```java
	//前台业务service层
	public String getContentList() {
		// 调用服务层 查询商品内容信息（即大广告位）
		String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
		try {
			// 把字符串转换成TaotaoResult
			TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
			// 取出内容列表
			List<TbContent> list = (List<TbContent>) taotaoResult.getData();
			List<Map> resultList = new ArrayList<Map>(); 
			// 创建一个jsp页码要求的pojo列表
			for(TbContent tbContent : list) {
				Map map = new HashMap();
				map.put("srcB", tbContent.getPic2());
				map.put("height", 240);
				map.put("alt", tbContent.getTitle());
				map.put("width", 670);
				map.put("src", tbContent.getPic());
				map.put("widthB", 550);
				map.put("href", tbContent.getUrl());
				map.put("heightB", 240);
				resultList.add(map);
			}
			return JsonUtils.objectToJson(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//rest服务层 添加大广告位显示redis的逻辑
	@Override
	public List<TbContent> getContentList(long contentCategoryId) {
		try {
			// 从缓存中取内容
			String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY,
					contentCategoryId + "");
			if (!StringUtils.isBlank(result)) {
				// 把字符串转换成list
				List<TbContent> resultList = JsonUtils.jsonToList(result,
						TbContent.class);
				return resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		// 根据内容分类id查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentCategoryId);
		// 执行查询
		List<TbContent> list = contentMapper.selectByExample(example);

		try {
			// 向缓存中添加内容
			String cacheString = JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentCategoryId + "",
					cacheString);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return list;
	}

	/**
	 * redisServiceImpl.java
	 * 前台修改内容时调用此服务，删除redis中的该内容的内容分类下的全部内容
	 */
	@Override
	public TaotaoResult syncContent(long contentCategoryId) {
		try {
			jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, contentCategoryId + "");
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}
```
## day08
-  将数据库商品信息导入到solr索引库中
	- 技术要点1：
		1. 查询数据库中的商品信息 商品信息对应solr的业务字段，并创建相应的pojo，需要查询mysql中tb_item、tb_item_cat、tb_item_desc三表。
		2. 业务部分参见ItemServiceImpl.java

``` sql
SELECT
	a.id,
	a.title,
	a.sell_point,
	a.price,
	a.image,
	b.`name` category_name,
	c.item_desc
FROM
	tb_item a
LEFT JOIN tb_item_cat b ON a.cid = b.id
LEFT JOIN tb_item_desc c ON a.id = c.item_id

```
``` xml
<!-- solr业务字段 -->
<field name="item_title" type="text_ik" indexed="true" stored="true"/>
<field name="item_sell_point" type="text_ik" indexed="true" stored="true"/>
<field name="item_price"  type="long" indexed="true" stored="true"/>
<field name="item_image" type="string" indexed="false" stored="true" />
<field name="item_category_name" type="string" indexed="true" stored="true" />
<field name="item_desc" type="text_ik" indexed="true" stored="false" />
<field name="item_keywords" type="text_ik" indexed="true" stored="false" multiValued="true"/>
<copyField source="item_title" dest="item_keywords"/>
<copyField source="item_sell_point" dest="item_keywords"/>
<copyField source="item_category_name" dest="item_keywords"/>
<copyField source="item_desc" dest="item_keywords"/>	
```
``` java
//sql语句业务字段对应的pojo
public class Item {

	private String id;
	private String title;
	private String sell_point;
	private long price;
	private String image;
	private String category_name;
	private String item_des;
}
```
- 发布搜索服务，对外提供一个get形式的服务，调用此服务时需要查询条件，分页条件可以使用page（要显示第几页）、rows（每页显示的记录数）。
	- 技术要点2：
		1. 对外提供http形式的get请求服务。请求的url：`/search/query?q={查询条件}&page={page}&rows={rows}`
		2. 需要创建一个搜索结果的pojo
		3. dao层查询solr索引库返回pojo
		4. service层设置查询条件
		5. controller层设置默认的当前页和默认的每页显示的记录数
		6. 详细参见SearchDao、SearchService、SearchController

``` java
//搜索结果pojo
public class SearchResult {
	// 商品列表
	private List<Item> Items;
	// 总记录数
	private long recordCount;
	// 总页数
	private long pageCount;
	// 当前页
	private long currentPage;
}

//dao
public SearchResult search(SolrQuery query) {
	// 创建返回值对象
	SearchResult result = new SearchResult();
	// 根据查询条件查询索引库
	QueryResponse queryResponse = null;
	try {
		queryResponse = solrServer.query(query);
	} catch (SolrServerException e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	}
	// 取查询结果
	SolrDocumentList documentList = queryResponse.getResults();
	// 取查询结果总数量，并加入result中
	result.setRecordCount(documentList.getNumFound());
	// 商品列表
	List<Item> items = new ArrayList<Item>();
	// 取高亮显示
	Map<String, Map<String, List<String>>> hightLighting = queryResponse
	.getHighlighting();
	// 取商品列表
	for (SolrDocument document : documentList) {
		// 创建商品对象
		Item item = new Item();
		item.setId((String) document.get("id"));
		// 取高亮显示的结果
		List<String> list = hightLighting.get(document.get("id")).get("item_title");
		String title = "";
		if (list != null && list.size() > 0) {
			title = list.get(0);
		} else {
			title = (String) document.get("item_title");
		}
		item.setTitle(title);
		item.setImage((String) document.get("item_image"));
		item.setPrice((long) document.get("item_price"));
		item.setSell_point((String) document.get("item_sell_point"));
		item.setCategory_name((String) document.get("item_category_name"));
		// 添加的商品列表
		items.add(item);
	}
	result.setItems(items);
	return result;
}

//service
public SearchResult search(String queryString, int page, int rows) {
	// 创建查询对象
	SolrQuery query = new SolrQuery();
	// 设置查询条件
	query.setQuery(queryString);
	// 设置分页 默认是每页10条
	query.setStart((page - 1) * rows);
	query.setRows(rows);
	// 设置默认搜索域
	query.set("df", "item_keywords");
	// 设置高亮显示
	query.setHighlight(true);
	query.addHighlightField("item_title");
	query.setHighlightSimplePre("<em style=\"color:red\">");
	query.setHighlightSimplePost("</em>");
	// 执行查询
	SearchResult searchResult = searchDao.search(query);
	// 计算查询结果总页数
	long recordCount = searchResult.getRecordCount();
	long pageCount = recordCount / rows;
	if (recordCount % rows > 0) {
		pageCount++;
	}
	// 总页数
	searchResult.setPageCount(pageCount);
	// 当前页码
	searchResult.setCurrentPage(page);
	return searchResult;
}

//controller
@RequestMapping(value = "/query", method = RequestMethod.GET)
@ResponseBody
TaotaoResult search(@RequestParam("q") String queryString,@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "40") Integer rows) {
	// 查询条件不能为空
	if (StringUtils.isBlank(queryString)) {
		return TaotaoResult.build(400, "查询条件不能为空");
	}
	SearchResult searchResult = null;
	try {
		// 解决get乱码问题
		searchResult = searchService.search(
				new String(queryString.getBytes("iso8859-1"), "utf-8"),page, rows);
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
		return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
	}
	return TaotaoResult.ok(searchResult);
}
```


      

  

  

		



