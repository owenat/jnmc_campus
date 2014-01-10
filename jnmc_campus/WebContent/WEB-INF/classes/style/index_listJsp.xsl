<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
	<div class="bg">
	<div class="zt">
	<div class="hangju">
		<!-- TODO: Auto-generated template -->
		<div >
				<div style="margin:5px auto" class="hd touchTit"><h3>				
					&#160;<xsl:value-of select="result/HtmlTitle"/>
				</h3>
			   </div>		
			<xsl:for-each select="result/item">
				<p >
					<div style="margin:3px auto">
				&#160;<a href="{./a/@href}">
							<xsl:value-of select="./a"/>
						</a>
						&#160;&#160;
						<time><xsl:value-of select="./time"/></time>
					</div>
				</p>
			</xsl:for-each>
			<hr/>
			<p width="100%">
				<xsl:for-each select="result/pages/a">
					&#160;<a style="margin:auto auto" href="{./@href}">
						<xsl:value-of select="."/>
					</a>
				</xsl:for-each>
			</p>
		</div>
		</div>
		</div>
		</div>
	</xsl:template>
</xsl:stylesheet> 