package com.jarvi.bitboxapi.service;

import com.jarvi.bitboxapi.controller.exception.AlreadyExistException;
import com.jarvi.bitboxapi.controller.exception.InvalidStateException;
import com.jarvi.bitboxapi.controller.exception.NotFoundException;
import com.jarvi.bitboxapi.model.ItemDTO;
import com.jarvi.bitboxapi.persistence.entity.Item;
import com.jarvi.bitboxapi.persistence.entity.PriceReduction;
import com.jarvi.bitboxapi.persistence.entity.Supplier;
import com.jarvi.bitboxapi.persistence.entity.User;
import com.jarvi.bitboxapi.persistence.repository.ItemRepository;
import com.jarvi.bitboxapi.persistence.repository.PriceReductionRepository;
import com.jarvi.bitboxapi.persistence.repository.SupplierRepository;
import com.jarvi.bitboxapi.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ItemService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private PriceReductionRepository priceReductionRepository;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private PriceReductionService priceReductionService;

    @Transactional(rollbackFor = Exception.class)
    public Item create(ItemDTO newItem, String username) {

        User user = userRepository.findByUsername(username);

        Item exist = itemRepository.findByItemCode(newItem.getItemCode());
        if (exist != null) {
            throw new AlreadyExistException("The Item " + newItem.getItemCode() + " already exist!");
        }

        Item.Builder daoItemBuilder = Item.newItem()
                .itemCode(newItem.getItemCode())
                .description(newItem.getDescription())
                .price(newItem.getPrice())
                .state(Item.State.ACTIVE)
                .creationDate(new Date())
                .creator(user);

        Item item = daoItemBuilder.build();

        item.setSuppliers(supplierService.addSuppliers(newItem.getSuppliers()));
        item.setPriceReductions(priceReductionService.addPriceReductions(newItem.getPriceReductions()));

        itemRepository.save(item);
        return item;
    }

    @Transactional(rollbackFor = Exception.class)
    public Item update(int itemCode, ItemDTO itemsUpdates, String username) {
        itemsUpdates.setItemCode(itemCode);

        Item item = itemRepository.findByItemCode(itemCode);
        if (item == null) {
            throw new NotFoundException("The Item " + itemCode + " not exist!");
        }

        if (!item.getState().equals(Item.State.ACTIVE)){
            throw new InvalidStateException("The item state must be ACTIVE to update");

        }

        item.setDescription(itemsUpdates.getDescription());
        item.setPrice(itemsUpdates.getPrice());

        item.setSuppliers(supplierService.addSuppliers(itemsUpdates.getSuppliers()));
        item.setPriceReductions(priceReductionService.addPriceReductions(itemsUpdates.getPriceReductions()));

        itemRepository.save(item);
        return item;

    }

    public Item findByItemCode(int itemCode) {
        Item item = itemRepository.findByItemCode(itemCode);
        if (item == null) {
            throw new NotFoundException("The Item " + itemCode + " not exist!");
        }
        return item;
    }

    public Iterable<Item> findAll() {
        return itemRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public Item deactivate(int itemCode, String reason, String username) {
        User user = userRepository.findByUsername(username);
        Item item = itemRepository.findByItemCode(itemCode);
        if (item == null) {
            throw new NotFoundException("The Item " + itemCode + " not exist!");
        }

        item.setState(Item.State.DISCONTINUED);
        item.setDiscontinuedAt(new Date());
        item.setDiscontinuedReason(reason);
        item.setDiscontinuedBy(user);

        itemRepository.save(item);
        return item;
    }

    @Transactional(rollbackFor = Exception.class)
    public Iterable<Item> delete(int itemCode) {
        Item item = itemRepository.findByItemCode(itemCode);

        if (item == null) {
            throw new NotFoundException("The Item " + itemCode + " not exist!");
        }

        if (item.getPriceReductions() != null) {
            for (PriceReduction priceReduction: item.getPriceReductions()){
                priceReductionRepository.delete(priceReduction);
            }
        }
        item.getPriceReductions().clear();

        if (item.getSuppliers() != null) {
            for (Supplier supplier: item.getSuppliers()){
                supplier.removeItem(item);
                supplierRepository.save(supplier);
            }
        }
        item.getSuppliers().clear();

        itemRepository.delete(item);

        return findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public Iterable<Item> cheapestPerSupplier(String supplierName) {
        Supplier supplier = supplierRepository.findByName(supplierName);
        return itemRepository.findByCheapestPerSupplier(supplier);
    }



}
