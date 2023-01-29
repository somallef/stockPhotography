package br.com.stockPhotography.controller;

import br.com.stockPhotography.domain.image.Image;
import br.com.stockPhotography.domain.image.ImageDataDetailDto;
import br.com.stockPhotography.domain.image.UploadImageDto;
import br.com.stockPhotography.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@AllArgsConstructor
@RequestMapping("images")
public class ImageController {
    private ImageService service;

    @GetMapping
    public ResponseEntity<Page<ImageDataDetailDto>> listAllImages(
            @PageableDefault(/*size = 10, sort = { "date" }*/) //altera as configurações padrão de paginação e ordenação do Spring
            Pageable paginacao) {
        var page = service.getAllImages(paginacao) //retorna uma lista
                .map(ImageDataDetailDto::new); //instancia um obj de listagem e envia o list de médicos para o seu construtor
        return ResponseEntity.ok(page); //retorna conteudo e código 200
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity saveImage(@RequestPart("data") UploadImageDto uploadImageDto,
                                    @RequestPart("file") MultipartFile file,
                                           UriComponentsBuilder uriBuilder) {

        var image = new Image(uploadImageDto);
        service.saveImage(image, file);

        //constroi a URI para o cabeçalho Location
        var uri = uriBuilder
                .path("/images/{id}")
                .buildAndExpand(image.getId())
                .toUri();
        return ResponseEntity
                .created(uri) //cria o cabeçalho location com o código 201
                .body(new ImageDataDetailDto(image)); // devolve o conteudo do recurso criado

    }

    @GetMapping(value = "{id}/image/download")
    public byte[] downloadImage(@PathVariable("id") Long id) {
        return service.downloadImage(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.deleteImage(id);
        return ResponseEntity.noContent().build(); //retorna código 204
    }

    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable Long id) {
        var image = service.detailImage(id);
        return ResponseEntity.ok(new ImageDataDetailDto(image));
    }

}
