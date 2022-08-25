// Generated by Dagger (https://dagger.dev).
package com.codmine.mukellef.domain.use_case.document_screen;

import com.codmine.mukellef.domain.repository.MobileOfficeRepository;
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
public final class PostDocumentReadingInfo_Factory implements Factory<PostDocumentReadingInfo> {
  private final Provider<MobileOfficeRepository> repositoryProvider;

  public PostDocumentReadingInfo_Factory(Provider<MobileOfficeRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public PostDocumentReadingInfo get() {
    return newInstance(repositoryProvider.get());
  }

  public static PostDocumentReadingInfo_Factory create(
      Provider<MobileOfficeRepository> repositoryProvider) {
    return new PostDocumentReadingInfo_Factory(repositoryProvider);
  }

  public static PostDocumentReadingInfo newInstance(MobileOfficeRepository repository) {
    return new PostDocumentReadingInfo(repository);
  }
}
