package com.ecommerce.system.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class ProductDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String description;

    private String brand;

    private BigDecimal price;

    private int categoryId;

    private Date releaseDate;

    private boolean productAvailable;

    private int stockQuantity;

    private String imagePath;

}
