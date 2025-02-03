package com.financial.self.service;

import com.financial.self.exception.InvalidTaskIdException;
import com.financial.self.models.entity.FinancialRecord;
import com.financial.self.models.entity.Operation;
import com.financial.self.models.entity.Product;
import com.financial.self.models.enums.OperationType;
import com.financial.self.models.response.ProductResponse;
import com.financial.self.utility.AuthenticatedUserIdProvider;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class FinancialService {
    private final Firestore firestore;
    private final AuthenticatedUserIdProvider authenticatedUserIdProvider;
    private final HashMap<String,FinancialRecord> cacheFinancial = new HashMap<>();


    public void updateFinancialRecord(String idCompany, Operation operation) {
        FinancialRecord record;
        try{
            var recordDoc = get(idCompany);
            record = recordDoc.toObject(FinancialRecord.class);
        }catch (Exception exception){
            record = new FinancialRecord();
            record.setIdCompany(idCompany);
            record.setTotalBalance(BigDecimal.ZERO);
            record.setTotalCost(BigDecimal.ZERO);
            record.setTotalSale(BigDecimal.ZERO);
            record.setOperations(new ArrayList<>());
        }

        if(operation != null && record != null){
            record.addOperation(operation);
            firestore.collection(FinancialRecord.name()).document(idCompany).set(record);
        }
    }

    @SneakyThrows
    private DocumentSnapshot get(@NonNull final String companyId) {
        final var retrievedDocument = firestore.collection(FinancialRecord.name()).document(companyId).get().get();
        final var documentExists = retrievedDocument.exists();
        if (Boolean.FALSE.equals(documentExists)) {
            throw new InvalidTaskIdException("No Financial register exists in the system with provided-id");
        }
        return retrievedDocument;
    }

    @SneakyThrows
    public FinancialRecord getAll() {
        final var idCompany = authenticatedUserIdProvider.getUserId();
        if(cacheFinancial.containsKey(idCompany)){
            return cacheFinancial.get(idCompany);
        }
        var recordDoc = get(idCompany);
        var financialRecord =  recordDoc.toObject(FinancialRecord.class);
        cacheFinancial.put(idCompany,financialRecord);
        return financialRecord;
    }

    public Operation buildOperationStock(Product product, OperationType operationType, String description){
        return Operation.builder()
                .date(new Date())
                .amount(product.getCost().multiply(BigDecimal.valueOf(product.getQuantity())))
                .operation(operationType)
                .quantity(product.getQuantity())
                .idProduct(product.getProductId())
                .description(description)
                .type("stock")
                .build();
    }

    public Operation buildOperationInStock(Product product, OperationType operationType, String description){
        return Operation.builder()
                .date(new Date())
                .amount(product.getSalePrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .operation(operationType)
                .quantity(product.getQuantity())
                .idProduct(product.getProductId())
                .description(description)
                .type("stock")
                .build();
    }

}
