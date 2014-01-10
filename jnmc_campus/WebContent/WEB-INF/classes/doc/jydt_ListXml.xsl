<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:template match="/">
		<result>
			<HtmlTitle>
		         <xsl:value-of select=".//h1"/>
			</HtmlTitle>
			<xsl:for-each select="//ul[@class='artical_list']/li">
				<item>
					<a href="{./a/@href}">
						<xsl:value-of select="./a"/>
					</a>
					<time><xsl:value-of select="./span[3]"/></time>
				</item>
			</xsl:for-each>		

			<pages>
				<xsl:for-each select="//div[@class='num']//a">
					<a href="{./@href}">
						<xsl:value-of select="."/>
					</a>
				</xsl:for-each>	
			</pages>	
		</result>
	</xsl:template>
</xsl:stylesheet>
