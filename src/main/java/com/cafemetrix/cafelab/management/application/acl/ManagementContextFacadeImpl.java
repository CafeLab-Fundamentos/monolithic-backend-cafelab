package com.cafemetrix.cafelab.management.application.acl;

import com.cafemetrix.cafelab.management.domain.exceptions.InsufficientCoffeeLotStockException;
import com.cafemetrix.cafelab.management.domain.exceptions.InventoryAccessDeniedException;
import com.cafemetrix.cafelab.management.domain.model.aggregates.InventoryEntry;
import com.cafemetrix.cafelab.management.domain.model.commands.CreateInventoryEntryCommand;
import com.cafemetrix.cafelab.management.domain.model.commands.DeleteInventoryEntryCommand;
import com.cafemetrix.cafelab.management.domain.model.commands.UpdateInventoryEntryCommand;
import com.cafemetrix.cafelab.management.domain.services.InventoryEntryCommandService;
import com.cafemetrix.cafelab.management.domain.services.InventoryEntryQueryService;
import com.cafemetrix.cafelab.management.interfaces.acl.ManagementContextFacade;
import com.cafemetrix.cafelab.production.domain.exceptions.CoffeeLotNotFoundException;
import com.cafemetrix.cafelab.production.domain.exceptions.CoffeeLotOwnershipException;
import com.cafemetrix.cafelab.production.domain.model.aggregates.CoffeeLot;
import com.cafemetrix.cafelab.production.interfaces.acl.CoffeeproductionContextFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ManagementContextFacadeImpl implements ManagementContextFacade {
    private final InventoryEntryCommandService inventoryEntryCommandService;
    private final InventoryEntryQueryService inventoryEntryQueryService;
    private final CoffeeproductionContextFacade coffeeproductionContextFacade;

    public ManagementContextFacadeImpl(
            InventoryEntryCommandService inventoryEntryCommandService,
            InventoryEntryQueryService inventoryEntryQueryService,
            CoffeeproductionContextFacade coffeeproductionContextFacade) {
        this.inventoryEntryCommandService = inventoryEntryCommandService;
        this.inventoryEntryQueryService = inventoryEntryQueryService;
        this.coffeeproductionContextFacade = coffeeproductionContextFacade;
    }

    @Override
    @Transactional
    public Long createInventoryEntry(CreateInventoryEntryCommand command) {
        deductStock(command.userId(), command.coffeeLotId(), command.quantityUsed());
        var result = inventoryEntryCommandService.handle(command);
        return result.map(InventoryEntry::getId).orElse(0L);
    }

    @Override
    @Transactional
    public Long updateInventoryEntry(
            Long ownerUserId,
            Long inventoryEntryId,
            Long coffeeLotId,
            Double quantityUsed,
            LocalDateTime dateUsed,
            String finalProduct) {
        var existingOpt = inventoryEntryQueryService.getInventoryEntryById(inventoryEntryId);
        if (existingOpt.isEmpty()) {
            return 0L;
        }
        var existing = existingOpt.get();
        if (!existing.getUserId().equals(ownerUserId)) {
            throw new InventoryAccessDeniedException("No autorizado para modificar esta entrada");
        }
        restoreStock(ownerUserId, existing.getCoffeeLotId(), existing.getQuantityUsed());
        deductStock(ownerUserId, coffeeLotId, quantityUsed);
        var command =
                new UpdateInventoryEntryCommand(
                        inventoryEntryId, coffeeLotId, quantityUsed, dateUsed, finalProduct);
        var result = inventoryEntryCommandService.handle(command);
        return result.map(InventoryEntry::getId).orElse(0L);
    }

    @Override
    @Transactional
    public boolean deleteInventoryEntry(Long ownerUserId, Long inventoryEntryId) {
        var existingOpt = inventoryEntryQueryService.getInventoryEntryById(inventoryEntryId);
        if (existingOpt.isEmpty()) {
            return false;
        }
        var existing = existingOpt.get();
        if (!existing.getUserId().equals(ownerUserId)) {
            throw new InventoryAccessDeniedException("No autorizado para eliminar esta entrada");
        }
        restoreStock(ownerUserId, existing.getCoffeeLotId(), existing.getQuantityUsed());
        return inventoryEntryCommandService.handle(new DeleteInventoryEntryCommand(inventoryEntryId));
    }

    private void deductStock(Long userId, Long coffeeLotId, double quantityUsed) {
        CoffeeLot lot = requireOwnedLot(userId, coffeeLotId);
        if (quantityUsed > lot.getWeight()) {
            throw new InsufficientCoffeeLotStockException();
        }
        double newWeight = lot.getWeight() - quantityUsed;
        persistLotWeight(lot, newWeight);
    }

    private void restoreStock(Long userId, Long coffeeLotId, double quantityToRestore) {
        CoffeeLot lot = requireOwnedLot(userId, coffeeLotId);
        double newWeight = lot.getWeight() + quantityToRestore;
        persistLotWeight(lot, newWeight);
    }

    private CoffeeLot requireOwnedLot(Long userId, Long coffeeLotId) {
        var lotOpt = coffeeproductionContextFacade.getCoffeeLotById(coffeeLotId);
        if (lotOpt.isEmpty()) {
            throw new CoffeeLotNotFoundException(coffeeLotId);
        }
        var lot = lotOpt.get();
        if (!lot.getUserId().equals(userId)) {
            throw new CoffeeLotOwnershipException("El lote no pertenece a su cuenta");
        }
        return lot;
    }

    private void persistLotWeight(CoffeeLot lot, double newWeight) {
        long updated =
                coffeeproductionContextFacade.updateCoffeeLot(
                        lot.getId(),
                        lot.getLotName(),
                        lot.getCoffeeType(),
                        lot.getProcessingMethod(),
                        lot.getAltitude(),
                        newWeight,
                        lot.getOrigin(),
                        lot.getStatus(),
                        lot.getCertifications());
        if (updated == 0L) {
            throw new IllegalStateException("No se pudo actualizar el stock del lote");
        }
    }

    @Override
    public List<InventoryEntry> getAllInventoryEntries() {
        return inventoryEntryQueryService.getAllInventoryEntries();
    }

    @Override
    public List<InventoryEntry> getInventoryEntriesByUserId(Long userId) {
        return inventoryEntryQueryService.getInventoryEntriesByUserId(userId);
    }

    @Override
    public List<InventoryEntry> getInventoryEntriesByCoffeeLotId(Long coffeeLotId) {
        return inventoryEntryQueryService.getInventoryEntriesByCoffeeLotId(coffeeLotId);
    }

    @Override
    public Optional<InventoryEntry> getInventoryEntryById(Long inventoryEntryId) {
        return inventoryEntryQueryService.getInventoryEntryById(inventoryEntryId);
    }
}
