package com.cafemetrix.cafelab.management.interfaces.acl;

import com.cafemetrix.cafelab.management.domain.model.aggregates.InventoryEntry;
import com.cafemetrix.cafelab.management.domain.model.commands.CreateInventoryEntryCommand;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Anti-corruption facade for the management bounded context (inventory entries and stock coordination).
 * Implementations live in {@code application.acl}; controllers depend on this interface only.
 */
public interface ManagementContextFacade {

    Long createInventoryEntry(CreateInventoryEntryCommand command);

    Long updateInventoryEntry(
            Long ownerUserId,
            Long inventoryEntryId,
            Long coffeeLotId,
            Double quantityUsed,
            LocalDateTime dateUsed,
            String finalProduct);

    boolean deleteInventoryEntry(Long ownerUserId, Long inventoryEntryId);

    List<InventoryEntry> getAllInventoryEntries();

    List<InventoryEntry> getInventoryEntriesByUserId(Long userId);

    List<InventoryEntry> getInventoryEntriesByCoffeeLotId(Long coffeeLotId);

    Optional<InventoryEntry> getInventoryEntryById(Long inventoryEntryId);
}
