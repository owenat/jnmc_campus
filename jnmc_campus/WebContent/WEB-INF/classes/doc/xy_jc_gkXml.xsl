<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<result>
			<content>
					<xsl:copy-of select=".//div[@class='rightcontent']" />
				<br />
			</content>
		</result>
	</xsl:template>
</xsl:stylesheet>