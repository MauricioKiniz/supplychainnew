package com.mksistemas.supply.organization.adapter.rest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.mksistemas.supply.organization.OrganizationManagerSupplier;
import com.mksistemas.supply.organization.OrganizationManagerUseCase.CreateOrganizationCommand;
import com.mksistemas.supply.shared.library.test.BaseIntegrationTest;

@SpringBootTest
@AutoConfigureMockMvc
class OrganizationControllerTest extends BaseIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {
			"/db/clearDatabase.sql"})
	void shouldCreateOrganization() throws Exception {

		CreateOrganizationCommand command = OrganizationManagerSupplier
				.getDefaultCreateCommand();
		String content = serializeCommand(command);

		this.mockMvc
				.perform(post("/api/v1/organization")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(header().string("Location",
						containsString("/api/v1/organization")));
	}
}