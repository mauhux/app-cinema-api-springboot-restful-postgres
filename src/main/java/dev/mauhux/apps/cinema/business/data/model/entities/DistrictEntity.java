package dev.mauhux.apps.cinema.business.data.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "districts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistrictEntity {

    @Id
    @Column(name = "district_id")
    private String districtId;

    @Column(name = "district_name")
    private String districtName;

    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false)
    private ProvinceEntity province;
}
