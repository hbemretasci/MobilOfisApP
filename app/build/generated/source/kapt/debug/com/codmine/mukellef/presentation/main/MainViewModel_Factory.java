// Generated by Dagger (https://dagger.dev).
package com.codmine.mukellef.presentation.main;

import com.codmine.mukellef.domain.use_case.login_screen.SetUserLoginData;
import com.codmine.mukellef.domain.use_case.main.LogOut;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<SetUserLoginData> setUserLoginDataProvider;

  private final Provider<LogOut> logOutProvider;

  public MainViewModel_Factory(Provider<SetUserLoginData> setUserLoginDataProvider,
      Provider<LogOut> logOutProvider) {
    this.setUserLoginDataProvider = setUserLoginDataProvider;
    this.logOutProvider = logOutProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(setUserLoginDataProvider.get(), logOutProvider.get());
  }

  public static MainViewModel_Factory create(Provider<SetUserLoginData> setUserLoginDataProvider,
      Provider<LogOut> logOutProvider) {
    return new MainViewModel_Factory(setUserLoginDataProvider, logOutProvider);
  }

  public static MainViewModel newInstance(SetUserLoginData setUserLoginData, LogOut logOut) {
    return new MainViewModel(setUserLoginData, logOut);
  }
}
