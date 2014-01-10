<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<result>
			<HtmlTitle>
				<xsl:value-of select=".//table[@height='239']/tr[1]" />
			</HtmlTitle>
			<br />
			<author>
				<br />
				<xsl:value-of select=".//table[@height='239']/tr[2]" />
				<br />
			</author>
			<content>
				<br />
				<xsl:copy-of select=".//table[@height='239']/tr[3]" />
				<br />
			</content>
			<bottom>
				<br />
					<xsl:value-of select=".//table[@height='239']/tr[4]" />
				<br />
			</bottom>
		</result>
	</xsl:template>
</xsl:stylesheet>