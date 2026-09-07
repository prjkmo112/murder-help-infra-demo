package io.github.prjkmo112.murderhelpinfrademo.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item create(ItemRequest request) {
        Item item = new Item(null, request.getName(), request.getDescription());
        return itemRepository.save(item);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Item not found: id=" + id));
    }

    public Item update(Long id, ItemRequest request) {
        Item item = findById(id);
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        return itemRepository.save(item);
    }

    public void delete(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new NoSuchElementException("Item not found: id=" + id);
        }
        itemRepository.deleteById(id);
    }
}