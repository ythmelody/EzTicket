package com.ezticket.web.activity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash
public class CollectRedis {
    @Id
    private String collectno;
    private String qrcode;
    @TimeToLive
    private long time;
}
