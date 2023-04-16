package com.ezticket.web.activity.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
public class PlaceModel {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer modelno;
    @Column
    private String modelName;
    @Column
    private Integer modelStatus;
    @Column
    private byte[] modelImg;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name="modelno", referencedColumnName = "modelno")
    private List<BlockModel> blockModels;
}
