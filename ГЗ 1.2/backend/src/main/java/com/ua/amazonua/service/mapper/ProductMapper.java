package com.ua.amazonua.service.mapper;

import com.ua.amazonua.domain.Characteristic;
import com.ua.amazonua.domain.Product;
import com.ua.amazonua.web.dto.CharacteristicDto;
import com.ua.amazonua.web.dto.ProductCardDto;
import com.ua.amazonua.web.dto.ProductDetailDto;
import com.ua.amazonua.web.dto.ProductRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Перетворення доменного {@link Product} у DTO.
 *
 * ШАР: service/mapper. Винесено ОКРЕМО від сервісу (SRP): сервіс відповідає
 * за бізнес-логіку, маппер — лише за перетворення форматів.
 * Коли мапінгів побільшає, тут буде одне місце для всіх.
 */
@Component
public class ProductMapper {

    public ProductCardDto toCard(Product product) {
        return new ProductCardDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }

    public ProductDetailDto toDetail(Product product) {
        List<CharacteristicDto> characteristics = product.getCharacteristics().stream()
                .map(c -> new CharacteristicDto(c.getName(), c.getValue()))
                .toList();
        return new ProductDetailDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.getImages(),
                product.getDescription(),
                product.getCategory(),
                characteristics
        );
    }

    /**
     * Вхідний DTO → доменний об'єкт (для створення/оновлення).
     * id не встановлюємо тут: при створенні його призначить репозиторій,
     * при оновленні — встановить сервіс зі шляху запиту.
     */
    public Product toDomain(ProductRequest request) {
        List<Characteristic> characteristics = request.characteristics() == null
                ? List.of()
                : request.characteristics().stream()
                        .map(c -> new Characteristic(c.name(), c.value()))
                        .toList();
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setImageUrl(request.imageUrl());
        product.setImages(request.images());
        product.setDescription(request.description());
        product.setCategory(request.category());
        product.setCharacteristics(characteristics);
        return product;
    }
}
