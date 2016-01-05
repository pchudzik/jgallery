package com.pchudzik.jgallery.context;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by pawel on 05.01.16.
 */
@Configuration
@EntityScan(basePackages = "com.pchudzik.jgallery")
public class DatabaseConfiguration {
}
