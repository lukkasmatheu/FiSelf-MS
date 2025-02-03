package com.financial.self.models.entity;

import com.financial.self.models.enums.OperationType;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinancialRecord {
    private static final String ENTITY_NAME = "operation";
    public static String name() {
        return ENTITY_NAME;
    }
    private String idCompany;
    private BigDecimal totalBalance;
    private BigDecimal totalSale;
    private BigDecimal totalCost;
    private Boolean isPositive;
    private List<Operation> operations = new ArrayList<>();


    public void addOperation(Operation operation) {
        this.operations.add(operation);
        if (operation.getOperation() == OperationType.CASH_OUT) {
            this.totalCost = this.totalCost.add(operation.getAmount());
            this.totalBalance = this.totalBalance.subtract(operation.getAmount());
        } else {
            this.totalSale = this.totalSale.add(operation.getAmount());
            this.totalBalance = this.totalBalance.add(operation.getAmount());
        }
        this.isPositive = this.totalBalance.compareTo(BigDecimal.ZERO) >= 0;
    }
}