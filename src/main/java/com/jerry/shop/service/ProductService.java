package com.jerry.shop.service;

import com.jerry.shop.Constany;
import com.jerry.shop.dto.ProductDto;
import com.jerry.shop.entity.Product;
import com.jerry.shop.repo.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductService {
  private ProductRepo productRepo;

  public List<ProductDto> findAll() {
    return productRepo.findAll().stream().map(ProductDto::valueOf).collect(Collectors.toList());
  }

  public Optional<ProductDto> findById(Long id) {
    return productRepo.findById(id).map(ProductDto::valueOf);
  }

  public void update(Long id, ProductDto productDto) {
    try {
      System.out.println(Thread.currentThread().getName() + " update begin");

      Product product = productRepo.findById(id).orElseThrow(EntityNotFoundException::new);
      product.setName(productDto.getName());
      productRepo.save(product);

      System.out.println(Thread.currentThread().getName() + " update success");
    } catch (OptimisticLockingFailureException e) {
      System.out.println("Optimistic locking occur");
      e.printStackTrace();
    }
  }

  public void save(Long id, ProductDto productDto) {
    boolean success = false;
    while (!success) {
      try {
        System.out.println(Thread.currentThread().getName() + " update begin");
        Product product =
            productRepo
                .findById(id)
                .map(
                    // 更新產品資訊
                    t -> {
                      t.setName(productDto.getName());
                      t.setPrice(productDto.getPrice());
                      t.setInventory(productDto.getInventory());
                      t.setUpdatedBy(Constany.SYS_ID);
                      t.setUpdatedAt(LocalDateTime.now());
                      return t;
                    })
                .orElse(
                    // 新增產品資訊
                    Product.builder()
                        .name(productDto.getName())
                        .inventory(productDto.getInventory())
                        .price(productDto.getPrice())
                        .createdBy(Constany.SYS_ID)
                        .createdAt(LocalDateTime.now())
                        .build());
        productRepo.save(product);
        System.out.println(Thread.currentThread().getName() + " update success");
        success = true;
      } catch (OptimisticLockingFailureException e) {
        System.out.println(Thread.currentThread().getName() + " Optimistic locking occur");
        try {
          Thread.sleep(1000L); // wait 1 seconds
        } catch (InterruptedException interruptedException) {
          System.out.println("Thread interrupted");
        }
      }
    }
  }

  @Transactional
  public void delete(Long id) {
    productRepo.deleteById(id);
  }

  public void initData() {
    var proList =
        List.of(
            Product.builder()
                .name("prod1")
                .price(100)
                .inventory(1000)
                .createdAt(LocalDateTime.now())
                .createdBy(Constany.SYS_ID)
                .build(),
            Product.builder()
                .name("prod2")
                .price(200)
                .inventory(2000)
                .createdAt(LocalDateTime.now())
                .createdBy(Constany.SYS_ID)
                .build(),
            Product.builder()
                .name("prod3")
                .price(300)
                .inventory(3000)
                .createdAt(LocalDateTime.now())
                .createdBy(Constany.SYS_ID)
                .build());
    productRepo.saveAll(proList);
  }
}
