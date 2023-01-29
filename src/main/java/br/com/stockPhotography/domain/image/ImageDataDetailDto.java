package br.com.stockPhotography.domain.image;

public record ImageDataDetailDto(
        Long id,
        Resolution resolution,
        LicenseType license,
        String filePath,
        String title,
        String description,
        String imageFileName,
        boolean available
) {
    public ImageDataDetailDto(Image image) {
        this(
                image.getId(),
                image.getResolution(),
                image.getLicense(),
                image.getFilePath(),
                image.getTitle(),
                image.getDescription(),
                image.getImageFileName(),
                image.isAvailable()
        );
    }
}
