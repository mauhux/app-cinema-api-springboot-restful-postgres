package dev.mauhux.apps.cinema.business.data.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "provinces")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProvinceEntity {

    @Id
    @Column(name = "province_id")
    private String provinceId;

    @Column(name = "province_name")
    private String provinceName;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity department;
}
