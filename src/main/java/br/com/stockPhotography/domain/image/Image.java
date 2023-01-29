package br.com.stockPhotography.domain.image;

import jakarta.persistence.*;
import lombok.*;
@Table(name = "images")
@Entity(name = "Image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Resolution resolution;
    private LicenseType license;
    private String filePath;
    private String title;
    private String description;
    private String imageFileName;
    private boolean available;

    public Image(UploadImageDto uploadImageDto) {
        this.resolution = uploadImageDto.resolution();
        this.license = uploadImageDto.license();
        this.filePath = uploadImageDto.filePath();
        this.title = uploadImageDto.title();
        this.description = uploadImageDto.description();
        this.imageFileName = uploadImageDto.imageFileName();
        this.available = true;
    }

    public void delete() {
        this.available = false;
    }
}
