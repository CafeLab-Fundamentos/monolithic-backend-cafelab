package com.cafemetrix.cafelab.iam.infrastructure.hashing.bcrypt;

import com.cafemetrix.cafelab.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {
}
