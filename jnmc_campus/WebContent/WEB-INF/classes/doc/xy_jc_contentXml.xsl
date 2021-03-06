<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<result>
			<HtmlTitle>
				<xsl:copy-of select=".//div[@class='texttitle']" />
			</HtmlTitle>
			<br />
			<author>
				<br />
					<xsl:copy-of select=".//div[@class='info']" />
				<br />
			</author>
			<content>
				<br />
					<xsl:copy-of select=".//div[@class='paragraph']" />
				<br />
			</content>
		</result>
	</xsl:template>
</xsl:stylesheet>