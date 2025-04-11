package com.projects.Neighbrly.Neighbrly.service;

import com.projects.Neighbrly.Neighbrly.entity.Room;

public interface InventoryService {
    void initializeInventory(Room room);
    void deleteFutureInventory(Room room);
}
