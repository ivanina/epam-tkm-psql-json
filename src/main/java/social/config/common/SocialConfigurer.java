package social.config.common;


import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;

public abstract class SocialConfigurer<A> extends SocialAutoConfigurerAdapter {

    @Override
    protected abstract ConnectionFactory<A> createConnectionFactory();

    protected abstract Class<A> apiBindingClass();

}
