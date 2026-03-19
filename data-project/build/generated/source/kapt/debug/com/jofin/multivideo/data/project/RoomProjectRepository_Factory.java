package com.jofin.multivideo.data.project;

import com.jofin.multivideo.data.project.db.ProjectDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class RoomProjectRepository_Factory implements Factory<RoomProjectRepository> {
  private final Provider<ProjectDao> projectDaoProvider;

  public RoomProjectRepository_Factory(Provider<ProjectDao> projectDaoProvider) {
    this.projectDaoProvider = projectDaoProvider;
  }

  @Override
  public RoomProjectRepository get() {
    return newInstance(projectDaoProvider.get());
  }

  public static RoomProjectRepository_Factory create(Provider<ProjectDao> projectDaoProvider) {
    return new RoomProjectRepository_Factory(projectDaoProvider);
  }

  public static RoomProjectRepository newInstance(ProjectDao projectDao) {
    return new RoomProjectRepository(projectDao);
  }
}
