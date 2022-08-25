// Generated by Dagger (https://dagger.dev).
package com.codmine.mukellef.presentation.splash_screen;

import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData;
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
public final class SplashViewModel_Factory implements Factory<SplashViewModel> {
  private final Provider<GetUserLoginData> getUserLoginDataProvider;

  public SplashViewModel_Factory(Provider<GetUserLoginData> getUserLoginDataProvider) {
    this.getUserLoginDataProvider = getUserLoginDataProvider;
  }

  @Override
  public SplashViewModel get() {
    return newInstance(getUserLoginDataProvider.get());
  }

  public static SplashViewModel_Factory create(
      Provider<GetUserLoginData> getUserLoginDataProvider) {
    return new SplashViewModel_Factory(getUserLoginDataProvider);
  }

  public static SplashViewModel newInstance(GetUserLoginData getUserLoginData) {
    return new SplashViewModel(getUserLoginData);
  }
}
