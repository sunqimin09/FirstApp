package com.tellout.util;
/** 
   @author sunqm E-mail:sunqimin09@163.com
   @version 创建时间：2013-11-15 上午11:04:42
   @description 
  */
public class DbHelper {
	
	private void create() {
		String sql = "create table  dbo.province(proID int primary key, proName   varchar(50),keys    varchar(2))";
		String sql1 = "create table city ( cityID int identity(1,1), cityName varchar(50) primary key, proID int foreign key references province(proID), keys varchar(2) )";
	}
	
	private void insert(){
		String sql = "insert into province values(1,'北京市','B')";
		
		
	}
	String[] insertPro = { "insert into province values(1,'北京市','B')",
			"insert into province values(2,'天津市','T')",
			" insert into province values(3,'上海市','S')",
			"insert into province values(4,'重庆市','C')",
			"insert into province  values(5,'河北省','H')",
			"insert into province values(6,'山西省','S')",
			" insert into province values(7,'台湾省','T')",
			" insert into province  values(8,'辽宁省','L')",
			" insert into province values(9,'吉林省','J')",
			" insert into province values(10,'黑龙江省','H')",
			" insert into province values(11,'江苏省','J')",
			" insert into province values(12,'浙江省','Z')",
			" insert  into province values(13,'安徽省','A')",
	   		" insert into province values(14,'福建省','F')",  
	   		
	   		"insert into province values(15,'江西省','J')",
	   		" insert into province values(16,'山东省','S')",
	   		" insert into province  values(17,'河南省','H')",
	   		" insert into province values(18,'湖北省','H')",
	   		" insert into province values(19,'湖南省','H')",
	   		" insert into province  values(20,'广东省','G')",
	   		" insert into province values(21,'甘肃省','G')",
	   		" insert into province values(22,'四川省','S')",
	   		" insert into province values(24,'贵州省','G')",
	   		" insert into province values(25,'海南省','H')",
	   		" insert into province values(26,'云南省','Y')",
	   		" insert into province  values(27,'青海省','Q')",
	   		" insert into province values(28,'陕西省','S')",
	   		" insert into province values(29,'广西壮族自治区','G')",
	   		" insert into province  values(30,'西藏自治区','X')",
	   		" insert into province values(31,'宁夏回族自治区','N')",
	   		" insert into province values(32,'新疆维吾尔自治区','X')",
	   		" insert into province values(33,'内蒙古自治区','N')",
	   		" insert into province values(34,'澳门特别行政区','A')",
	   		" insert into province values(35,'香港特别行政区','X')"};
	
