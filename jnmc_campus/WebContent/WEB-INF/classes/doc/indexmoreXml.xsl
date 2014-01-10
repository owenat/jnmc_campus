<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:template match="/">
		<result>
			<HtmlTitle>
		         新闻
			</HtmlTitle>
			<xsl:for-each select="//table[@class='indexlist_2']//tr[position()&lt; last()]">
				<item>
					<a href="{./td/a/@href}">
						<xsl:value-of select="./td/a"/>
					</a>
					<time><xsl:value-of select="./td[2]"/></time>
				</item>
			</xsl:for-each>		

			<pages>
				<xsl:for-each select="//table[@class='indexlist_2']//tr[position() = last()]/td/a">
					<a href="{./@href}">
						<xsl:value-of select="."/>
					</a>
				</xsl:for-each>	
			</pages>	
		</result>
	</xsl:template>
</xsl:stylesheet>
