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
			actions = {"setVisible('returnDate', false)"},
			condition = "equals(getValue('journeyType'), 'ow')"
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
							value = {
								"from", "to", "journeyType", "departDate",
								"returnDate"
							}
						)
					}
				)
			}
		)
	}
)
public interface ShowHide {

	@DDMFormField(label="Depart Date", type="date")
	public String departDate();

	@DDMFormField(label="From")
	public String from();

	@DDMFormField(
		label="Journey Type",
		optionLabels = {"Return", "One-way"}, optionValues = {"rt", "ow"},
		properties = "inline=true", type = "radio"
	)
	public String journeyType();

	@DDMFormField(label="Return Date", type="date")
	public String returnDate();

	@DDMFormField(label="To")
	public String to();

}