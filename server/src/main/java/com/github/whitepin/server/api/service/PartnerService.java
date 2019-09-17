package com.github.whitepin.server.api.service;

import com.github.whitepin.server.api.component.PartnerEntityToPartnerDTO;
import com.github.whitepin.server.api.dto.PartnerDTO;
import com.github.whitepin.server.api.entity.PartnerEntity;
import com.github.whitepin.server.api.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerService {

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private Converter<List<PartnerEntity>, List<PartnerDTO>> partnerEntityPartnerDTOConverter;

    public List<PartnerDTO> getAllPartner() {
        return partnerEntityPartnerDTOConverter.convert(partnerRepository.findAll());
    }

}
