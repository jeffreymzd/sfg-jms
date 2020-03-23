package com.github.jeffrey.spring.boot.sfg.jms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jgroups.util.UUID;

import java.io.Serializable;

/**
 * Created by jeffreymzd on 3/22/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldMessage implements Serializable {

    private static final long serialVersionUID = -9013155626995973471L;

    private UUID id;
    private String message;
}
