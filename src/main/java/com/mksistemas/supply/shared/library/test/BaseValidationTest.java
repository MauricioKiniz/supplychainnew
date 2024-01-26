package com.mksistemas.supply.shared.library.test;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public final class BaseValidationTest<TInput> {
	public Set<ConstraintViolation<TInput>> validate(TInput input) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator.validate(input);
	}
}
