package com.jofin.multivideo.feature.editor;

import androidx.lifecycle.SavedStateHandle;
import com.jofin.multivideo.core.playback.PreviewSessionManager;
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
public final class EditorViewModel_Factory implements Factory<EditorViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<ProjectRepository> projectRepositoryProvider;

  private final Provider<PreviewSessionManager> previewSessionManagerProvider;

  public EditorViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ProjectRepository> projectRepositoryProvider,
      Provider<PreviewSessionManager> previewSessionManagerProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.projectRepositoryProvider = projectRepositoryProvider;
    this.previewSessionManagerProvider = previewSessionManagerProvider;
  }

  @Override
  public EditorViewModel get() {
    return newInstance(savedStateHandleProvider.get(), projectRepositoryProvider.get(), previewSessionManagerProvider.get());
  }

  public static EditorViewModel_Factory create(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ProjectRepository> projectRepositoryProvider,
      Provider<PreviewSessionManager> previewSessionManagerProvider) {
    return new EditorViewModel_Factory(savedStateHandleProvider, projectRepositoryProvider, previewSessionManagerProvider);
  }

  public static EditorViewModel newInstance(SavedStateHandle savedStateHandle,
      ProjectRepository projectRepository, PreviewSessionManager previewSessionManager) {
    return new EditorViewModel(savedStateHandle, projectRepository, previewSessionManager);
  }
}
