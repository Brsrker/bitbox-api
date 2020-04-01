package com.jarvi.bitboxapi.controller;

import com.jarvi.bitboxapi.controller.security.IsAdmin;
import com.jarvi.bitboxapi.controller.security.IsUser;
import com.jarvi.bitboxapi.model.DeactivateItemDTO;
import com.jarvi.bitboxapi.model.ItemDTO;
import com.jarvi.bitboxapi.persistence.entity.Item;
import com.jarvi.bitboxapi.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/items")
@CrossOrigin
public class ItemController {

    @Autowired
    private ItemService itemService;

    @IsUser
    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody @Validated ItemDTO itemDTO) throws Exception {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        itemService.create(itemDTO, principal.getUsername());
        return ResponseEntity.ok(itemService.findAll());
    }

    @IsUser
    @GetMapping(value = "/{itemCode}")
    public ResponseEntity<Item> find(
            @PathVariable(name = "itemCode") Integer itemCode) throws Exception {
        return ResponseEntity.ok(itemService.findByItemCode(itemCode));
    }

    @IsUser
    @GetMapping(value = "/supplierCheapest/{supplierName}")
    public ResponseEntity<?> cheapestPerSupplier(HttpServletRequest request,
                                                 @PathVariable(name = "supplierName") String supplierName) throws Exception {
        request.isUserInRole("ITEM_LIST");
        return ResponseEntity.ok(itemService.cheapestPerSupplier(supplierName));
    }

    @IsUser
    @GetMapping(value = "")
    public ResponseEntity<Iterable<Item>> findAll() throws Exception {
        return ResponseEntity.ok(itemService.findAll());
    }

    @IsUser
    @PutMapping(value = "/{itemCode}")
    public ResponseEntity<?> update(
            @PathVariable(name = "itemCode") Integer itemCode,
            @RequestBody @Validated ItemDTO itemDTO) throws Exception {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        itemService.update(itemCode, itemDTO, principal.getUsername());
        return ResponseEntity.ok(itemService.findAll());
    }

    @IsUser
    @PutMapping(value = "/{itemCode}/deactivate")
    public ResponseEntity<?> deactivate(
            @PathVariable(name = "itemCode") Integer itemCode,
            @RequestBody @Validated DeactivateItemDTO deactivateItemDTO) throws Exception {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        itemService.deactivate(itemCode, deactivateItemDTO.getReason(), principal.getUsername());
        return ResponseEntity.ok(itemService.findAll());
    }

    @IsAdmin
    @DeleteMapping(value = "/{itemCode}")
    public ResponseEntity<?> delete(@PathVariable(name = "itemCode") Integer itemCode) throws Exception {
        return ResponseEntity.ok(itemService.delete(itemCode));
    }
}
