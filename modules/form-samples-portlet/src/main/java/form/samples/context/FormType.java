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

/**
 * @author Marcellus Tavares
 */
public enum FormType {

	AUTOFILL_REGIONS("autofillRegions"), AUTOFILL_ZIP("autofillZip"), CALCULATE("calculate"),
	DATE_VALIDATION("dateValidation"), ENABLE_DISABLE("enableDisable"),
	REQUIRE("require"), SHOW_HIDE("showHide");

	@Override
	public String toString() {
		return _value;
	}

	private FormType(String value) {
		_value = value;
	}

	private String _value;

}