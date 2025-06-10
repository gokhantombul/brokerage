package com.inghubstr.brokerage.v1.repository;

import com.inghubstr.brokerage.v1.model.Asset;
import com.inghubstr.brokerage.v1.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findByUserId(Long userId);

    Optional<Asset> findByUserIdAndAssetName(Long userId, String assetName);

    Optional<Asset> findByAssetNameAndStatus(String assetName, Status status);

}