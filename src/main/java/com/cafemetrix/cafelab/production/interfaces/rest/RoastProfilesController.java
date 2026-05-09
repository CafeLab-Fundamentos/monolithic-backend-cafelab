package com.cafemetrix.cafelab.production.interfaces.rest;

import com.cafemetrix.cafelab.production.domain.exceptions.RoastProfileNotFoundException;
import com.cafemetrix.cafelab.production.interfaces.acl.CoffeeproductionContextFacade;
import com.cafemetrix.cafelab.production.interfaces.rest.resources.CreateRoastProfileResource;
import com.cafemetrix.cafelab.production.interfaces.rest.resources.RoastProfileResource;
import com.cafemetrix.cafelab.production.interfaces.rest.resources.UpdateRoastProfileResource;
import com.cafemetrix.cafelab.production.interfaces.rest.transform.CreateRoastProfileCommandFromResourceAssembler;
import com.cafemetrix.cafelab.production.interfaces.rest.transform.RoastProfileResourceFromEntityAssembler;
import com.cafemetrix.cafelab.production.interfaces.rest.transform.UpdateRoastProfileCommandFromResourceAssembler;
import com.cafemetrix.cafelab.iam.infrastructure.authorization.sfs.support.CurrentProfileIdResolver;
import com.cafemetrix.cafelab.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/roast-profile", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Roast Profiles", description = "Roast Profile Management Endpoints")
public class RoastProfilesController {
    private final CoffeeproductionContextFacade coffeeproductionContextFacade;
    private final CurrentProfileIdResolver currentProfileIdResolver;

    public RoastProfilesController(
            CoffeeproductionContextFacade coffeeproductionContextFacade,
            CurrentProfileIdResolver currentProfileIdResolver) {
        this.coffeeproductionContextFacade = coffeeproductionContextFacade;
        this.currentProfileIdResolver = currentProfileIdResolver;
    }

    private Optional<Long> resolveCurrentUserId() {
        return currentProfileIdResolver.resolveProfileId();
    }

