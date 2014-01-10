移动端网站实现
地址：http://www.jnmc.edu.cn/
====================================
流程：
1 抓取网络源码
2 标签过滤
3 JTidy处理成Dom
4 Dom树过滤 处理a标签（相对地址转绝对地址）img标签（实现图片缩放）
5 渲染操作xml转换为html

缓存机制：对网页正文内容进行保存 下次点击的时候重定向到保存的地址
用到 Servlet容器 正则匹配 Sax解析

================================
配置文件
afilter：A标签补全链接 正则匹配
imgFilter：IMG标签补全链接 正则匹配
config：XSLT抽取模板 正则匹配
htmlFilter: 过滤标签 正则匹配
constant:全局变量
web.xml servlet配置文件

