package com.jerry.shop.controller;

import com.jerry.shop.dto.ProductDto;
import com.jerry.shop.exception.ApiException;
import com.jerry.shop.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "產品資訊 API")
@AllArgsConstructor
@RequestMapping("product")
public class ProductController {
    private ProductService productService;

    @ApiOperation("取得所有產品")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @GetMapping("/")
    public List<ProductDto> findProducts() {
        return productService.findAll();
    }

    @ApiOperation("取得產品")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ProductDto findProduct(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(ApiException::noData);
    }

    @ApiOperation("新增/更新產品")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto, BindingResult result) {
        if (result.hasErrors()) {
            throw ApiException.fail(result.getFieldErrors()
                    .stream()
                    .map(t -> t.getField() + ":" + t.getDefaultMessage())
                    .collect(Collectors.joining(",")));
        }
        productService.save(id, productDto);
    }

    @ApiOperation("刪除產品")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.delete(id);
    }
}
