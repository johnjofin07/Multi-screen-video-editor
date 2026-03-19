package com.jofin.multivideo.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
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
public final class CreateProjectFromMediaUseCase_Factory implements Factory<CreateProjectFromMediaUseCase> {
  @Override
  public CreateProjectFromMediaUseCase get() {
    return newInstance();
  }

  public static CreateProjectFromMediaUseCase_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CreateProjectFromMediaUseCase newInstance() {
    return new CreateProjectFromMediaUseCase();
  }

  private static final class InstanceHolder {
    private static final CreateProjectFromMediaUseCase_Factory INSTANCE = new CreateProjectFromMediaUseCase_Factory();
  }
}
