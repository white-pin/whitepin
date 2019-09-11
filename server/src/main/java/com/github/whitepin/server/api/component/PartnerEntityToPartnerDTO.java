package com.github.whitepin.server.api.component;

import com.github.whitepin.server.api.dto.PartnerDTO;
import com.github.whitepin.server.api.entity.PartnerEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class PartnerEntityToPartnerDTO implements Converter<PartnerEntity, PartnerDTO> {

    @Override
    public PartnerDTO convert(PartnerEntity partner) {
        PartnerDTO partnerDTO = new PartnerDTO();
        if (partner != null) {
            partnerDTO = PartnerDTO.builder()
                    .code(partner.getCode())
                    .name(partner.getName())
                    .build();
        }
        return partnerDTO;
    }

    public List<PartnerDTO> convert(List<PartnerEntity> partnerList) {
        List<PartnerDTO> partnerDTOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(partnerList)) {
            partnerList.forEach(partnerEntity -> {
                partnerDTOList.add(PartnerDTO.builder()
                        .code(partnerEntity.getCode())
                        .name(partnerEntity.getName())
                        .build());
            });
        }
        return partnerDTOList;
    }

}
