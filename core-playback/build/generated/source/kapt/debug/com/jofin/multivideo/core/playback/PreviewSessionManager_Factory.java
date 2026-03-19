package com.jofin.multivideo.core.playback;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class PreviewSessionManager_Factory implements Factory<PreviewSessionManager> {
  private final Provider<Context> contextProvider;

  public PreviewSessionManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public PreviewSessionManager get() {
    return newInstance(contextProvider.get());
  }

  public static PreviewSessionManager_Factory create(Provider<Context> contextProvider) {
    return new PreviewSessionManager_Factory(contextProvider);
  }

  public static PreviewSessionManager newInstance(Context context) {
    return new PreviewSessionManager(context);
  }
}
