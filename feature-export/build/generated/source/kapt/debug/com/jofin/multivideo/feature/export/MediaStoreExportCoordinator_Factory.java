package com.jofin.multivideo.feature.export;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class MediaStoreExportCoordinator_Factory implements Factory<MediaStoreExportCoordinator> {
  private final Provider<Context> contextProvider;

  public MediaStoreExportCoordinator_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public MediaStoreExportCoordinator get() {
    return newInstance(contextProvider.get());
  }

  public static MediaStoreExportCoordinator_Factory create(Provider<Context> contextProvider) {
    return new MediaStoreExportCoordinator_Factory(contextProvider);
  }

  public static MediaStoreExportCoordinator newInstance(Context context) {
    return new MediaStoreExportCoordinator(context);
  }
}
