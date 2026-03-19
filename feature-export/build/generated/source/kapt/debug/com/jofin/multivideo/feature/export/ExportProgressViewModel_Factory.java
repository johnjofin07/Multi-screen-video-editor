package com.jofin.multivideo.feature.export;

import androidx.lifecycle.SavedStateHandle;
import com.jofin.multivideo.domain.repository.ProjectRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ExportProgressViewModel_Factory implements Factory<ExportProgressViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<ProjectRepository> projectRepositoryProvider;

  public ExportProgressViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ProjectRepository> projectRepositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.projectRepositoryProvider = projectRepositoryProvider;
  }

  @Override
  public ExportProgressViewModel get() {
    return newInstance(savedStateHandleProvider.get(), projectRepositoryProvider.get());
  }

  public static ExportProgressViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ProjectRepository> projectRepositoryProvider) {
    return new ExportProgressViewModel_Factory(savedStateHandleProvider, projectRepositoryProvider);
  }

  public static ExportProgressViewModel newInstance(SavedStateHandle savedStateHandle,
      ProjectRepository projectRepository) {
    return new ExportProgressViewModel(savedStateHandle, projectRepository);
  }
}
