package com.cafemetrix.cafelab.profiles.domain.model.queries;

/**
 * Localiza el perfil de barista vinculado al usuario IAM ({@code users.id} → {@code profiles.user_id}).
 */
public record GetProfileByIamUserIdQuery(Long iamUserId) {}
