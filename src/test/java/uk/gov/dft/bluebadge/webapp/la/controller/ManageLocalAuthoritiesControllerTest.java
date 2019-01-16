package uk.gov.dft.bluebadge.webapp.la.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.dft.bluebadge.webapp.la.StandaloneMvcTestViewResolver;
import uk.gov.dft.bluebadge.webapp.la.client.referencedataservice.model.ReferenceData;
import uk.gov.dft.bluebadge.webapp.la.service.referencedata.ReferenceDataService;

public class ManageLocalAuthoritiesControllerTest {

  private MockMvc mockMvc;

  @Mock private ReferenceDataService referenceDataServiceMock;

  private ManageLocalAuthoritiesController controller;

  private List<ReferenceData> localAuthorities;

  @Before
  public void setup() {

    // Process mock annotations
    MockitoAnnotations.initMocks(this);

    controller = new ManageLocalAuthoritiesController(referenceDataServiceMock);

    this.mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setViewResolvers(new StandaloneMvcTestViewResolver())
            .build();

    ReferenceData rd1 = ReferenceData.builder().description("La 1").build();
    ReferenceData rd2 = ReferenceData.builder().description("La 2").build();
    ReferenceData rd3 = ReferenceData.builder().description("La 3").build();
    localAuthorities = Lists.newArrayList(rd1, rd2, rd3);
    when(referenceDataServiceMock.retrieveBadgeLocalAuthorities()).thenReturn(localAuthorities);
  }

  @Test
  public void show_shouldDisplayLocalAuthorities() throws Exception {
    mockMvc
        .perform(get("/manage-local-authorities"))
        .andExpect(status().isOk())
        .andExpect(view().name("manage-local-authorities"))
        .andExpect(model().attribute("localAuthorities", localAuthorities));
    verify(referenceDataServiceMock, times(1)).retrieveBadgeLocalAuthorities();
  }
}
