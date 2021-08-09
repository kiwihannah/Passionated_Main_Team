package com.upload.files.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upload.files.entity.ListSearch;
import com.upload.files.entity.Product;
import com.upload.files.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    @PersistenceContext private EntityManager em;

    public void save(Product product) {
        em.persist(product);
    }

    public Product findOne(Long proNo) {
        return em.find(Product.class, proNo);
    }

    public List<Product> findAll() {
        return em.createQuery("select p from Product p", Product.class)
                .getResultList();
    }

    /** pagination & listing All */
    public Page<Product> pagingFindAll(Pageable pageable){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct product = QProduct.product;
        QueryResults<Product> results =
                queryFactory.select(product)
                    .from(product)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetchResults();

        List<Product> products = results.getResults();
        Long total = results.getTotal();

        return new PageImpl<>(products, pageable, total);
    }

    public void deleteProduct(Product product) {
        em.remove(em.contains(product) ? product : em.merge(product));
    }

    /** 전체 sorting 사이즈를 받아서 아이템 수만큼 값을 찾아오기 위한 로직 */
    public List<Product> findByFilter(ListSearch listSearch){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct product = QProduct.product;

        return queryFactory
                .select(product)
                .from(product)
                .where(regionLike(listSearch.getRegion()),
                        seasonLike(listSearch.getSeason()),
                        themeLike(listSearch.getTheme()))
                .fetch();
    }

    /** pagination & sorting */
    public Page<Product> pagingFindByFilter(ListSearch listSearch, Pageable pageable){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct product = QProduct.product;

        QueryResults<Product> result =
                queryFactory.select(product)
                        .from(product)
                        .where(regionLike(listSearch.getRegion()),
                                seasonLike(listSearch.getSeason()),
                                themeLike(listSearch.getTheme()))
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .fetchResults();

        List<Product> products = result.getResults();
        Long total = result.getTotal();

        return new PageImpl<>(products, pageable, total);
    }

    private BooleanExpression regionLike(String regionCond) {
        QProduct product = QProduct.product;
        return !StringUtils.hasText(regionCond) ? null : product.region.like(regionCond);
    }

    private BooleanExpression seasonLike(String seasonCond) {
        QProduct product = QProduct.product;
        return !StringUtils.hasText(seasonCond) ? null : product.season.like(seasonCond);
    }

    private BooleanExpression themeLike(String themeCond) {
        QProduct product = QProduct.product;
        return !StringUtils.hasText(themeCond) ? null : product.theme.like(themeCond);
    }

}
