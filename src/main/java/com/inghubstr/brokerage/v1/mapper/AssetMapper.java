package com.inghubstr.brokerage.v1.mapper;

import com.inghubstr.brokerage.v1.dto.AssetDto;
import com.inghubstr.brokerage.v1.mapper.base.BaseMapper;
import com.inghubstr.brokerage.v1.model.Asset;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssetMapper extends BaseMapper<Asset, AssetDto> {

}
