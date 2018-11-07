<%@ include file="init.jsp" %>

<p>
	<b>Forms Taglib Demo</b>
</p>

<ul class="nav nav-underline" role="tablist">
	<li class="nav-item">
		<a aria-controls="navUnderlineFields" aria-expanded="true" class="active nav-link" data-toggle="tab" href="#navUnderlineFields" id="navUnderlineFieldsTab" role="tab">Form Tab #1</a>
	</li>
	<li class="nav-item">
		<a aria-controls="navUnderlineSettings" class="nav-link" data-toggle="tab" href="#navUnderlineSettings" id="navUnderlineSettingsTab" role="tab">Form Tab #2</a>
	</li>
</ul>
<div class="tab-content">
	<div aria-labelledby="navUnderlineFieldsTab" class="active fade show tab-pane" id="navUnderlineFields" role="tabpanel">
		<liferay-form:ddm-form-renderer
				ddmFormInstanceId="37414"
				showSubmitButton="false"
				showFormBasicInfo="false"
		/>
	</div>
	<div aria-labelledby="navUnderlineSettingsTab" class="fade tab-pane" id="navUnderlineSettings" role="tabpanel">
		<liferay-form:ddm-form-renderer
				ddmFormInstanceId="37420"
				showSubmitButton="true"
				showFormBasicInfo="false"
		/>
	</div>
</div>
