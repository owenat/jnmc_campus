<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:template match="/">
		<result>
			<item1>
				<title>济医新闻</title>
				<content>
				<xsl:for-each select="//ul[@class='dyn_left_ul']/li[position()&gt;1]">
					<n>
					<a href="{./a/@href}"><xsl:value-of select="./a"/></a>
					<time><xsl:value-of select="./span[4]"/></time>
					</n>
				</xsl:for-each>
				</content>
			</item1>
			<item2>
			<title>通知公告</title>
				<content>
				<xsl:for-each select="//ul[@id='tzggUL']/li">
					<n>
					<a href="{./a/@href}"><xsl:value-of select="./a"/></a>
					<time><xsl:value-of select="./span"/></time>
					</n>
				</xsl:for-each>
				</content>
			</item2>
		</result>
	</xsl:template>
</xsl:stylesheet>