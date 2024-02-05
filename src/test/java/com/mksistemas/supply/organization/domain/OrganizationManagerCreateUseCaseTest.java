package com.mksistemas.supply.organization.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mksistemas.supply.organization.OrganizationManagerSupplier;
import com.mksistemas.supply.organization.OrganizationManagerUseCase.OrganizationCommand;
import com.mksistemas.supply.shared.domain.BusinessException;
import com.mksistemas.supply.shared.library.test.BaseTest;
import com.mksistemas.supply.shared.library.test.BaseValidationTest;

import jakarta.validation.ConstraintViolation;

@ExtendWith(MockitoExtension.class)
class OrganizationManagerCreateUseCaseTest {

	private OrganizationRepository organizationRepository;

	private BaseTest<OrganizationCommand, Organization> testCase;
	private BaseTest<OrganizationCommand, Set<ConstraintViolation<OrganizationCommand>>> testValidation;

	@BeforeEach
	public void setup() {
		organizationRepository = mock(OrganizationRepository.class);
		testCase = new BaseTest<OrganizationCommand, Organization>().when(
				request -> new OrganizationManageService(organizationRepository)
						.create(request));
		testValidation = new BaseTest<OrganizationCommand, Set<ConstraintViolation<OrganizationCommand>>>()
				.when(request -> new BaseValidationTest<OrganizationCommand>()
						.validate(request))
				.given(command -> command);
	}

	@Captor
	private ArgumentCaptor<Organization> organizationCaptor;

	@Test
	void createCorrectExecution() {
		testCase.commandSupplier(
				() -> OrganizationManagerSupplier.getDefaultCreateCommand())
				.given(command -> {
					when(organizationRepository
							.existsNameDuplicated(anyString()))
							.thenReturn(Optional.of(false));
					return command;
				}).then((command, response) -> {
					verify(organizationRepository, times(1))
							.existsNameDuplicated(anyString());
					verify(organizationRepository, times(1)).save(response);
				}).execute();

	}

	@Test
	void duplicateOrganizationName() {
		testCase.commandSupplier(
				() -> OrganizationManagerSupplier.getDefaultCreateCommand())
				.given(command -> {
					when(organizationRepository
							.existsNameDuplicated(anyString()))
							.thenReturn(Optional.of(true));
					return command;
				}).then((command, response) -> {
					fail("Nao poderia executar aqui");
				}).thenOnException((command, e) -> {
					BusinessException be = (BusinessException) e;
					assertEquals(
							OrganizationDefaultValidation.ORGANIZATION_NAME_DUPLICATED,
							be.getCode());
				}).execute();
	}

	@Test
	void countryIsoCodeNotFound() {
		testCase.commandSupplier(() -> OrganizationManagerSupplier
				.getInvalidCountryIsoCodeCommand()).given(command -> {
					when(organizationRepository
							.existsNameDuplicated(anyString()))
							.thenReturn(Optional.empty());
					return command;
				}).then((command, response) -> {
					fail("Nao poderia executar aqui");
				}).thenOnException((command, e) -> {
					BusinessException be = (BusinessException) e;
					assertEquals(
							OrganizationDefaultValidation.ORGANIZATION_COUNTRYISOCODE_NOTFOUND,
							be.getCode());
				}).execute();

	}

	@Test
	void validateAllFieldsCorrect() {
		testValidation
				.commandSupplier(() -> OrganizationManagerSupplier
						.getDefaultCreateCommand())
				.given(command -> command).then((command, response) -> {
					assertTrue(response.isEmpty());
				}).execute();
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {" ", "     "})
	void validateNameBlank(String name) {
		testValidation
				.commandSupplier(() -> OrganizationManagerSupplier
						.getCreateCommandWithName(name))
				.then((command, response) -> {
					assertTrue(!response.isEmpty());
				}).execute();
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {" ", "     "})
	void validateIdentityBlank(String identity) {
		testValidation
				.commandSupplier(() -> OrganizationManagerSupplier
						.getCreateCommandWithIdentity(identity))
				.then((command, response) -> {
					assertTrue(!response.isEmpty());
				}).execute();
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {" ", "     ",})
	void validateCountryIsoCodeInvalid(String countryIsoCode) {
		testValidation
				.commandSupplier(() -> OrganizationManagerSupplier
						.getCreateCommandWithIdentity(countryIsoCode))
				.then((command, response) -> {
					assertTrue(!response.isEmpty());
				}).execute();
	}

	@ParameterizedTest
	@ValueSource(strings = {"BR", "AR", "CO"})
	void validateCountryIsoCodeValid(String countryIsoCode) {
		testValidation
				.commandSupplier(() -> OrganizationManagerSupplier
						.getCreateCommandWithIdentity(countryIsoCode))
				.then((command, response) -> {
					assertTrue(response.isEmpty());
				}).execute();
	}

}
