<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE result SYSTEM "constant.dtd">
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:template match="/">
		<title>济宁医学院</title>
		<div class="pubBox news" id="news">
		<xsl:for-each select="result/item[1]">		
				<div class="hd touchTit"><h2>				
					<xsl:value-of select="./title"/>
				</h2></div>
				<div class="newsBox">
				<div class="newsBox1 touchCon">
					<div id="news1" class="newsBoxDiv">             
					<div class="ft">
						<ul>
				<xsl:for-each select="./content/n">
				<li class="flag0">
					<xsl:element name="a">
						<xsl:attribute name="href">
							<xsl:value-of select="./a/@href"/>
						</xsl:attribute>
						<xsl:value-of select="./a"/>
					</xsl:element>
						  <xsl:text> </xsl:text><xsl:value-of select="./time"/>
						</li>
				</xsl:for-each>
				</ul>
					<hr/>
			<p width="100%">
				<xsl:for-each select="./page/a">
					&#160;<a style="margin:auto auto" href="{./@href}">
						<xsl:value-of select="."/>
					</a>
				</xsl:for-each>
			</p>
				</div>
				</div>
				</div>
				</div>
			</xsl:for-each>
		</div>
	</xsl:template>
</xsl:stylesheet>