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

package form.samples.definitions;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.annotations.DDMFormRule;

/**
 * @author Marcellus Tavares
 */
@DDMForm(
	rules = {
		@DDMFormRule(
			actions = {
				"setEnabled('country', false)", "setEnabled('state', false)",
				"setEnabled('place', false)"
			}
		),
		@DDMFormRule(
			actions = {
				"call('43c116fd-2879-94b2-484b-97472b097ab5', 'zipcode=zipCode', 'country=Country;state=State;place=Place')"
			},
			condition = "not(equals(getValue('zipCode'), ''))"
		),
		@DDMFormRule(
			actions = {
				"setValue('country', '')", "setValue('state', '')",
				"setValue('place', '')"
			},
			condition = "equals(getValue('zipCode'), '')"
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
							size = 12,
							value = {"zipCode", "country", "state", "place"}
						)
					}
				)
			}
		)
	}
)
public interface AutofillZip {

	@DDMFormField(label="Country")
	public String country();

	@DDMFormField(label="Place")
	public String place();

	@DDMFormField(label="State")
	public String state();

	@DDMFormField(label="Zip code")
	public String zipCode();

}