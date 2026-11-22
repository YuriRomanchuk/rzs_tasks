package com.ua.amazonua.service;

import com.ua.amazonua.domain.Characteristic;
import com.ua.amazonua.domain.Product;
import com.ua.amazonua.exception.ProductNotFoundException;
import com.ua.amazonua.repository.ProductRepository;
import com.ua.amazonua.service.mapper.ProductMapper;
import com.ua.amazonua.web.dto.ProductCardDto;
import com.ua.amazonua.web.dto.ProductDetailDto;
import com.ua.amazonua.web.dto.ProductRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * UNIT-тест сервісу: репозиторій замокано, маппер справжній.
 * Перевіряємо лише логіку ProductServiceImpl. (Повніше про тести — на 1/4.)
 */
class ProductServiceImplTest {

    private final ProductRepository repository = mock(ProductRepository.class);
    private final ProductMapper mapper = new ProductMapper();
    private final ProductService service = new ProductServiceImpl(repository, mapper);

    private Product sampleProduct() {
        return new Product(
                1L, "Навушники", new BigDecimal("1299.00"),
                "/images/h.svg",
                List.of("/images/h.svg", "/images/h2.svg"),
                "Опис", "Аудіо",
                List.of(new Characteristic("Колір", "Чорний"))
        );
    }

    private ProductRequest sampleRequest() {
        return new ProductRequest("Миша", new BigDecimal("749.00"), "/images/m.svg",
                List.of("/images/m.svg"), "Опис миші", "Периферія", List.of());
    }

    @Test
    void findAll_withoutCategory_mapsAllProductsToCards() {
        when(repository.findAll()).thenReturn(List.of(sampleProduct()));

        List<ProductCardDto> cards = service.findAll(null);

        assertThat(cards).hasSize(1);
        assertThat(cards.get(0).name()).isEqualTo("Навушники");
    }

    @Test
    void findAll_withCategory_filtersByCategory() {
        when(repository.findByCategory("Аудіо")).thenReturn(List.of(sampleProduct()));

        List<ProductCardDto> cards = service.findAll("Аудіо");

        assertThat(cards).hasSize(1);
        verify(repository).findByCategory("Аудіо");
    }

    @Test
    void findById_returnsDetailWithImagesAndCharacteristics() {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleProduct()));

        ProductDetailDto detail = service.findById(1L);

        assertThat(detail.id()).isEqualTo(1L);
        assertThat(detail.images()).hasSize(2);
        assertThat(detail.characteristics().get(0).name()).isEqualTo("Колір");
    }

    @Test
    void findById_throwsWhenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(999L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void create_savesProductWithoutId() {
        when(repository.save(any(Product.class))).thenAnswer(inv -> {
            Product p = inv.getArgument(0);
            p.setId(5L);
            return p;
        });

        ProductDetailDto created = service.create(sampleRequest());

        assertThat(created.id()).isEqualTo(5L);
        assertThat(created.name()).isEqualTo("Миша");
    }

    @Test
    void update_existingProduct_keepsId() {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleProduct()));
        when(repository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));

        ProductDetailDto updated = service.update(1L, sampleRequest());

        assertThat(updated.id()).isEqualTo(1L);
        assertThat(updated.name()).isEqualTo("Миша");
    }

    @Test
    void update_missingProduct_throws() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(999L, sampleRequest()))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void delete_existingProduct_ok() {
        when(repository.deleteById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void delete_missingProduct_throws() {
        when(repository.deleteById(999L)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(999L))
                .isInstanceOf(ProductNotFoundException.class);
    }
}
