package br.com.stockPhotography.service;

import br.com.stockPhotography.config.BucketName;
import br.com.stockPhotography.domain.image.Image;
import br.com.stockPhotography.domain.image.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final FileStore fileStore;
    private final ImageRepository repository;

    @Override
    @Transactional
    public void saveImage(Image image, MultipartFile file) {

        //check if the file is empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        //Check if the file is an image
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File uploaded is not an image");
        }
        //get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        //Save Image in S3 and then save  in the database
        String path = String.format("%s/%s", BucketName.VIELAS_DEV.getBucketName(), UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());

        try {
            fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        //TODO: garantir que caso lance exceção, não seja gravado no banco
        image.setFilePath(path);
        image.setImageFileName(fileName);
        repository.save(image);
    }

    @Override
    public byte[] downloadImage(Long id) {
        Image image = repository.findById(id).get();
        return fileStore.download(image.getFilePath(), image.getImageFileName());
    }

    @Override
    public Page<Image> getAllImages(Pageable paginacao) {
        return repository.findAllByAvailableTrue(paginacao);
    }

    @Override
    @Transactional
    public void deleteImage(Long id) {
        var image = repository.getReferenceById(id);
        image.delete();
    }

    @Override
    public Image detailImage(Long id) {
        return repository.getReferenceById(id);
    }
}
