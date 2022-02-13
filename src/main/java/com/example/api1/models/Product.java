package com.example.api1.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable =false)
    String description;

    @Column(nullable =false)
    String sellPrice;

    @Column(nullable =false)
    String barcode;

    @Column(name = "stock_available")
    Integer stockAvailable;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private Date lastModifiedDate;
}
