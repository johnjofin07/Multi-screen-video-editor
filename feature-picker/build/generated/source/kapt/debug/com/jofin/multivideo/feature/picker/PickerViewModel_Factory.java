package com.jofin.multivideo.feature.picker;

import com.jofin.multivideo.core.media.MediaMetadataReader;
import com.jofin.multivideo.core.media.UriPermissionManager;
import com.jofin.multivideo.domain.repository.ProjectRepository;
import com.jofin.multivideo.domain.usecase.CreateProjectFromMediaUseCase;
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
public final class PickerViewModel_Factory implements Factory<PickerViewModel> {
  private final Provider<MediaMetadataReader> metadataReaderProvider;

  private final Provider<UriPermissionManager> permissionManagerProvider;

  private final Provider<CreateProjectFromMediaUseCase> createProjectFromMediaUseCaseProvider;

  private final Provider<ProjectRepository> projectRepositoryProvider;

  public PickerViewModel_Factory(Provider<MediaMetadataReader> metadataReaderProvider,
      Provider<UriPermissionManager> permissionManagerProvider,
      Provider<CreateProjectFromMediaUseCase> createProjectFromMediaUseCaseProvider,
      Provider<ProjectRepository> projectRepositoryProvider) {
    this.metadataReaderProvider = metadataReaderProvider;
    this.permissionManagerProvider = permissionManagerProvider;
    this.createProjectFromMediaUseCaseProvider = createProjectFromMediaUseCaseProvider;
    this.projectRepositoryProvider = projectRepositoryProvider;
  }

  @Override
  public PickerViewModel get() {
    return newInstance(metadataReaderProvider.get(), permissionManagerProvider.get(), createProjectFromMediaUseCaseProvider.get(), projectRepositoryProvider.get());
  }

  public static PickerViewModel_Factory create(Provider<MediaMetadataReader> metadataReaderProvider,
      Provider<UriPermissionManager> permissionManagerProvider,
      Provider<CreateProjectFromMediaUseCase> createProjectFromMediaUseCaseProvider,
      Provider<ProjectRepository> projectRepositoryProvider) {
    return new PickerViewModel_Factory(metadataReaderProvider, permissionManagerProvider, createProjectFromMediaUseCaseProvider, projectRepositoryProvider);
  }

  public static PickerViewModel newInstance(MediaMetadataReader metadataReader,
      UriPermissionManager permissionManager,
      CreateProjectFromMediaUseCase createProjectFromMediaUseCase,
      ProjectRepository projectRepository) {
    return new PickerViewModel(metadataReader, permissionManager, createProjectFromMediaUseCase, projectRepository);
  }
}
