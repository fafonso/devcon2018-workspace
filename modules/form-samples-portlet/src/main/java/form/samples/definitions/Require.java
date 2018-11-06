/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
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
			actions = {"setRequired('insuranceCoverage', true)"},
			condition = "equals(getValue('age'), 'groupA')"
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
								"pickUpLocation", "returnDifferentLocation",
								"pickUpDate", "returnDate", "age",
								"insuranceCoverage"
							}
						)
					}
				)
			}
		)
	}
)
public interface Require {

	@DDMFormField(
		optionLabels = {"18-21 yo", "22-30 yo", "31-40 yo", "41+ yo"},
		optionValues = {"groupA", "groupB", "groupC", "groupD"}, type = "select"
	)
	public String age();

	@DDMFormField(
		optionLabels = {
			"Loss Damage Waiver (LDW)", "Collision Damage Waiver (CDW)",
			"Personal Accident Insurance (PAI)"
		},
		optionValues = {"ldw", "cdw", "pai"}, properties = "inline=false",
		type = "checkbox_multiple"
	)
	public String insuranceCoverage();

	@DDMFormField(type = "date")
	public String pickUpDate();

	@DDMFormField
	public String pickUpLocation();

	@DDMFormField(type = "date")
	public String returnDate();

	@DDMFormField(
		optionLabels = {"Return in different location"}, optionValues = {"rt"},
		type = "checkbox_multiple"
	)
	public String returnDifferentLocation();

}