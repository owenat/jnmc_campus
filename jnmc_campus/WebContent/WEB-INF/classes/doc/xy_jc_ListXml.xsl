<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:template match="/">
		<result>
			<HtmlTitle>
		         <xsl:value-of select=".//div[@class='subtitle']/span/a[2]"/>
			</HtmlTitle>
			<xsl:for-each select="//div[@class='listbox2']//li">
				<item>
					<a href="{./a/@href}">
						<xsl:value-of select="./a"/>
					</a>
					<time><xsl:value-of select="./span"/></time>
				</item>
			</xsl:for-each>		

			<pages>
				<xsl:for-each select="//div[@id='pagelink']//a">
					<a href="{./@href}">
						<xsl:value-of select="."/>
					</a>
				</xsl:for-each>	
			</pages>	
		</result>
	</xsl:template>
</xsl:stylesheet>