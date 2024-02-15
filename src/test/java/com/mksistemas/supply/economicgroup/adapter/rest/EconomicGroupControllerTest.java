package com.mksistemas.supply.economicgroup.adapter.rest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import com.mksistemas.supply.economicgroup.EconomicGroupDataSupplier;
import com.mksistemas.supply.economicgroup.EconomicGroupManagerUseCase.EconomicGroupCommand;
import com.mksistemas.supply.economicgroup.EconomicGroupManagerUseCase.EconomicGroupLinkOrganizationCommand;
import com.mksistemas.supply.shared.library.test.BaseIntegrationTest;

import io.hypersistence.tsid.TSID;

@SpringBootTest
@AutoConfigureMockMvc
class EconomicGroupControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = { "/db/clearDatabase.sql" })
    void shouldCreateEconomicGroup() throws Exception {

        EconomicGroupCommand command = EconomicGroupDataSupplier.getDefaultCommand();
        String content = serializeCommand(command);

        this.mockMvc
            .perform(
                post("/api/v1/economicgroup").contentType(MediaType.APPLICATION_JSON).content(content)
            )
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", containsString("/api/v1/economicgroup")));
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "/db/updateEconomicGroup.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = { "/db/clearDatabase.sql" })
    void shouldUpdateWholeEconomicGroup() throws Exception {

        EconomicGroupCommand command = EconomicGroupDataSupplier.getUpdateCommand();
        String content = serializeCommand(command);

        this.mockMvc
            .perform(
                put("/api/v1/economicgroup").contentType(MediaType.APPLICATION_JSON)
                    .content(content).param("id", TSID.from(1001).toLowerCase())
            )
            .andExpect(status().isOk());
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "/db/updateEconomicGroup.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = { "/db/clearDatabase.sql" })
    void shouldRemoveEconomicGroup() throws Exception {
        this.mockMvc
            .perform(
                delete("/api/v1/economicgroup").contentType(MediaType.APPLICATION_JSON)
                    .param("id", TSID.from(1001).toLowerCase())
            )
            .andExpect(status().isOk());
    }

    @Test
    @Sql(
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = { "/db/createLinkEconomicGroup.sql", "/db/insertManyOrganization.sql" }
    )
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = { "/db/clearDatabase.sql" })
    void shouldLinkEconomicGroup() throws Exception {

        EconomicGroupLinkOrganizationCommand command = EconomicGroupDataSupplier.getSuccessLinkCommand();
        String content = serializeCommand(command);

        this.mockMvc
            .perform(
                post("/api/v1/economicgroup/{id}/link", TSID.from(1001).toLowerCase())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @Test
    @Sql(
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = { "/db/createLinkToRemoveAllEconomicGroup.sql", "/db/insertManyOrganization.sql" }
    )
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = { "/db/clearDatabase.sql" })
    void shouldRemoveAllLinkEconomicGroup() throws Exception {

        EconomicGroupLinkOrganizationCommand command = EconomicGroupDataSupplier.getRemoveAllLinkCommand();
        String content = serializeCommand(command);

        this.mockMvc
            .perform(
                post("/api/v1/economicgroup/{id}/link", TSID.from(1001).toLowerCase())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @Test
    @Sql(
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = { "/db/createLinkToRemoveAllEconomicGroup.sql", "/db/insertManyOrganization.sql" }
    )
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = { "/db/clearDatabase.sql" })
    void shouldGetEconomicGroupById() throws Exception {
        this.mockMvc
            .perform(
                get("/api/v1/economicgroup/{id}", TSID.from(1001).toLowerCase())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @Sql(
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = { "/db/insertManyEconomicGroup.sql", "/db/insertManyOrganization.sql" }
    )
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = { "/db/clearDatabase.sql" })
    void shouldGetAllEconomicGroup() throws Exception {
        this.mockMvc
            .perform(
                get("/api/v1/economicgroup")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(print());
    }

}
