package uk.gov.dft.bluebadge.webapp.la.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.ReferenceDataApiClient;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataDomainEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.RefDataGroupEnum;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public class ReferenceDataServiceTest {

  @Mock private ReferenceDataApiClient referenceDataManagementApiClientMock;

  private ReferenceDataService referenceDataService;

  private ReferenceData referenceData1;
  private ReferenceData referenceData2;
  private ReferenceData referenceData3;
  private ReferenceData referenceData4;
  private ReferenceData referenceData5;
  private ReferenceData referenceData6;
  private ReferenceData referenceData7;
  private ReferenceData referenceData8;
  private ReferenceData referenceData9;
  private ReferenceData referenceData10;
  private List<ReferenceData> referenceData;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    referenceDataService = new ReferenceDataService(referenceDataManagementApiClientMock);

    referenceData1 = buildReferenceData(RefDataGroupEnum.ELIGIBILITY.getGroupKey(), 1);
    referenceData2 = buildReferenceData(RefDataGroupEnum.GENDER.getGroupKey(), 2);
    referenceData3 = buildReferenceData(RefDataGroupEnum.ELIGIBILITY.getGroupKey(), 3);
    referenceData4 = buildReferenceData(RefDataGroupEnum.GENDER.getGroupKey(), 4);
    referenceData5 = buildReferenceData(RefDataGroupEnum.APP_SOURCE.getGroupKey(), 5);
    referenceData6 = buildReferenceData(RefDataGroupEnum.APP_SOURCE.getGroupKey(), 6);
    referenceData7 = buildReferenceData(RefDataGroupEnum.DELIVER_TO.getGroupKey(), 7);
    referenceData8 = buildReferenceData(RefDataGroupEnum.DELIVER_TO.getGroupKey(), 8);
    referenceData9 = buildReferenceData(RefDataGroupEnum.DELIVERY_OPTIONS.getGroupKey(), 9);
    referenceData10 = buildReferenceData(RefDataGroupEnum.DELIVERY_OPTIONS.getGroupKey(), 10);

    referenceData =
        Lists.newArrayList(
            referenceData1,
            referenceData2,
            referenceData3,
            referenceData4,
            referenceData5,
            referenceData6,
            referenceData7,
            referenceData8,
            referenceData9,
            referenceData10);
    when(referenceDataManagementApiClientMock.retrieveReferenceData(RefDataDomainEnum.BADGE))
        .thenReturn(referenceData);
  }

  @Test
  public void retrieveEligibilities_ShouldReturnEligibilities() {
    List<ReferenceData> eligibilities = referenceDataService.retrieveEligilities();
    assertThat(eligibilities).containsExactlyInAnyOrder(referenceData1, referenceData3);
  }

  @Test
  public void retrieveGender_ShouldReturnGender() {
    List<ReferenceData> gender = referenceDataService.retrieveGender();
    assertThat(gender).containsExactlyInAnyOrder(referenceData2, referenceData4);
  }

  @Test
  public void retrieveAppSource_ShouldReturnAppSource() {
    List<ReferenceData> appSource = referenceDataService.retrieveAppSource();
    assertThat(appSource).containsExactlyInAnyOrder(referenceData5, referenceData6);
  }

  @Test
  public void retrieveDeliverTo_ShouldReturnDeliverTo() {
    List<ReferenceData> deliverTo = referenceDataService.retrieveDeliverTo();
    assertThat(deliverTo).containsExactlyInAnyOrder(referenceData7, referenceData8);
  }

  @Test
  public void retrieveDeliveryOptions_ShouldReturnDeliveryOptions() {
    List<ReferenceData> deliveryOptions = referenceDataService.retrieveDeliveryOptions();
    assertThat(deliveryOptions).containsExactlyInAnyOrder(referenceData9, referenceData10);
  }

  private ReferenceData buildReferenceData(String groupShortCode, int i) {
    return new ReferenceData()
        .description("description" + 1)
        .displayOrder(i)
        .groupDescription("groupDescription" + i)
        .groupShortCode(groupShortCode)
        .shortCode("shortCode" + i)
        .subgroupDescription("subGroupDescription" + i)
        .subgroupShortCode("subGroupShortCode" + i);
  }
}
