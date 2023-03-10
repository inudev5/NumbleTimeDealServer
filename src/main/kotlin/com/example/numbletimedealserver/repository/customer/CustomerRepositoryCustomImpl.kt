package com.example.numbletimedealserver.repository.customer

import com.example.numbletimedealserver.domain.Customer
import com.example.numbletimedealserver.domain.QCustomer
import com.example.numbletimedealserver.domain.QCustomer.*

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository



class CustomerRepositoryCustomImpl(private val jpaQueryFactory: JPAQueryFactory):CustomerRepositoryCustom{
    override fun findByUsernameAndPassword(username: String, pw: String): Customer? {
        return jpaQueryFactory.selectFrom(customer).where(customer.name.eq(username).and(customer.password.eq(pw))).fetchOne()
    }

    override fun pageAll(pageable: Pageable): Page<Customer> {
        val content=jpaQueryFactory.selectFrom(customer).fetch()
        val countQuery = jpaQueryFactory.select(customer.count()).from(customer)
        return PageableExecutionUtils.getPage(content,pageable){countQuery.fetchOne()?:0L}
    }
}