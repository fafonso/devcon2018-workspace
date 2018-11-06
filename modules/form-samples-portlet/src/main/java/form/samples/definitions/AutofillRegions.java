package form.samples.definitions;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.annotations.DDMFormRule;

@DDMForm(
	rules = {
		@DDMFormRule(
			actions = {
				"call('152892', 'countryId=country', 'region=Regions')"
			},
			condition = "not(isEmpty(getValue('country')))"
		)
	}
)
@DDMFormLayout(
		paginationMode = com.liferay.dynamic.data.mapping.model.DDMFormLayout.SINGLE_PAGE_MODE,
		value = {
			@DDMFormLayoutPage(
				value = {
					@DDMFormLayoutRow(
						{
							@DDMFormLayoutColumn(
								size = 6,
								value = {"country"}
							),
							@DDMFormLayoutColumn(
								size = 6,
								value = {"region"}
							)
						}
					)
				}
			)
		}
	)
public interface AutofillRegions {

	@DDMFormField(
		label="Country", type = "select",
		properties = {
			"dataSourceType=data-provider",
			"ddmDataProviderInstanceId=[\"152843\"]",
			"ddmDataProviderInstanceOutput=[\"Country Name\"]"
		}
	)
	public String country();

	@DDMFormField(
		label="Region", type = "select", properties = {"dataSourceType=from-autofill"}
	)
	public String region();
}
