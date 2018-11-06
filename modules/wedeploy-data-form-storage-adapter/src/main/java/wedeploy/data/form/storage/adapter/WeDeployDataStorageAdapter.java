package wedeploy.data.form.storage.adapter;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.BaseStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageAdapter;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidator;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import com.wedeploy.android.WeDeploy;
import com.wedeploy.android.exception.WeDeployException;
import com.wedeploy.android.transport.Response;

import org.json.JSONObject;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Queiroz
 */
@Component(service = StorageAdapter.class)
public class WeDeployDataStorageAdapter extends BaseStorageAdapter {

	@Override
	public String getStorageType() {
		return "WeDeploy Data";
	}

	protected void createFormEntry(long fileId, DDMFormValues formValues)
		throws WeDeployException {

		String serializedDDMFormValues = _ddmFormValuesJSONSerializer.serialize(
			formValues);

		JSONObject jsonObject = new JSONObject(serializedDDMFormValues);

		jsonObject.put("id", String.valueOf(fileId));

		_weDeploy
			.data("https://storage-devcon2018demo.wedeploy.io")
			.create("formEntries", jsonObject)
			.execute();
	}

	@Override
	protected long doCreate(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws Exception {

		validate(ddmFormValues, serviceContext);

		long fileId = _counterLocalService.increment();

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getLatestStructureVersion(
				ddmStructureId);

		long classNameId = PortalUtil.getClassNameId(
			WeDeployDataStorageAdapter.class.getName());

		_ddmStorageLinkLocalService.addStorageLink(
			classNameId, fileId, ddmStructureVersion.getStructureVersionId(),
			serviceContext);

		createFormEntry(fileId, ddmFormValues);

		return fileId;
	}

	@Override
	protected void doDeleteByClass(long classPK) throws Exception {
		_weDeploy
			.data("https://storage-devcon2018demo.wedeploy.io")
			.delete("formEntries/" + classPK)
			.execute();

		_ddmStorageLinkLocalService.deleteClassStorageLink(classPK);
	}

	@Override
	protected void doDeleteByDDMStructure(long ddmStructureId)
		throws Exception {

		_weDeploy
			.data("https://storage-devcon2018demo.wedeploy.io")
			.delete("formEntries")
			.execute();

		_ddmStorageLinkLocalService.deleteStructureStorageLinks(ddmStructureId);
	}

	@Override
	protected DDMFormValues doGetDDMFormValues(long classPK) throws Exception {
		DDMStorageLink storageLink =
			_ddmStorageLinkLocalService.getClassStorageLink(classPK);

		DDMStructureVersion structureVersion =
			_ddmStructureVersionLocalService.getStructureVersion(
				storageLink.getStructureVersionId());

		WeDeploy weDeploy = new WeDeploy.Builder().build();

		Response response = weDeploy
			.data("https://storage-devcon2018demo.wedeploy.io")
			.get("formEntries/" + storageLink.getClassPK())
			.execute();

		String serializedDDMFormValues = response.getBody();

		return _ddmFormValuesJSONDeserializer.deserialize(
			structureVersion.getDDMForm(), serializedDDMFormValues);
	}

	@Override
	protected void doUpdate(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws Exception {

		validate(ddmFormValues, serviceContext);

		updateFormEntry(classPK, ddmFormValues);
	}

	protected void updateFormEntry(long fileId, DDMFormValues formValues)
		throws WeDeployException {

		String serializedDDMFormValues = _ddmFormValuesJSONSerializer.serialize(
			formValues);

		JSONObject jsonObject = new JSONObject(serializedDDMFormValues);

		_weDeploy
			.data("https://storage-devcon2018demo.wedeploy.io")
			.replace("formEntries/" + fileId, jsonObject)
			.execute();
	}

	protected void validate(
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws Exception {

		boolean validateDDMFormValues = GetterUtil.getBoolean(
			serviceContext.getAttribute("validateDDMFormValues"), true);

		if (!validateDDMFormValues) {
			return;
		}

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;

	@Reference
	private DDMFormValuesJSONSerializer _ddmFormValuesJSONSerializer;

	@Reference
	private DDMFormValuesValidator _ddmFormValuesValidator;

	@Reference
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	private WeDeploy _weDeploy = new WeDeploy.Builder().build();

}