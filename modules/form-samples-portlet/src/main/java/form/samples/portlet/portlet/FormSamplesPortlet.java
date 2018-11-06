package form.samples.portlet.portlet;

import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.portal.kernel.util.WebKeys;
import form.samples.context.FormSampleDisplayContext;
import form.samples.portlet.constants.FormSamplesPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;

/**
 * @author pedroqueiroz94
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + FormSamplesPortletKeys.FormSamples,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class FormSamplesPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		FormSampleDisplayContext formSampleDisplayContext =
				new FormSampleDisplayContext(
						ddmFormRenderer, renderRequest, renderResponse);

		renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT, formSampleDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Reference
	protected DDMFormRenderer ddmFormRenderer;
}