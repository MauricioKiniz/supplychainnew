package com.mksistemas.supply.organization.adapter.rest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import com.mksistemas.supply.organization.OrganizationManagerSupplier;
import com.mksistemas.supply.organization.OrganizationManagerUseCase.OrganizationCommand;
import com.mksistemas.supply.shared.library.test.BaseIntegrationTest;

import io.hypersistence.tsid.TSID;

@SpringBootTest
@AutoConfigureMockMvc
class OrganizationControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = { "/db/clearDatabase.sql" })
    void shouldCreateOrganization() throws Exception {

        OrganizationCommand command = OrganizationManagerSupplier.getDefaultCreateCommand();
        String content = serializeCommand(command);

        this.mockMvc
            .perform(
                post("/api/v1/organization").contentType(MediaType.APPLICATION_JSON).content(content)
            )
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", containsString("/api/v1/organization")));
    }

    @Test
    void shouldCreateOrganizationWithConstraintError() throws Exception {

        OrganizationCommand command = OrganizationManagerSupplier.getCreateCommandWithName(null);
        String content = serializeCommand(command);

        this.mockMvc
            .perform(
                post("/api/v1/organization").contentType(MediaType.APPLICATION_JSON).content(content)
            )
            .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldCreateOrganizationWithMoreThanOneConstraintError() throws Exception {

        OrganizationCommand command = OrganizationManagerSupplier.getCreateCommandWithNameAndIdentity(null, null);
        String content = serializeCommand(command);

        this.mockMvc
            .perform(
                post("/api/v1/organization").contentType(MediaType.APPLICATION_JSON).content(content)
            )
            .andExpect(status().is4xxClientError());

    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "/db/updateOrganization.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = { "/db/clearDatabase.sql" })
    void shouldUpdateWholeOrganization() throws Exception {

        OrganizationCommand command = OrganizationManagerSupplier.getUpdateCommandComplete();
        String content = serializeCommand(command);

        this.mockMvc
            .perform(
                put("/api/v1/organization").contentType(MediaType.APPLICATION_JSON)
                    .content(content).param("id", TSID.from(1001).toLowerCase())
            )
            .andExpect(status().isOk());
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "/db/updateOrganization.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = { "/db/clearDatabase.sql" })
    void shouldRemoveWholeOrganization() throws Exception {

        this.mockMvc
            .perform(
                delete("/api/v1/organization").contentType(MediaType.APPLICATION_JSON)
                    .param("id", TSID.from(1001).toLowerCase())
            )
            .andExpect(status().isOk());
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "/db/insertManyOrganization.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = { "/db/clearDatabase.sql" })
    void shouldGetAllOrganization() throws Exception {
        this.mockMvc
            .perform(
                get("/api/v1/organization")
                    .contentType(MediaType.APPLICATION_JSON)

            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pagina").value("0"))
            .andExpect(jsonPath("$.proximo").value("false"))
            .andExpect(jsonPath("$.items.length()", is(13)))
            .andDo(print(System.out));
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "/db/insertManyOrganization.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = { "/db/clearDatabase.sql" })
    void shouldGetAllWithFirstPageOrganization() throws Exception {
        this.mockMvc
            .perform(
                get("/api/v1/organization")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("pageNo", "0")
                    .param("pageSize", "5")

            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pagina").value("0"))
            .andExpect(jsonPath("$.proximo").value("true"))
            .andExpect(jsonPath("$.items.length()", is(5)))
            .andDo(print(System.out));
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "/db/insertManyOrganization.sql" })
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = { "/db/clearDatabase.sql" })
    void shouldGetAllWithSecondPageOrganization() throws Exception {
        this.mockMvc
            .perform(
                get("/api/v1/organization")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("pageNo", "1")
                    .param("pageSize", "8")

            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pagina").value("1"))
            .andExpect(jsonPath("$.proximo").value("false"))
            .andExpect(jsonPath("$.items.length()", is(5)))
            .andDo(print(System.out));
    }

}
