package dev.mauhux.apps.cinema.business.api.resources;

import dev.mauhux.apps.cinema.business.api.dtos.DepartmentResponseDto;
import dev.mauhux.apps.cinema.business.api.dtos.DistrictResponseDto;
import dev.mauhux.apps.cinema.business.api.dtos.ProvinceResponseDto;
import dev.mauhux.apps.cinema.business.domain.services.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/locations")
@AllArgsConstructor
@Tag(name = "Locations", description = "REST API for location information: departments, provinces, and districts")
public class LocationResource {

    private final LocationService locationService;

    // =========================================================================
    // Get All Departments
    // =========================================================================
    @Operation(
            summary = "Get all departments",
            description = "Returns a list of all departments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Departments found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DepartmentResponseDto.class))),
                    @ApiResponse(responseCode = "204", description = "No departments found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentResponseDto>> getDepartments() {
        try {
            List<DepartmentResponseDto> departments = locationService.getAllDepartments();
            if (departments.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(departments);
        } catch (Exception e) {
            log.error("Error retrieving departments", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // =========================================================================
    // Get Provinces by Department ID
    // =========================================================================
    @Operation(
            summary = "Get provinces by department ID",
            description = "Returns a list of provinces for a given department",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provinces found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProvinceResponseDto.class))),
                    @ApiResponse(responseCode = "204", description = "No provinces found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/departments/{departmentId}/provinces")
    public ResponseEntity<List<ProvinceResponseDto>> getProvinces(@PathVariable String departmentId) {
        try {
            List<ProvinceResponseDto> provinces = locationService.getProvincesByDepartmentId(departmentId);
            if (provinces.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(provinces);
        } catch (Exception e) {
            log.error("Error retrieving provinces for department {}", departmentId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // =========================================================================
    // Get Districts by Province ID
    // =========================================================================
    @Operation(
            summary = "Get districts by province ID",
            description = "Returns a list of districts for a given province",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Districts found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DistrictResponseDto.class))),
                    @ApiResponse(responseCode = "204", description = "No districts found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/provinces/{provinceId}/districts")
    public ResponseEntity<List<DistrictResponseDto>> getDistricts(@PathVariable String provinceId) {
        try {
            List<DistrictResponseDto> districts = locationService.getDistrictsByProvinceId(provinceId);
            if (districts.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(districts);
        } catch (Exception e) {
            log.error("Error retrieving districts for province {}", provinceId, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
