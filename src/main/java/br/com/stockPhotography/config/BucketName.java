package br.com.stockPhotography.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    VIELAS_DEV("vielas-dev");
    private final String bucketName;
}
