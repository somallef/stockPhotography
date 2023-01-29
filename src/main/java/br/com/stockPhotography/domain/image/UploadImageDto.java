package br.com.stockPhotography.domain.image;

public record UploadImageDto(
        Resolution resolution,
        LicenseType license,
        String filePath,
        String title,
        String description,
        String imageFileName
) {
}
