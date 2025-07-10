package dev.mauhux.apps.cinema.business.data.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentEntity {

    @Id
    @Column(name = "department_id")
    private String departmentId;

    @Column(name = "department_name")
    private String departmentName;
}
