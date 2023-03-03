package com.example.numbletimedealserver.repository.product

import com.example.numbletimedealserver.domain.Product
import com.example.numbletimedealserver.domain.QProduct
import com.example.numbletimedealserver.domain.QProduct.product
import com.example.numbletimedealserver.request.ProductUpdateRequest
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service
import java.time.LocalTime
import java.util.*


@Service
class ProductRepositoryCustomImpl(private val jpaQueryFactory: JPAQueryFactory) : ProductRepositoryCustom {

    override fun findAllByAppointedTimeBetween(start: LocalTime, end: LocalTime): List<Product> =
        jpaQueryFactory.selectFrom(product).where(product._appointedTime.between(start, end)).fetch()


    override fun findAllByAdminId(adminId: UUID): List<Product> =
        jpaQueryFactory.selectFrom(product).where(product.admin.id.eq(adminId)).fetch()


    override fun countAllByAdminId(adminId: UUID): JPAQuery<Long> =
        jpaQueryFactory.select(product.count()).from(product).where(product.admin.id.eq(adminId))

}