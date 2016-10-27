package com.salmon.test.step_definitions.dataload.product;

import com.salmon.test.dataload.product.CommercialBanJsonGenerator;
import com.salmon.test.enums.DatabaseQueries;
import com.salmon.test.framework.helpers.DatabaseHelper;
import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.dataload.pim.product.CommercialBanningPimModel;
import com.salmon.test.models.dataload.pim.product.SaleDestinationInfo;
import com.salmon.test.models.dataload.wcs.product.CommercialBanningWcsModel;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.salmon.test.dataload.product.ProductWcsFileChecker.verifyWcsProductBanningMappingFile;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by Developer on 11/10/2016.
 */
public class CommercialBanningSteps {
    static final Logger LOG = LoggerFactory.getLogger(CommercialBanningSteps.class);
    private List<CommercialBanningPimModel> commercialBanningModelList;
    private List<SaleDestinationInfo> destinationInfoList;

    private CommercialBanJsonGenerator commercialBanJsonGenerator;

    public CommercialBanningSteps(CommercialBanJsonGenerator commercialBanJsonGenerator) {
        this.commercialBanJsonGenerator = commercialBanJsonGenerator;
    }


    @Given("^provided list of country for banning$")
    public void providedListOfCountryForBanning(List<SaleDestinationInfo> salesDestinationInfos) throws Throwable {
        this.destinationInfoList = salesDestinationInfos;

    }

    @And("^I create banning product Json file$")
    public void iCreateBanningProductJsonFile(List<CommercialBanningPimModel> commercialBanningPimModelList) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            commercialBanningModelList = commercialBanningPimModelList;
            commercialBanningModelList.get(0).setSaleDestinationInfo(this.destinationInfoList);
            commercialBanningModelList.get(0).setBrandChannel(UrlBuilder.readValueFromConfig(brand + "."+ commercialBanningPimModelList.get(0).getBrandChannel()));
            commercialBanJsonGenerator.createCommercialBanningJson(commercialBanningModelList.get(0));
        }
    }

    @Then("^verify WCS(.*)product file has the correct format:$")
    public void verifyWCSBanningProductFileHasTheCorrectFormat(List<CommercialBanningWcsModel> banningWcsModels, String interfaceName) throws Throwable {
        verifyWcsProductBanningMappingFile(banningWcsModels, interfaceName);
    }


    @And("^verify the data has been loaded to catentryattr$")
    public void verifyTheDataHasBeenLoadedToCatentryattr(List<CommercialBanningWcsModel> banningWcsModels) throws Throwable {
        List<HashMap> actCatEntryAttrSqlResult;
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            for (CommercialBanningWcsModel banningWcsModel : banningWcsModels) {
                actCatEntryAttrSqlResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_CATENTRYATTR.getQuery().replace("|gtin|", banningWcsModel.getPartNumber()));
                for (HashMap catEntryAttrSqlResult : actCatEntryAttrSqlResult) {
                    assertThat(catEntryAttrSqlResult.get("PARTNUMBER").toString().contains(banningWcsModel.getPartNumber())).isTrue();
                    assertThat(catEntryAttrSqlResult.get("ATTRIBUTEIDENTIFIER").toString().contains(banningWcsModel.getAttributeIdentifier())).isTrue();
                    assertThat(catEntryAttrSqlResult.get("ATTRIBUTEVALUEIDENTIFIER").toString().contains(banningWcsModel.getAttributeValueIdentifier())).isTrue();
                }
            }
        }
    }
}