	/**  
	   ------------------------------------------------------------------------
	   ----------------------------------------------------------------
	   --中国34个省级行政单位 23个省 5个自治区 4个直辖市 2特别行政区
	   
	  
	   
	   ------------------------------------------------------------------------
	   ------------------------------------------------------------------
	   创建city表 create table city ( cityID int identity(1,1), cityName
	   varchar(50) primary key, proID int foreign key references
	   province(proID), keys varchar(2) )
	   
	   ------------------------------------------------------------------------
	   ----------------------------------------------------------------
	   --插入各个省的城市数据 --4个直辖市 
	   insert into city values('北京市',1,'B') 
	   insert into city values('天津市',2,'T') 
	   insert into city values('上海市',3,'S') 
	   insert into city values('重庆市',4,'C') 
	   --5河北省(2005年辖：11个地级市，36个市辖区、22个县级市、108个县、6个自治县)
	   insert into city values('石家庄市',5,'S') 
	   insert into city values('唐山市',5,'T') 
	   insert into city values('秦皇岛市',5,'Q') 
	   insert into city values('邯郸市',5,'G') 
	   insert into city values('邢台市',5,'J')  
	   insert into city values('保定市',5,'B') 
	   insert into city values('张家口市',5,'Z') 
	   insert into city values('承德市',5,'C') 
	   insert into city values('沧州市',5,'C') 
	   insert into city values('廊坊市',5,'L') 
	   insert into city values('衡水市',5,'H')
	   --6山西省11个城市 
	   insert into city values('太原市',6,'T') 
	   insert into city values('大同市',6,'D') 
	   insert into city values('阳泉市',6,'Y') 
	   insert into city values('长治市',6,'C') 
	   insert into city values('晋城市',6,'J') 
	   insert into city values('朔州市',6,'S') 
	   insert into city values('晋中市',6,'J') 
	   insert into city values('运城市',6,'Y') 
	   insert into city values('忻州市',6,'Q') 
	   insert into city values('临汾市',6,'L') 
	   insert into city values('吕梁市',6,'L')
	   --7台湾省(台湾本岛和澎湖共设7市
	   、16县，其中台北市和高雄市为“院辖市”，直属“行政院”，其余属台湾省；市下设区，县下设市（县辖市）、镇、乡，合称区市镇乡。) insert
	   into city values('台北市',7,'T') 
	   insert into city values('高雄市',7,'G') 
	   insert into city values('基隆市',7,'J') 
	   insert into city values('台中市',7,'T') 
	   insert into city values('台南市',7,'T') 
	   insert into city values('新竹市',7,'X') 
	   insert into city values('嘉义市',7,'J') 
	   insert into city values('台北县',7,'T') 
	   insert into city values('宜兰县',7,'Y') 
	   insert into city values('桃园县',7,'T') 
	   insert into city values('新竹县',7,'X') 
	   insert into city values('苗栗县',7,'M') 
	   insert into city values('台中县',7,'T') 
	   insert into city values('彰化县',7,'Z') 
	   insert into city values('南投县',7,'N') 
	   insert into city values('云林县',7,'Y') 
	   insert into city values('嘉义县',7,'J') 
	   insert into city values('台南县',7,'T') 
	   insert
	   into city values('高雄县',7,'G') 
	   insert into city values('屏东县',7,'P') 
	   insert into city values('澎湖县',7,'P') 
	   insert into city values('台东县',7,'T') 
	   
	   insert into city values('花莲县',7,'H')
	   
	   
	   --8辽宁省(2006年，辖：14个地级市；56个市辖区、17个县级市、19个县、8个自治县。) 
	   insert into city values('沈阳市',8,'S') 
	   insert into city values('大连市',8,'D') 
	   insert into city values('鞍山市',8,'A') 
	   insert into city values('抚顺市',8,'F') 
	   insert into city values('本溪市',8,'B')
 	   insert into city values('丹东市',8,'D') 
	   insert into city
	   values('锦州市',8,'J') insert into city values('营口市',8,'Y') 
	   insert into city
	   values('阜新市',8,'F') insert into city values('辽阳市',8,'L') 
	   insert into city
	   values('盘锦市',8,'P') insert into city values('铁岭市',8,'T') 
	   insert into city
	   values('朝阳市',8,'Z') insert into city values('葫芦岛市',8,'H')
	   --9吉林省(2006年，辖：8个地级市、1个自治州；20个市辖区、20个县级市、17个县、3个自治县。) 
	   insert into city
	   values('长春市',9,'C') insert into city values('吉林市',9,'J') 
	   insert into city
	   values('四平市',9,'S') insert into city values('辽源市',9,'L') 
	   insert into city
	   values('通化市',9,'T') insert into city values('白山市',9,'B') 
	   insert into city
	   values('松原市',9,'S') insert into city values('白城市',9,'B') 
	   insert into city
	   values('延边朝鲜族自治州',9,'Y') --10黑龙江省(2006年，辖：12地级市、1地区；64市辖区、18县级市、45县、1自治县)
	   insert into city values('哈尔滨市',10,'H') 
	   insert into city
	   values('齐齐哈尔市',10,'Q') 
	   insert into city values('鹤岗市',10,'H') 
	   insert into
	   city values('双鸭山市',10,'S') 
	   insert into city values('鸡市',10,'J') 
	   insert
	   into city values('大庆市',10,'D') 
	   insert into city values('伊春市',10,'Y')
	   insert into city values('牡丹江市',10,'M')
	    insert into city
	   values('佳木斯市',10,'J') 
	   insert into city values('七台河市',10,'Q') 
	   insert into
	   city values('黑河市',10,'H') 
	   insert into city values('绥化市',10,'S') 
	   insert
	   into city values('大兴安岭地区',10,'D')
	   --11江苏省(2005年辖：13个地级市；54个市辖区、27个县级市、25个县) 
	   insert into city
	   values('南京市',11,'N') 
	   insert into city values('无锡市',11,'W') 
	   insert into
	   city values('徐州市',11,'X') 
	   insert into city values('常州市',11,'C') 
	   insert
	   into city values('苏州市',11,'S') 
	   insert into city values('南通市',11,'N')
	   insert into city values('连云港市',11,'L') 
	   insert into city
	   values('淮安市',11,'H') 
	   insert into city values('盐城市',11,'Y') 
	   insert into
	   city values('扬州市',11,'Y') 
	   insert into city values('镇江市',11,'Z') 
	   insert
	   into city values('泰州市',11,'T') 
	   insert into city values('宿迁市',11,'S')
	   --12浙江省(2006年，辖：11个地级市；32个市辖区、22个县级市、35个县、1个自治县。) insert into city
	   values('杭州市',12,'H') 
	   insert into city values('宁波市',12,'N') 
	   insert into
	   city values('温州市',12,'W')
	    insert into city values('嘉兴市',12,'J') 
	    insert
	   into city values('湖州市',12,'H') 
	   insert into city values('绍兴市',12,'X')
	   insert into city values('金华市',12,'J') 
	   insert into city
	   values('衢州市',12,'Q') 
	   insert into city values('舟山市',12,'Z') insert into
	   city values('台州市',12,'T') 
	   insert into city values('丽水市',12,'L')
	   --13安徽省(2005年辖：17个地级市；44个市辖区、5县个级市、56个县。)
	    insert into city
	   values('合肥市',13,'H') 
	   insert into city values('芜湖市',13,'W') 
	   insert into
	   city values('蚌埠市',13,'F') 
	   insert into city values('淮南市',13,'H') 
	   insert
	   into city values('马鞍山市',13,'M') 
	   insert into city values('淮北市',13,'H')
	   insert into city values('铜陵市',13,'T') 
	   insert into city
	   values('安庆市',13,'A') 
	   insert into city values('黄山市',13,'H') insert into
	   city values('滁州市',13,'C') 
	   insert into city values('阜阳市',13,'F') 
	   insert
	   into city values('宿州市',13,'S') 
	   insert into city values('巢湖市',13,'C')
	   insert into city values('六安市',13,'L') 
	   insert into city
	   values('亳州市',13,'H') 
	   insert into city values('池州市',13,'C') 
	   insert into
	   city values('宣城市',13,'X') --14福建省(2006年辖：9个地级市；26个市辖区、14个县级市、45个县。)
	   insert into city values('福州市',14,'F') 
	   insert into city
	   values('厦门市',14,'X') 
	   insert into city values('莆田市',14,'P') 
	   insert into
	   city values('三明市',14,'S') 
	   insert into city values('泉州市',14,'Q') 
	   insert
	   into city values('漳州市',14,'Z') 
	   insert into city values('南平市',14,'N')
	   insert into city values('龙岩市',14,'L') 
	   insert into city
	   values('宁德市',14,'N') --15江西省(2006年全省辖：11个地级市；19个市辖区、10个县级市、70个县。) 
	   insert
	   into city values('南昌市',15,'N') 
	   insert into city values('景德镇市',15,'J')
	   insert into city values('萍乡市',15,'P')
	    insert into city
	   values('九江市',15,'J') 
	   insert into city values('新余市',15,'X') 
	   insert into
	   city values('鹰潭市',15,'Y') 
	   insert into city values('赣州市',15,'G') 
	   insert
	   into city values('吉安市',15,'J') insert into city values('宜春市',15,'Y')
	   insert into city values('抚州市',15,'F') insert into city
	   values('上饶市',15,'S') --16山东省(2005年，辖：17个地级市；49个市辖区、31个县级市、60个县。) insert
	   into city values('济南市',16,'J') insert into city values('青岛市',16,'Q')
	   insert into city values('淄博市',16,'Z') insert into city
	   values('枣庄市',16,'Z') insert into city values('东营市',16,'D') insert into
	   city values('烟台市',16,'Y') insert into city values('潍坊市',16,'W') insert
	   into city values('济宁市',16,'J') insert into city values('泰安市',16,'T')
	   insert into city values('威海市',16,'W') insert into city
	   values('日照市',16,'R') insert into city values('莱芜市',16,'L') insert into
	   city values('临沂市',16,'L') insert into city values('德州市',16,'D') insert
	   into city values('聊城市',16,'L') insert into city values('滨州市',16,'B')
	   insert into city values('菏泽市',16,'H')
	   --17河南省（2005年辖：17个地级市；50个市辖区、21个县级市、88个县。） insert into city
	   values('郑州市',17,'Z') insert into city values('开封市',17,'K') insert into
	   city values('洛阳市',17,'L') insert into city values('平顶山市',17,'P') insert
	   into city values('安阳市',17,'A') insert into city values('鹤壁市',17,'H')
	   insert into city values('新乡市',17,'X') insert into city
	   values('焦作市',17,'J') insert into city values('濮阳市',17,'P') insert into
	   city values('许昌市',17,'X') insert into city values('漯河市',17,'L') insert
	   into city values('三门峡市',17,'S') insert into city values('南阳市',17,'N')
	   insert into city values('商丘市',17,'S') insert into city
	   values('信阳市',17,'X') insert into city values('周口市',17,'Z') insert into
	   city values('驻马店市',17,'Z') insert into city values('济源市',17,'J')
	   --18湖北省（截至2005年12月31日
	   ，全省辖13个地级单位（12个地级市、1个自治州）；102县级单位（38个市辖区、24个县级市、37个县、
	   2个自治县、1个林区），共有1220个乡级单位（277个街道、733个镇、210个乡）。） insert into city
	   values('武汉市',18,'W') insert into city values('黄石市',18,'H') insert into
	   city values('十堰市',18,'S') insert into city values('荆州市',18,'J') insert
	   into city values('宜昌市',18,'Y') insert into city values('襄樊市',18,'X')
	   insert into city values('鄂州市',18,'E') insert into city
	   values('荆门市',18,'J') insert into city values('孝感市',18,'X') insert into
	   city values('黄冈市',18,'H') insert into city values('咸宁市',18,'X') insert
	   into city values('随州市',18,'X') insert into city values('仙桃市',18,'X')
	   insert into city values('天门市',18,'T') insert into city
	   values('潜江市',18,'Q') insert into city values('神农架林区',18,'S') insert into
	   city values('恩施土家族苗族自治州',18,'E')
	   --19湖南省（2005年辖：13个地级市、1个自治州；34个市辖区、16个县级市、65个县、7个自治县。） insert into city
	   values('长沙市',19,'C') insert into city values('株洲市',19,'Z') insert into
	   city values('湘潭市',19,'X') insert into city values('衡阳市',19,'H') insert
	   into city values('邵阳市',19,'X') insert into city values('岳阳市',19,'Y')
	   insert into city values('常德市',19,'C') insert into city
	   values('张家界市',19,'Z') insert into city values('益阳市',19,'Y') insert into
	   city values('郴州市',19,'C') insert into city values('永州市',19,'Y') insert
	   into city values('怀化市',19,'H') insert into city values('娄底市',19,'L')
	   insert into city values('湘西土家族苗族自治州',19,'X')
	   --20广东省（截至2005年12月31日，广东省辖：21
	   个地级市，54个市辖区、23个县级市、41个县、3个自治县，429个街道办事处、1145个镇、4个乡、7个民族乡。） insert into
	   city values('广州市',20,'G') insert into city values('深圳市',20,'S') insert
	   into city values('珠海市',20,'Z') insert into city values('汕头市',20,'S')
	   insert into city values('韶关市',20,'S') insert into city
	   values('佛山市',20,'F') insert into city values('江门市',20,'J') insert into
	   city values('湛江市',20,'Z') insert into city values('茂名市',20,'M') insert
	   into city values('肇庆市',20,'Z') insert into city values('惠州市',20,'H')
	   insert into city values('梅州市',20,'M') insert into city
	   values('汕尾市',20,'S') insert into city values('河源市',20,'H') insert into
	   city values('阳江市',20,'Y') insert into city values('清远市',20,'Q') insert
	   into city values('东莞市',20,'D') insert into city values('中山市',20,'Z')
	   insert into city values('潮州市',20,'C') insert into city
	   values('揭阳市',20,'YJ') insert into city values('云浮市',20,'')
	   
	   --21甘肃省（2006年辖：12个地级市、2个自治州；17个市辖区、4个县级市、58个县、7个自治县。） insert into city
	   values('兰州市',21,'L') insert into city values('金昌市',21,'J') insert into
	   city values('白银市',21,'B') insert into city values('天水市',21,'T') insert
	   into city values('嘉峪关市',21,'J') insert into city values('武威市',21,'W')
	   insert into city values('张掖市',21,'Z') insert into city
	   values('平凉市',21,'P') insert into city values('酒泉市',21,'J') insert into
	   city values('庆阳市',21,'Q') insert into city values('定西市',21,'D') insert
	   into city values('陇南市',21,'L') insert into city values('临夏回族自治州',21,'L')
	   insert into city values('甘南藏族自治州',21,'G')
	   --22四川省（2006年辖：18个地级市、3个自治州；43个市辖区、14个县级市、120个县、4个自治县。） insert into city
	   values('成都市',22,'C') insert into city values('自贡市',22,'Z') insert into
	   city values('攀枝花市',22,'P') insert into city values('泸州市',22,'L') insert
	   into city values('德阳市',22,'D') insert into city values('绵阳市',22,'M')
	   insert into city values('广元市',22,'G') insert into city
	   values('遂宁市',22,'S') insert into city values('内江市',22,'N') insert into
	   city values('乐山市',22,'L') insert into city values('南充市',22,'N') insert
	   into city values('眉山市',22,'M') insert into city values('宜宾市',22,'Y')
	   insert into city values('广安市',22,'G') insert into city
	   values('达州市',22,'D') insert into city values('雅安市',22,'Y') insert into
	   city values('巴中市',22,'B') insert into city values('资阳市',22,'Z') insert
	   into city values('阿坝藏族羌族自治州',22,'A') insert into city
	   values('甘孜藏族自治州',22,'G') insert into city values('凉山彝族自治州',22,'L')
	   --24贵州省(2006年辖：4个地级市、2个地区、3个自治州；10个市辖区、9个县级市、56个县、11个自治县、2个特区。) insert
	   into city values('贵阳市',24,'G') insert into city values('六盘水市',24,'L')
	   insert into city values('遵义市',24,'Z') insert into city
	   values('安顺市',24,'A') insert into city values('铜仁地区',24,'T') insert into
	   city values('毕节地区',24,'B') insert into city values('黔西南布依族苗族自治州',24,'Q')
	   insert into city values('黔东南苗族侗族自治州',24,'Q') insert into city
	   values('黔南布依族苗族自治州',24,'Q')
	   --25海南省(2003－2005年　全省有2个地级市，6个县级市，4个县，6个民族自治县，4个市辖区，1个办事处（西南中沙群岛办事处，县级）。)
	   insert into city values('海口市',25,'H') insert into city
	   values('三亚市',25,'S') insert into city values('五指山市',25,'W') insert into
	   city values('琼海市',25,'Q') insert into city values('儋州市',25,'D') insert
	   into city values('文昌市',25,'W') insert into city values('万宁市',25,'W')
	   insert into city values('东方市',25,'D') insert into city
	   values('澄迈县',25,'C') insert into city values('定安县',25,'D') insert into
	   city values('屯昌县',25,'T') insert into city values('临高县',25,'L') insert
	   into city values('白沙黎族自治县',25,'B') insert into city
	   values('昌江黎族自治县',25,'C') insert into city values('乐东黎族自治县',25,'L') insert
	   into city values('陵水黎族自治县',25,'L') insert into city
	   values('保亭黎族苗族自治县',25,'B') insert into city values('琼中黎族苗族自治县',25,'Q')
	   --26云南省(2006年辖：8个地级市、8个自治州；12个市辖区、9个县级市、79个县、29个自治县。) insert into city
	   values('昆明市',26,'K') insert into city values('曲靖市',26,'Q') insert into
	   city values('玉溪市',26,'Y') insert into city values('保山市',26,'B') insert
	   into city values('昭通市',26,'Z') insert into city values('丽江市',26,'L')
	   insert into city values('思茅市',26,'S') insert into city
	   values('临沧市',26,'L') insert into city values('文山壮族苗族自治州',26,'W') insert
	   into city values('红河哈尼族彝族自治州',26,'H') insert into city
	   values('西双版纳傣族自治州',26,'X') insert into city values('楚雄彝族自治州',26,'C')
	   insert into city values('大理白族自治州',26,'D') insert into city
	   values('德宏傣族景颇族自治州',26,'D') insert into city values('怒江傈傈族自治州',26,'N')
	   insert into city values('迪庆藏族自治州',26,'D')
	   --27青海省(2006年辖：1个地级市、1个地区、6个自治州；4个市辖区、2个县级市、30个县、7个自治县。) insert into city
	   values('西宁市',27,'X') insert into city values('海东地区',27,'H') insert into
	   city values('海北藏族自治州',27,'H') insert into city values('黄南藏族自治州',27,'H')
	   insert into city values('海南藏族自治州',27,'H') insert into city
	   values('果洛藏族自治州',27,'G') insert into city values('玉树藏族自治州',27,'Y') insert
	   into city values('海西蒙古族藏族自治州',27,'H')
	   --28陕西省(2006年辖：10个地级市；24个市辖区、3个县级市、80个县。) insert into city
	   values('西安市',28,'X') insert into city values('铜川市',28,'T') insert into
	   city values('宝鸡市',28,'B') insert into city values('咸阳市',28,'X') insert
	   into city values('渭南市',28,'W') insert into city values('延安市',28,'Y')
	   insert into city values('汉中市',28,'H') insert into city
	   values('榆林市',28,'Y') insert into city values('安康市',28,'A') insert into
	   city values('商洛市',28,'S')
	   --29广西壮族自治区(2005年辖：14个地级市；34个市辖区、7个县级市、56个县、12个自治县。) insert into city
	   values('南宁市',29,'N') insert into city values('柳州市',29,'L') insert into
	   city values('桂林市',29,'G') insert into city values('梧州市',29,'W') insert
	   into city values('北海市',29,'B') insert into city values('防城港市',29,'F')
	   insert into city values('钦州市',29,'Q') insert into city
	   values('贵港市',29,'G') insert into city values('玉林市',29,'Y') insert into
	   city values('百色市',29,'B') insert into city values('贺州市',29,'H') insert
	   into city values('河池市',29,'H') insert into city values('来宾市',29,'L')
	   insert into city values('崇左市',29,'C')
	   --30西藏自治区(2005年辖：1个地级市、6个地区；1个市辖区、1个县级市、71个县。) insert into city
	   values('拉萨市',30,'L') insert into city values('那曲地区',30,'N') insert into
	   city values('昌都地区',30,'C') insert into city values('山南地区',30,'S') insert
	   into city values('日喀则地区',30,'R') insert into city values('阿里地区',30,'A')
	   insert into city values('林芝地区',30,'L') --31宁夏回族自治区 insert into city
	   values('银川市',31,'Y') insert into city values('石嘴山市',31,'S') insert into
	   city values('吴忠市',31,'W') insert into city values('固原市',31,'G') insert
	   into city values('中卫市',31,'Z')
	   --32新疆维吾尔自治区(2005年辖：2个地级市、7个地区、5个自治州；11个市辖区、20个县级市、62个县、6个自治县) insert
	   into city values('乌鲁木齐市',32,'W') insert into city values('克拉玛依市',32,'K')
	   insert into city values('石河子市　',32,'S') insert into city
	   values('阿拉尔市',32,'A') insert into city values('图木舒克市',32,'T') insert into
	   city values('五家渠市',32,'W') insert into city values('吐鲁番市',32,'K') insert
	   into city values('阿克苏市',32,'A') insert into city values('喀什市',32,'K')
	   insert into city values('哈密市',32,'H') insert into city
	   values('和田市',32,'H') insert into city values('阿图什市',32,'A') insert into
	   city values('库尔勒市',32,'K') insert into city values('昌吉市　',32,'K') insert
	   into city values('阜康市',32,'F') insert into city values('米泉市',32,'M')
	   insert into city values('博乐市',32,'B') insert into city
	   values('伊宁市',32,'Y') insert into city values('奎屯市',32,'K') insert into
	   city values('塔城市',32,'T') insert into city values('乌苏市',32,'W') insert
	   into city values('阿勒泰市',32,'A')
	   --33内蒙古自治区(2006年，辖：9个地级市、3个盟；21个市辖区、11个县级市、17个县、49个旗、3个自治旗。) insert into
	   city values('呼和浩特市',33,'F') insert into city values('包头市',33,'B') insert
	   into city values('乌海市',33,'W') insert into city values('赤峰市',33,'C')
	   insert into city values('通辽市',33,'T') insert into city
	   values('鄂尔多斯市',33,'E') insert into city values('呼伦贝尔市',33,'F') insert
	   into city values('巴彦淖尔市',33,'B') insert into city values('乌兰察布市',33,'W')
	   insert into city values('锡林郭勒盟',33,'X') insert into city
	   values('兴安盟',33,'X') insert into city values('阿拉善盟',33,'A') --34澳门特别行政区
	   insert into city values('澳门特别行政区',34,'A') --35香港特别行政区 insert into city
	   values('香港特别行政区',35,'X')
	  */
}
