package io.project.wolfgym.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.sql.SQLException;

@Configuration
@EnableJpaAuditing
public class H2Config {
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp","-tcpAllowOthers","-tcpPort","9092");
    }
// Для удобной работы с интерфейсом бд в ИДЕ
}

