package com.ezticket.core.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "redis_data")
public class RedisData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "redis_key")
    private String key;

    @Column(name = "redis_value")
    private String value;


}
