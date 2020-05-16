package vip.crazyboy.img.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvConfig {

    //项目域名
    @Value("${domain.name}")
    private String domainName;
}
