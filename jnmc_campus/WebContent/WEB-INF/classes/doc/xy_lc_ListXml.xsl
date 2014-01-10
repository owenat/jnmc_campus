<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:template match="/">
		<result>
			<HtmlTitle>
		         <xsl:value-of select=".//table[@id='table8']//td/a[3]"/>
			</HtmlTitle>
			<xsl:for-each select=".//table[@id='table38']/tr[1]//tr">
				<item>
					<a href="{./td[2]/a[2]/@href}">
						<xsl:value-of select="./td[2]/a[2]"/>
					</a>
					<time><xsl:value-of select="./td[4]"/></time>
				</item>
			</xsl:for-each>		

		</result>
	</xsl:template>
</xsl:stylesheet>