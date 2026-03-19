package com.jofin.multivideo.feature.export;

import com.jofin.multivideo.domain.repository.ProjectRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ExportForegroundService_MembersInjector implements MembersInjector<ExportForegroundService> {
  private final Provider<ProjectRepository> projectRepositoryProvider;

  private final Provider<ExportCoordinator> exportCoordinatorProvider;

  public ExportForegroundService_MembersInjector(
      Provider<ProjectRepository> projectRepositoryProvider,
      Provider<ExportCoordinator> exportCoordinatorProvider) {
    this.projectRepositoryProvider = projectRepositoryProvider;
    this.exportCoordinatorProvider = exportCoordinatorProvider;
  }

  public static MembersInjector<ExportForegroundService> create(
      Provider<ProjectRepository> projectRepositoryProvider,
      Provider<ExportCoordinator> exportCoordinatorProvider) {
    return new ExportForegroundService_MembersInjector(projectRepositoryProvider, exportCoordinatorProvider);
  }

  @Override
  public void injectMembers(ExportForegroundService instance) {
    injectProjectRepository(instance, projectRepositoryProvider.get());
    injectExportCoordinator(instance, exportCoordinatorProvider.get());
  }

  @InjectedFieldSignature("com.jofin.multivideo.feature.export.ExportForegroundService.projectRepository")
  public static void injectProjectRepository(ExportForegroundService instance,
      ProjectRepository projectRepository) {
    instance.projectRepository = projectRepository;
  }

  @InjectedFieldSignature("com.jofin.multivideo.feature.export.ExportForegroundService.exportCoordinator")
  public static void injectExportCoordinator(ExportForegroundService instance,
      ExportCoordinator exportCoordinator) {
    instance.exportCoordinator = exportCoordinator;
  }
}
