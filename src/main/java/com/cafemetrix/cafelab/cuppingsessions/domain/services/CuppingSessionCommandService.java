package com.cafemetrix.cafelab.cuppingsessions.domain.services;

import com.cafemetrix.cafelab.cuppingsessions.domain.model.aggregates.CuppingSession;
import com.cafemetrix.cafelab.cuppingsessions.domain.model.commands.CreateCuppingSessionCommand;
import com.cafemetrix.cafelab.cuppingsessions.domain.model.commands.UpdateCuppingSessionCommand;

import java.util.Optional;

public interface CuppingSessionCommandService {

    Optional<CuppingSession> handle(CreateCuppingSessionCommand command);

    Optional<CuppingSession> handle(UpdateCuppingSessionCommand command);

    boolean delete(Long sessionId, Long userId);
}
