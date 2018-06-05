# Algorithm

记录一些算法的实现

# 使用SOP

1. 添加新的算法名称到arrays.xml


	    <string-array name="algorithm_list">
	        <item> 阶梯树 </item>
	        <item> 多音字转拼音 </item>
	        <item> 链表 </item>
	        <item> 二叉树 </item>
	    </string-array>

2. 添加算法实现

	- algorithm添加自己的文件夹和实现
	- 算法类需要实现IAlgorithm接口

3. 增加算法工厂方法


		public final class AlgorithmFactory {
		    private List<IAlgorithm> list = new ArrayList<>();
		
		    public AlgorithmFactory() {
		        list.add(JieTiTree.getInstance());
		        list.add(ChineseToPinYin.getInstance());
		        list.add(new NodeAlgorithm());
			    //============Add new algoritm creation here=====================
		    }

# Algorithm

- [Algorithm 目录](http://vivianking6855.github.io/2018/05/08/Algorithm-Index/)



