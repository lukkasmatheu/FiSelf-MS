package com.financial.self.service;

import com.financial.self.exception.InvalidTaskIdException;
import com.financial.self.exception.TaskOwnershipViolationException;
import com.financial.self.models.entity.Product;
import com.financial.self.models.request.ProductCreateRequest;
import com.financial.self.models.response.ProductResponse;
import com.financial.self.utility.AuthenticatedUserIdProvider;
import com.financial.self.utility.DateUtility;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final Firestore firestore;
	private final DateUtility dateUtility;
	private final AuthenticatedUserIdProvider authenticatedUserIdProvider;


	public ProductResponse getById(String productId) {
		final var retrievedDocument = get(productId);
		final var product = retrievedDocument.toObject(Product.class);
		verifyProductOwnership(product);
		return createResponse(retrievedDocument, product);
	}

	@SneakyThrows
	public List<ProductResponse> getAll() {
		final var userId = authenticatedUserIdProvider.getUserId();
		return firestore.collection(Product.name()).whereEqualTo("createdBy", userId)
				.get().get().getDocuments()
				.stream()
				.map(document -> {
					final var product = document.toObject(Product.class);
					return createResponse(document, product);
				}).toList();
	}
	

	public void create(@NonNull final ProductCreateRequest productCreateRequest) {
		var productEntity = Product.fromRequest(productCreateRequest);
		productEntity.setCreateByOwner(authenticatedUserIdProvider.getUserId());
		firestore.collection(Product.name()).document().set(productEntity);
	}
	

	public void update(@NonNull final String taskId, @NonNull final ProductCreateRequest productCreateRequest) {
		final var retrievedDocument = get(taskId);
		var product = retrievedDocument.toObject(Product.class);
		verifyProductOwnership(product);
		product = updateValues(product,productCreateRequest);
		firestore.collection(Product.name()).document(retrievedDocument.getId()).set(product);
	}



	public void delete(@NonNull final String productIdf) {
		final var document = get(productIdf);
		final var product = document.toObject(Product.class);
		verifyProductOwnership(product);
		
		firestore.collection(Product.name()).document(document.getId()).delete();
	}


	private void verifyProductOwnership(@NonNull final Product product) {
		final var userId = authenticatedUserIdProvider.getUserId();
		final var taskBelongsToUser = product.getCreateByOwner().equals(userId);
		if (Boolean.FALSE.equals(taskBelongsToUser)) {
			throw new TaskOwnershipViolationException();
		}
	}
	

	@SneakyThrows
	private DocumentSnapshot get(@NonNull final String productId) {
		final var retrievedDocument = firestore.collection(Product.name()).document(productId).get().get();
		final var documentExists = retrievedDocument.exists();
		if (Boolean.FALSE.equals(documentExists)) {
			throw new InvalidTaskIdException("No product register exists in the system with provided-id");
		}
		return retrievedDocument;
	}

	private ProductResponse createResponse(final DocumentSnapshot document, final Product product) {
		return ProductResponse.builder()
				.id(document.getId())
				.productName(product.getProductName())
				.description(product.getDescription())
				.quantity(product.getQuantity())
				.category(product.getCategory())
				.cost(product.getCost())
				.image(product.getImage())
				.salePrice(product.getSalePrice())
				.expirationDate(product.getExpirationDate())
				.status(product.getStatus())
				.createdAt(dateUtility.convert(Objects.requireNonNull(document.getCreateTime())))
				.updatedAt(dateUtility.convert(Objects.requireNonNull(document.getUpdateTime())))
				.build();
	}

	private Product updateValues(Product product, @NonNull ProductCreateRequest productCreateRequest) {
		return Product.builder()
				.productId(getUpdatedValue(productCreateRequest.getIdProduct(), product.getProductId()))
				.companyId(getUpdatedValue(productCreateRequest.getIdCompany(), product.getCompanyId()))
				.creationDate(getUpdatedValue(productCreateRequest.getCreationDate(), product.getCreationDate()))
				.category(getUpdatedValue(productCreateRequest.getCategory(), product.getCategory()))
				.productName(getUpdatedValue(productCreateRequest.getProductName(), product.getProductName()))
				.description(getUpdatedValue(productCreateRequest.getDescription(), product.getDescription()))
				.cost(getUpdatedValue(productCreateRequest.getCost(), product.getCost()))
				.image(getUpdatedValue(productCreateRequest.getImage(), product.getImage()))
				.salePrice(getUpdatedValue(productCreateRequest.getSalePrice(), product.getSalePrice()))
				.expirationDate(getUpdatedValue(productCreateRequest.getExpirationDate(), product.getExpirationDate()))
				.status(getUpdatedValue(productCreateRequest.getStatus(), product.getStatus()))
				.quantity(getUpdatedValue(productCreateRequest.getQuantity(), product.getQuantity()))
				.supplier(getUpdatedValue(productCreateRequest.getSupplier(), product.getSupplier()))
				.build();
	}

	private <T> T getUpdatedValue(T newValue, T currentValue) {
		return newValue != null ? newValue : currentValue;
	}

}
