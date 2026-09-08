package io.github.prjkmo112.murderhelpinfrademo.domain.item.repository;

import io.github.prjkmo112.murderhelpinfrademo.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}