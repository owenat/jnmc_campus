<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE result SYSTEM "constant.dtd">
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:template match="/">
		<title>济宁医学院</title>
		<div class="swap">
		<div align="center" style="margin-top:16px;margin-bottom:16px;font-family: 黑体;font-size: 16px;color: #003399;">
			<xsl:value-of select="result/HtmlTitle"/>
		</div>
		<div align="center">
			<xsl:for-each select="result/author">
				<p><xsl:value-of select="."/></p>
			</xsl:for-each>
		</div>
		<div align="left">
			<xsl:copy-of select="result/content" />
		</div>
	     <div align="right">
			<xsl:copy-of select="result/bottom" />
		</div>
		</div>
	</xsl:template>
</xsl:stylesheet>