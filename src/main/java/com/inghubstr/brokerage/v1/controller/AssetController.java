package com.inghubstr.brokerage.v1.controller;

import com.inghubstr.brokerage.v1.dto.AssetDto;
import com.inghubstr.brokerage.v1.mapper.AssetMapper;
import com.inghubstr.brokerage.v1.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;
    private final AssetMapper assetMapper;

    @GetMapping
    public ResponseEntity<List<AssetDto>> listAssets(@RequestParam Long customerId) {
        return ResponseEntity.ok(assetMapper.toDto(assetService.getAssetsByUser(customerId)));
    }

}
