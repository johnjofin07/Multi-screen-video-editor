package com.jofin.multivideo.data.project;

import com.jofin.multivideo.data.project.db.ProjectDao;
import com.jofin.multivideo.data.project.db.ProjectDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class ProjectDataModule_ProvideProjectDaoFactory implements Factory<ProjectDao> {
  private final Provider<ProjectDatabase> databaseProvider;

  public ProjectDataModule_ProvideProjectDaoFactory(Provider<ProjectDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ProjectDao get() {
    return provideProjectDao(databaseProvider.get());
  }

  public static ProjectDataModule_ProvideProjectDaoFactory create(
      Provider<ProjectDatabase> databaseProvider) {
    return new ProjectDataModule_ProvideProjectDaoFactory(databaseProvider);
  }

  public static ProjectDao provideProjectDao(ProjectDatabase database) {
    return Preconditions.checkNotNullFromProvides(ProjectDataModule.INSTANCE.provideProjectDao(database));
  }
}
