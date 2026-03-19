package com.jofin.multivideo.core.media;

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
public final class AndroidUriPermissionManager_Factory implements Factory<AndroidUriPermissionManager> {
  private final Provider<Context> contextProvider;

  public AndroidUriPermissionManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public AndroidUriPermissionManager get() {
    return newInstance(contextProvider.get());
  }

  public static AndroidUriPermissionManager_Factory create(Provider<Context> contextProvider) {
    return new AndroidUriPermissionManager_Factory(contextProvider);
  }

  public static AndroidUriPermissionManager newInstance(Context context) {
    return new AndroidUriPermissionManager(context);
  }
}
