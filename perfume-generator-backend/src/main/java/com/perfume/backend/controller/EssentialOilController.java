package com.perfume.backend.controller;
import com.perfume.backend.dto.response.EssentialOilDto;
import com.perfume.backend.service.EssentialOilService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/oils")
@CrossOrigin(origins = "http://localhost:4200")
public class EssentialOilController {

    private final EssentialOilService service;

    public EssentialOilController(EssentialOilService service) {
        this.service = service;
    }

    @GetMapping
    public List<EssentialOilDto> getAll() {
        return service.getAllOils();
    }
}
