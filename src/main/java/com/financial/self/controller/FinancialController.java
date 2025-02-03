package com.financial.self.controller;


import com.financial.self.dto.ExceptionResponseDto;
import com.financial.self.models.entity.FinancialRecord;
import com.financial.self.models.response.ProductResponse;
import com.financial.self.service.FinancialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/financial")
@Tag(name = "Product Management", description = "Endpoints for managing Product.")
public class FinancialController {
    private final FinancialService financialService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieves all financial data", description = "Retrieve details of all tasks corresponding to authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product details retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication failure: Invalid access token",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))) })
    public ResponseEntity<FinancialRecord> getAll() {
        final var response = financialService.getAll();
        return ResponseEntity.ok(response);
    }

}
