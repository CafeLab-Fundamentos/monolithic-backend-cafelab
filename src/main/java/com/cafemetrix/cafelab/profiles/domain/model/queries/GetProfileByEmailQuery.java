package com.cafemetrix.cafelab.profiles.domain.model.queries;

import com.cafemetrix.cafelab.profiles.domain.model.valueobjects.EmailAddress;

public record GetProfileByEmailQuery(EmailAddress emailAddress) {
}
