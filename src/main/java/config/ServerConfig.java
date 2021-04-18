package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config.properties")
public interface ServerConfig  extends Config {
    @Key("url1")
    String url1();

    @Key("login")
    String login();

    @Key("passwors")
    String passwors();
}