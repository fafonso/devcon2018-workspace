/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package form.samples.context;

import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingException;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import form.samples.definitions.*;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class FormSampleDisplayContext {

	public FormSampleDisplayContext(
		DDMFormRenderer ddmFormRenderer, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_ddmFormRenderer = ddmFormRenderer;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public String getFormHTML() {
		DDMForm ddmForm = getDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			createDDMFormRenderingContext(
				_renderRequest, _renderResponse, ddmForm);

		try {
			return _ddmFormRenderer.render(
				ddmForm, getDDMFormLayout(), ddmFormRenderingContext);
		}
		catch (DDMFormRenderingException ddmfre) {
			ddmfre.printStackTrace();

			return "Unable to render the form";
		}
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		DDMForm ddmForm) {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setHttpServletRequest(
			PortalUtil.getHttpServletRequest(renderRequest));
		ddmFormRenderingContext.setHttpServletResponse(
			PortalUtil.getHttpServletResponse(renderResponse));

		ThemeDisplay themeDisplay = getThemeDisplay(renderRequest);

		ddmFormRenderingContext.setLocale(themeDisplay.getLocale());

		ddmFormRenderingContext.setPortletNamespace(
			renderResponse.getNamespace());

		return ddmFormRenderingContext;
	}

	protected DDMForm getDDMForm() {
		String type = _renderRequest.getParameter("type");

		return DDMFormFactory.create(_formsMap.get(type));
	}

	protected DDMFormLayout getDDMFormLayout() {
		String type = _renderRequest.getParameter("type");

		return DDMFormLayoutFactory.create(_formsMap.get(type));
	}

	protected ThemeDisplay getThemeDisplay(RenderRequest renderRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay;
	}

	private DDMFormRenderer _ddmFormRenderer;
	private RenderRequest _renderRequest;
	private RenderResponse _renderResponse;

	private static Map<String, Class<?>> _formsMap = new HashMap<>();

	static {
		_formsMap.put(FormType.AUTOFILL_REGIONS.toString(), AutofillRegions.class);
		_formsMap.put(FormType.AUTOFILL_ZIP.toString(), AutofillZip.class);
		_formsMap.put(
			FormType.DATE_VALIDATION.toString(), DateValidation.class);
		_formsMap.put(FormType.REQUIRE.toString(), Require.class);
		_formsMap.put(FormType.SHOW_HIDE.toString(), ShowHide.class);
		_formsMap.put(FormType.ENABLE_DISABLE.toString(), EnableDisable.class);
		_formsMap.put(FormType.CALCULATE.toString(), Calculate.class);
	}

}