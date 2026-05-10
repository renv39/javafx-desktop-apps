
package com.rendellvelasco.autoloanapp.config;

import com.rendellvelasco.autoloanapp.App;
import com.rendellvelasco.autoloanapp.repositories.*;
import com.rendellvelasco.autoloanapp.repositories.*;
import com.rendellvelasco.autoloanapp.state.LoanState;
import com.rendellvelasco.autoloanapp.utilities.FixedRateLoan;
import com.rendellvelasco.autoloanapp.utilities.LoanCalculation;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

public class AppModule extends AbstractModule {

    private final App app;

    // App instance passed in from App.java
    public AppModule(App app) {
        this.app = app;
    }

    @Override
    protected void configure() {
        bind(App.class).toInstance(this.app);

        bind(LoanState.class).in(Singleton.class);

        bind(IUserRepository.class)
                .to(SqlUserRepository.class);

        bind(ILoanRepository.class)
                .annotatedWith(Names.named("db"))
                .to(SqlLoanRepository.class);

        bind(ILoanRepository.class)
                .annotatedWith(Names.named("file"))
                .to(FileLoanRepository.class);

        bind(LoanCalculation.class)
                .to(FixedRateLoan.class);
    }
}