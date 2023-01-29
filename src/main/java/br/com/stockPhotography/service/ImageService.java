package br.com.stockPhotography.service;

import br.com.stockPhotography.domain.image.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


public interface ImageService {

    void saveImage(Image image, MultipartFile file);

    byte[] downloadImage(Long id);

    Page<Image> getAllImages(Pageable paginacao);

    void deleteImage(Long id);

    Image detailImage(Long id);
}
