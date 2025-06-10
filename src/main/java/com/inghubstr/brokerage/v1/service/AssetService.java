package com.inghubstr.brokerage.v1.service;

import com.inghubstr.brokerage.v1.model.Asset;
import com.inghubstr.brokerage.v1.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;

    public List<Asset> getAssetsByUser(Long userId) {
        return assetRepository.findByUserId(userId);
    }

}
