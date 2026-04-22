<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
  <html>
  <body>
    <h2>Recipe List</h2>
    <table border="1">
      <tr bgcolor="#9acd32">
        <th>Title</th>
        <th>Difficulty</th>
      </tr>
      <xsl:for-each select="recipe_system/recipes/recipe">
        <xsl:variable name="userSkill" select="/recipe_system/users/user[1]/skill_level"/>
        <tr>
          <xsl:attribute name="style">
            <xsl:choose>
              <xsl:when test="difficulty = $userSkill">background-color: yellow;</xsl:when>
              <xsl:otherwise>background-color: green;</xsl:otherwise>
            </xsl:choose>
          </xsl:attribute>
          <td><xsl:value-of select="title"/></td>
          <td><xsl:value-of select="difficulty"/></td>
        </tr>
      </xsl:for-each>
    </table>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>