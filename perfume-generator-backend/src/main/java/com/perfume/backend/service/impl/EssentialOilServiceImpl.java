package com.perfume.backend.service.impl;
import com.perfume.backend.dto.response.EssentialOilDto;
import com.perfume.backend.mapper.EssentialOilMapper;
import com.perfume.backend.repository.EssentialOilRepository;
import com.perfume.backend.service.EssentialOilService;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * Implémentation du service pour les huiles essentielles.
 */

@Service
public class EssentialOilServiceImpl implements EssentialOilService {

    private final EssentialOilRepository repository;

    public EssentialOilServiceImpl(EssentialOilRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EssentialOilDto> getAllOils() {
        return repository.findAll()
                .stream()
                .map(EssentialOilMapper::toDto)
                .toList();
    }
}
