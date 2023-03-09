package com.example.numbletimedealserver.domain

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import org.hibernate.annotations.BatchSize

@Entity
class Customer (
    name:String,
    @Column
    var password:String,
    @Column(nullable = false)
    val role:ROLE,
    @JsonManagedReference
    @OneToMany(mappedBy = "customer")
    @BatchSize(size = 100)
    private val _orders:MutableList<Order> = mutableListOf(),

):PrimaryKeyEntity(){
    @Column(nullable = false, unique = true)
    var name:String = name
        protected set
    val orders:List<Order> get() = _orders
}