package de.adtelligence.jdk17asmbug;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationScoped
@ApplicationPath("/rest")
public class RestApplication extends Application {

}