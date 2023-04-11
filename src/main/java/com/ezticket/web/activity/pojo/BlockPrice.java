package com.ezticket.web.activity.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="blockprice")
public class BlockPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BLOCKNO")
    private Integer blockNo;
    @Column(name = "BLOCKNAME")
    private String blockName;
    @Column(name = "ACTIVITYNO")
    private Integer activityNo;
    @Column(name = "BLOCKPRICE")
    private Integer blockPrice;

    @Column(name = "BLOCKTYPE")
    private Integer blockType;

}
