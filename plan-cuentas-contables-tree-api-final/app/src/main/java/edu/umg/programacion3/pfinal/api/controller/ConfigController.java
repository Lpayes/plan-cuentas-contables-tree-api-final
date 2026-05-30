package edu.umg.programacion3.pfinal.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.umg.programacion3.pfinal.service.StorageSelectorService;

@RestController
@RequestMapping("/config")
public class ConfigController {

    private final StorageSelectorService storageSelectorService;

    public ConfigController(StorageSelectorService storageSelectorService) {
        this.storageSelectorService = storageSelectorService;
    }

    @GetMapping("/storage")
    public Map<String, String> getStorage() {
        Map<String, String> response = new HashMap<>();
        response.put("activeStorage", storageSelectorService.getActiveStorage());
        return response;
    }

    @PostMapping("/storage/{storage}")
    public Map<String, String> changeStorage(
            @PathVariable(value = "storage") String storage) {
        storageSelectorService.changeStorage(storage);

        Map<String, String> response = new HashMap<>();
        response.put("activeStorage", storageSelectorService.getActiveStorage());
        response.put("message", "Persistencia cambiada correctamente");
        return response;
    }
}