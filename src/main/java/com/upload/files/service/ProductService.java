package com.upload.files.service;

import com.upload.files.entity.ListSearch;
import com.upload.files.entity.Product;
import com.upload.files.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> products() {
        return productRepository.findAll();
    }

    public Page<Product> pagingProducts(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10);
        return productRepository.pagingFindAll(pageable);
    }

    public Product findOne(Long proNo) {
        return productRepository.findOne(proNo);
    }

    public List<Product> findByFilter(ListSearch listSearch){
        return productRepository.findByFilter(listSearch);
    }

    /** 6개씩, 리스트 반환 */
    public Page<Product> pagingFindItemsByFilter(ListSearch listSearch, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 6);
        return productRepository.pagingFindByFilter(listSearch, pageable);
    }

}
