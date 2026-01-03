package com.perfume.backend.service;
import com.perfume.backend.dto.response.EssentialOilDto;
import java.util.List;

public interface EssentialOilService {
    List<EssentialOilDto> getAllOils();
}
