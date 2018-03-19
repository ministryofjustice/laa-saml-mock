package mujina.idp;

import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.catalina.valves.rewrite.RewriteValve;

@Configuration
public class TomcatRedirectHttpToHttpsConfig {
  @Bean TomcatEmbeddedServletContainerFactory servletContainerFactory() {
    TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
    factory.addContextValves(new RewriteValve());
    return factory;
  }
}
