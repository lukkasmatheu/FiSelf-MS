package com.financial.self.models.entity;

import com.financial.self.models.enums.OperationType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Operation {
    private Date date;
    private BigDecimal amount;
    private OperationType operation;
    private Integer quantity;
    private String idProduct;
    private String type;
    private String description;
}
