package xyz.arnau.setlisttoplaylist.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.web.server.AbstractConfigurableWebServerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class RemoveErrorPages implements BeanPostProcessor, EnvironmentAware {
    private Environment environment;

    @Override
    public Object postProcessBeforeInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        if( bean instanceof AbstractConfigurableWebServerFactory) {
            if( "false".equals( environment.getProperty("server.error.whitelabel.enabled") ) ) {
                ((AbstractConfigurableWebServerFactory) bean).getErrorPages().clear();
            }
        }
        return bean;
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.environment = environment;
    }
}