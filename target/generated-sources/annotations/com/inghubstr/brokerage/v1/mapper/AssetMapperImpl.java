package com.inghubstr.brokerage.v1.mapper;

import com.inghubstr.brokerage.v1.dto.AssetDto;
import com.inghubstr.brokerage.v1.model.Asset;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T14:44:33+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class AssetMapperImpl implements AssetMapper {

    @Override
    public Asset toEntity(AssetDto dto) {
        if ( dto == null ) {
            return null;
        }

        Asset.AssetBuilder asset = Asset.builder();

        asset.userId( dto.getUserId() );
        asset.assetName( dto.getAssetName() );
        asset.size( dto.getSize() );
        asset.usableSize( dto.getUsableSize() );

        return asset.build();
    }

    @Override
    public List<Asset> toEntity(List<AssetDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<Asset> list = new ArrayList<Asset>( dto.size() );
        for ( AssetDto assetDto : dto ) {
            list.add( toEntity( assetDto ) );
        }

        return list;
    }

    @Override
    public AssetDto toDto(Asset entity) {
        if ( entity == null ) {
            return null;
        }

        AssetDto.AssetDtoBuilder<?, ?> assetDto = AssetDto.builder();

        assetDto.uuid( entity.getUuid() );
        assetDto.createdDate( entity.getCreatedDate() );
        assetDto.lastModifiedDate( entity.getLastModifiedDate() );
        assetDto.createdBy( entity.getCreatedBy() );
        assetDto.lastModifiedBy( entity.getLastModifiedBy() );
        assetDto.status( entity.getStatus() );
        assetDto.userId( entity.getUserId() );
        assetDto.assetName( entity.getAssetName() );
        assetDto.size( entity.getSize() );
        assetDto.usableSize( entity.getUsableSize() );

        return assetDto.build();
    }

    @Override
    public List<AssetDto> toDto(List<Asset> entity) {
        if ( entity == null ) {
            return null;
        }

        List<AssetDto> list = new ArrayList<AssetDto>( entity.size() );
        for ( Asset asset : entity ) {
            list.add( toDto( asset ) );
        }

        return list;
    }
}