    private ResponseEntity<?> forbidden(String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResource(message));
    }

    private ResponseEntity<?> unauthorized(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResource(message));
    }

    private boolean ownsCoffeeLot(Long coffeeLotId, Long currentUserId) {
        return coffeeproductionContextFacade
                .getCoffeeLotById(coffeeLotId)
                .map(l -> l.getUserId().equals(currentUserId))
                .orElse(false);
    }

    private boolean ownsRoastProfile(Long roastProfileId, Long currentUserId) {
        return coffeeproductionContextFacade
                .getRoastProfileById(roastProfileId)
                .map(r -> r.getUserId().equals(currentUserId))
                .orElse(false);
    }

    @Operation(summary = "Crear perfil (JWT; lote debe ser suyo)")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRoastProfile(@Valid @RequestBody CreateRoastProfileResource resource) {
        Optional<Long> ownerIdOpt = resolveCurrentUserId();
        if (ownerIdOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        Long ownerId = ownerIdOpt.get();
        if (!ownsCoffeeLot(resource.lot(), ownerId)) {
            return forbidden("Debe vincular el perfil a un lote de su cuenta");
        }

        var command = CreateRoastProfileCommandFromResourceAssembler.toCommandFromResource(ownerId, resource);
        var roastProfileId =
                coffeeproductionContextFacade.createRoastProfile(
                        command.userId(),
                        command.name(),
                        command.type(),
                        command.duration(),
                        command.tempStart(),
                        command.tempEnd(),
                        command.coffeeLotId(),
                        command.isFavorite());

        if (roastProfileId == 0L) {
            return ResponseEntity.badRequest()
                    .body(new MessageResource("No se pudo crear el perfil de tueste"));
        }

        var roastProfile = coffeeproductionContextFacade.getRoastProfileById(roastProfileId);
        if (roastProfile.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new MessageResource("No se pudo obtener el perfil de tueste creado"));
        }

        var roastProfileResource =
                RoastProfileResourceFromEntityAssembler.toResourceFromEntity(roastProfile.get());
        return new ResponseEntity<>(roastProfileResource, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar perfiles del usuario autenticado")
    @GetMapping
    public ResponseEntity<?> getAllRoastProfiles() {
        Optional<Long> userIdOpt = resolveCurrentUserId();
        if (userIdOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        var roastProfiles =
                coffeeproductionContextFacade.getRoastProfilesByUserId(userIdOpt.get());
        var resources =
                roastProfiles.stream()
                        .map(RoastProfileResourceFromEntityAssembler::toResourceFromEntity)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Perfiles por userId (solo el propio perfil)")
    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getRoastProfilesByUserId(@PathVariable Long userId) {
        Optional<Long> currentOpt = resolveCurrentUserId();
        if (currentOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        if (!currentOpt.get().equals(userId)) {
            return forbidden("No puede consultar perfiles de otro perfil");
        }
        var roastProfiles = coffeeproductionContextFacade.getRoastProfilesByUserId(userId);
        var resources =
                roastProfiles.stream()
                        .map(RoastProfileResourceFromEntityAssembler::toResourceFromEntity)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Perfiles por lote (el lote debe ser suyo)")
    @GetMapping("/lot/{coffeeLotId}")
    public ResponseEntity<?> getRoastProfilesByCoffeeLotId(@PathVariable Long coffeeLotId) {
        Optional<Long> currentOpt = resolveCurrentUserId();
        if (currentOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        if (!ownsCoffeeLot(coffeeLotId, currentOpt.get())) {
            return forbidden("No autorizado para consultar perfiles de este lote");
        }
        var roastProfiles =
                coffeeproductionContextFacade.getRoastProfilesByCoffeeLotId(coffeeLotId);
        var resources =
                roastProfiles.stream()
                        .map(RoastProfileResourceFromEntityAssembler::toResourceFromEntity)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Obtener perfil por id")
    @GetMapping("/{roastProfileId}")
    public ResponseEntity<?> getRoastProfileById(@PathVariable Long roastProfileId) {
        Optional<Long> currentOpt = resolveCurrentUserId();
        if (currentOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        var roastProfile = coffeeproductionContextFacade.getRoastProfileById(roastProfileId);
        if (roastProfile.isEmpty()) {
            throw new RoastProfileNotFoundException(roastProfileId);
        }
        if (!roastProfile.get().getUserId().equals(currentOpt.get())) {
            return forbidden("No autorizado para ver este perfil");
        }
        var roastProfileResource =
                RoastProfileResourceFromEntityAssembler.toResourceFromEntity(roastProfile.get());
        return ResponseEntity.ok(roastProfileResource);
    }

    @Operation(summary = "Actualizar perfil")
    @PutMapping(value = "/{roastProfileId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRoastProfile(
            @PathVariable Long roastProfileId,
            @Valid @RequestBody UpdateRoastProfileResource resource) {
        Optional<Long> currentOpt = resolveCurrentUserId();
        if (currentOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        if (!ownsRoastProfile(roastProfileId, currentOpt.get())) {
            return forbidden("No autorizado para modificar este perfil");
        }
        if (!ownsCoffeeLot(resource.lot(), currentOpt.get())) {
            return forbidden("Debe vincular el perfil a un lote de su cuenta");
        }

        var updateRoastProfileCommand =
                UpdateRoastProfileCommandFromResourceAssembler.toCommandFromResource(
                        roastProfileId, resource);
        var updatedRoastProfileId =
                coffeeproductionContextFacade.updateRoastProfile(
                        updateRoastProfileCommand.roastProfileId(),
                        updateRoastProfileCommand.name(),
                        updateRoastProfileCommand.type(),
                        updateRoastProfileCommand.duration(),
                        updateRoastProfileCommand.tempStart(),
                        updateRoastProfileCommand.tempEnd(),
                        updateRoastProfileCommand.coffeeLotId(),
                        updateRoastProfileCommand.isFavorite());

        if (updatedRoastProfileId == 0L) {
            throw new RoastProfileNotFoundException(roastProfileId);
        }

        var roastProfile = coffeeproductionContextFacade.getRoastProfileById(updatedRoastProfileId);
        if (roastProfile.isEmpty()) {
            throw new RoastProfileNotFoundException(roastProfileId);
        }

        var roastProfileResource =
                RoastProfileResourceFromEntityAssembler.toResourceFromEntity(roastProfile.get());
        return ResponseEntity.ok(roastProfileResource);
    }

    @Operation(summary = "Eliminar perfil")
    @DeleteMapping("/{roastProfileId}")
    public ResponseEntity<?> deleteRoastProfile(@PathVariable Long roastProfileId) {
        Optional<Long> currentOpt = resolveCurrentUserId();
        if (currentOpt.isEmpty()) {
            return unauthorized("Usuario no autenticado o perfil no encontrado");
        }
        if (!ownsRoastProfile(roastProfileId, currentOpt.get())) {
            return forbidden("No autorizado para eliminar este perfil");
        }

        var deleted = coffeeproductionContextFacade.deleteRoastProfile(roastProfileId);
        if (deleted) {
            return ResponseEntity.ok(new MessageResource("Perfil de tueste eliminado exitosamente"));
        }
        throw new RoastProfileNotFoundException(roastProfileId);
    }
}
