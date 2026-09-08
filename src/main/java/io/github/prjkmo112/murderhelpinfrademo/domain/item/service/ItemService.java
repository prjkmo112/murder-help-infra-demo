package io.github.prjkmo112.murderhelpinfrademo.domain.item.service;

import io.github.prjkmo112.murderhelpinfrademo.domain.item.dto.ItemRequest;
import io.github.prjkmo112.murderhelpinfrademo.domain.item.dto.ItemResponse;
import io.github.prjkmo112.murderhelpinfrademo.domain.item.entity.Item;
import io.github.prjkmo112.murderhelpinfrademo.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private static final String CACHE_NAME = "item";

    private final ItemRepository itemRepository;

    @Transactional
    public ItemResponse create(ItemRequest request) {
        Item item = new Item(request.getName(), request.getDescription());
        return new ItemResponse(itemRepository.save(item));
    }

    public List<ItemResponse> findAll() {
        return itemRepository.findAll().stream()
                .map(ItemResponse::new)
                .toList();
    }

    @Cacheable(cacheNames = CACHE_NAME, key = "#id")
    public ItemResponse findById(Long id) {
        return new ItemResponse(getItem(id));
    }

    @Transactional
    @CacheEvict(cacheNames = CACHE_NAME, key = "#id")
    public ItemResponse update(Long id, ItemRequest request) {
        Item item = getItem(id);
        item.update(request.getName(), request.getDescription());
        return new ItemResponse(item);
    }

    @Transactional
    @CacheEvict(cacheNames = CACHE_NAME, key = "#id")
    public void delete(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new NoSuchElementException("Item not found: id=" + id);
        }
        itemRepository.deleteById(id);
    }

    private Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Item not found: id=" + id));
    }
}