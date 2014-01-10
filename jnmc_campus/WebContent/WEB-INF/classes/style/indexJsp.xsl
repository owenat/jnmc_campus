<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE result SYSTEM "constant.dtd">
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:template match="/">
		<title>济宁医学院</title>
		<div class="pubBox news" id="news">
		<xsl:for-each select="result/item1">		
				<div class="hd touchTit"><h2>				
					济医新闻
				</h2><div class="stitle">
				<a href="http://localhost:8080/jnmc/show.jsp?webUrl=http://www.jnmc.edu.cn/jydt/jydt/">&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
				更多</a></div></div>
				<div class="newsBox">
				<div class="newsBox1 touchCon">
					<div id="news1" class="newsBoxDiv">             
					<div class="ft">
						<ul>
				<xsl:for-each select="./content/n">
				<li class="flag0">
					<xsl:element name="a">
						<xsl:attribute name="href">
							<xsl:value-of select="./a/@href"/>
						</xsl:attribute>
						<xsl:value-of select="./a"/>
					</xsl:element>
						<xsl:value-of select="./time"/>
						</li>
				</xsl:for-each>
				</ul>
				</div>
				</div>
				</div>
				</div>
			</xsl:for-each>
			<!--  -->
			<xsl:for-each select="result/item2">
			<div class="pubBox">
			<div class="hd touchTit"><h2>通知公告</h2><div class="stitle">
			<a href="http://localhost:8080/jnmc/show.jsp?webUrl=http://www.jnmc.edu.cn/xntz/xntz/">&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
				更多</a></div></div>
				<div class="ft">
				<ul>	
				<xsl:for-each select="./content/n">	
					<li class="flag0">
					<xsl:element name="a">
						<xsl:attribute name="href">
							<xsl:value-of select="./a/@href"/>
						</xsl:attribute>
						<xsl:value-of select="./a"/>
					</xsl:element>
						<xsl:value-of select="./time"/>
						</li>
				</xsl:for-each>
				</ul>
				</div>
				</div>
			</xsl:for-each>
		</div>

	</xsl:template>
</xsl:stylesheet>