package helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.LicenseResponse;
import org.openapitools.client.model.TeamResponse;

import java.util.ArrayList;
import java.util.List;

import static data.PropertiesSupplier.prop;
import static java.lang.Thread.currentThread;

public class CustomerHelper extends BaseHelper {
    private Log log = LogFactory.getLog(CustomerHelper.class);
    public List<LicenseResponse> getLicenseResponseById(List<LicenseResponse> licenses) {
        return licenses.stream()
                .map(this::getLicenseResponseByLicenseIdSafe)
                .peek(lr -> log.info(currentThread().getId() + ": " + lr.getLicenseId() + " belongs to "
                        + lr.getTeam().getId()))
                .peek(log::info)
                .toList();
    }
    public List<LicenseResponse> getLicenseResponseById(LicenseResponse license) {
        return getLicenseResponseById(new ArrayList<>(){{add(license);}});
    }
    private LicenseResponse getLicenseResponseByLicenseIdSafe(LicenseResponse l) {
        try {
            return apiOrgAdmin.getLicensesApi().getByLicenseId(l.getLicenseId(), prop.getProperty("x.customer.code"));
        } catch (ApiException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public List<LicenseResponse> setTeamForLicenses(List<LicenseResponse> licences, TeamResponse teamResponse) {
        return licences.stream()
                .peek(l -> l.setTeam(teamResponse))
                .toList();
    }
}
