<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>

<tr class="noBorder">
  <th><label for="versionFormat">Version number:</label></th>
  <td>
    <props:textProperty name="versionFormat" className="longField" style="width: 100%;"/>
    <span class="error" id="error_versionFormat"/>
    <span class="smallNote">Specify version number</span>
  </td>
</tr>

<tr class="noBorder">
  <td colspan="2">
    Updates the version number in the Visual Basic project file.
    Changes are reverted an the end of the build.
  </td>
</tr>
