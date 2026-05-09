package com.cafemetrix.cafelab.preparation.interfaces.rest;

import com.cafemetrix.cafelab.preparation.domain.exceptions.PortfolioNotFoundException;
import com.cafemetrix.cafelab.preparation.interfaces.acl.PreparationContextFacade;
import com.cafemetrix.cafelab.preparation.interfaces.rest.dto.CreatePortfolioResource;
import com.cafemetrix.cafelab.preparation.interfaces.rest.dto.PortfolioResource;
import com.cafemetrix.cafelab.preparation.interfaces.rest.dto.UpdatePortfolioResource;
import com.cafemetrix.cafelab.preparation.interfaces.rest.transform.CreatePortfolioCommandFromResourceAssembler;
import com.cafemetrix.cafelab.preparation.interfaces.rest.transform.PortfolioResourceFromEntityAssembler;
import com.cafemetrix.cafelab.preparation.interfaces.rest.transform.UpdatePortfolioCommandFromResourceAssembler;
import com.cafemetrix.cafelab.iam.infrastructure.authorization.sfs.support.CurrentProfileIdResolver;
import com.cafemetrix.cafelab.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/portfolios", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Portfolios", description = "Portafolios por perfil (JWT)")
public class PortfoliosController {
    private final PreparationContextFacade preparationContextFacade;
    private final CurrentProfileIdResolver currentProfileIdResolver;

    public PortfoliosController(
            PreparationContextFacade preparationContextFacade,
            CurrentProfileIdResolver currentProfileIdResolver) {
        this.preparationContextFacade = preparationContextFacade;
        this.currentProfileIdResolver = currentProfileIdResolver;
    }

    private Optional<Long> resolveCurrentUserId() {
        return currentProfileIdResolver.resolveProfileId();
    }

    private ResponseEntity<?> unauthorized(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResource(message));
    }

    @Operation(summary = "Crear portafolio (perfil desde JWT)")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPortfolio(@RequestBody CreatePortfolioResource resource) {
        Optional<Long> userIdOpt = resolveCurrentUserId();
        if (userIdOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        try {
            var command =
                    CreatePortfolioCommandFromResourceAssembler.toCommand(userIdOpt.get(), resource);
            var portfolioId = preparationContextFacade.createPortfolio(
                    command.userId(), command.name());
            if (portfolioId == null || portfolioId == 0L) {
                return ResponseEntity.badRequest()
                        .body(new MessageResource("No se pudo crear el portafolio"));
            }
            var portfolio =
                    preparationContextFacade.getPortfolioByIdForUser(portfolioId, userIdOpt.get());
            if (portfolio.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new MessageResource("No se pudo obtener el portafolio creado"));
            }
            var portfolioResource = PortfolioResourceFromEntityAssembler.toResourceFromEntity(portfolio.get());
            return new ResponseEntity<>(portfolioResource, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new MessageResource(ex.getMessage()));
        }
    }

    @Operation(summary = "Listar portafolios del perfil autenticado")
    @GetMapping
    public ResponseEntity<?> listMyPortfolios() {
        Optional<Long> userIdOpt = resolveCurrentUserId();
        if (userIdOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        var portfolios = preparationContextFacade.getPortfoliosByUserId(userIdOpt.get());
        var resources = portfolios.stream()
                .map(PortfolioResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Obtener portafolio por id (solo si pertenece al perfil)")
    @GetMapping("/{portfolioId}")
    public ResponseEntity<?> getPortfolioById(@PathVariable Long portfolioId) {
        Optional<Long> userIdOpt = resolveCurrentUserId();
        if (userIdOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        var portfolio =
                preparationContextFacade.getPortfolioByIdForUser(portfolioId, userIdOpt.get());
        if (portfolio.isEmpty()) {
            throw new PortfolioNotFoundException(portfolioId);
        }
        return ResponseEntity.ok(PortfolioResourceFromEntityAssembler.toResourceFromEntity(portfolio.get()));
    }

    @Operation(summary = "Actualizar portafolio (solo si pertenece al perfil)")
    @PutMapping(value = "/{portfolioId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePortfolio(
            @PathVariable Long portfolioId, @RequestBody UpdatePortfolioResource resource) {
        Optional<Long> userIdOpt = resolveCurrentUserId();
        if (userIdOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        try {
            var command = UpdatePortfolioCommandFromResourceAssembler.toCommandFromResource(
                    userIdOpt.get(), portfolioId, resource);
            var updated = preparationContextFacade.updatePortfolio(command);
            if (updated.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MessageResource("No se pudo actualizar el portafolio"));
            }
            return ResponseEntity.ok(PortfolioResourceFromEntityAssembler.toResourceFromEntity(updated.get()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new MessageResource(ex.getMessage()));
        }
    }

    @Operation(summary = "Eliminar portafolio (solo si pertenece al perfil)")
    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<?> deletePortfolio(@PathVariable Long portfolioId) {
        Optional<Long> userIdOpt = resolveCurrentUserId();
        if (userIdOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        boolean deleted = preparationContextFacade.deletePortfolio(portfolioId, userIdOpt.get());
        if (deleted) {
            return ResponseEntity.ok(new MessageResource("Portafolio eliminado exitosamente"));
        }
        throw new PortfolioNotFoundException(portfolioId);
    }
}
