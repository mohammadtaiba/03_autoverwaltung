package de.fherfurt.carhub360.shared.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fherfurt.carhub360.contract.dto.ContractCreateRequest;
import de.fherfurt.carhub360.contract.dto.ContractResponse;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JacksonObjectMapperProviderTest {

    private final ObjectMapper objectMapper = new JacksonObjectMapperProvider().getContext(Object.class);

    @Test
    void deserializesIsoLocalDateFromContractJson() throws Exception {
        String json = """
                {
                  "customerId": 1,
                  "saleVehicleId": 1,
                  "rentVehicleId": 1,
                  "rentalContract": true,
                  "contractDate": "2026-08-11",
                  "rentalStartDate": "2026-08-11",
                  "rentalEndDate": "2026-08-11"
                }
                """;

        ContractCreateRequest request = objectMapper.readValue(json, ContractCreateRequest.class);

        assertEquals(LocalDate.of(2026, 8, 11), request.getContractDate());
        assertEquals(LocalDate.of(2026, 8, 11), request.getRentalStartDate());
        assertEquals(LocalDate.of(2026, 8, 11), request.getRentalEndDate());
    }

    @Test
    void serializesLocalDateAsIsoDateString() throws Exception {
        ContractResponse response = new ContractResponse();
        response.setContractDate(LocalDate.of(2026, 8, 11));

        String json = objectMapper.writeValueAsString(response);

        assertTrue(json.contains("\"contractDate\":\"2026-08-11\""));
    }
}
