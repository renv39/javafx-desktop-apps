
package com.rendellvelasco.projectstaffingtool;


import com.rendellvelasco.projectstaffingtool.data.EmployeeRepository;
import com.rendellvelasco.projectstaffingtool.data.ProjectRepository;
import com.rendellvelasco.projectstaffingtool.services.AuthenticationService;
import com.rendellvelasco.projectstaffingtool.services.ResourceService;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Guice DI module.
 * Declares HOW each interface/class is bound and what scope it lives in.
 * Controllers and services just use @Inject — they never call "new" themselves.
 */
public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        // Repositories are Singletons — one shared instance for the whole app
        bind(ProjectRepository.class).in(Singleton.class);
        bind(EmployeeRepository.class).in(Singleton.class);

        // Services are also Singletons
        bind(AuthenticationService.class).in(Singleton.class);
        bind(ResourceService.class).in(Singleton.class);

        // Controllers are NOT singletons — a new instance is created each time
        // a screen is loaded. No binding needed; Guice handles @Inject constructors
        // automatically as long as they have a no-arg or @Inject constructor.
    }
}
