package com.jofin.multivideo.data.project;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007\u00a8\u0006\n"}, d2 = {"Lcom/jofin/multivideo/data/project/ProjectDataModule;", "", "()V", "provideDatabase", "Lcom/jofin/multivideo/data/project/db/ProjectDatabase;", "context", "Landroid/content/Context;", "provideProjectDao", "Lcom/jofin/multivideo/data/project/db/ProjectDao;", "database", "data-project_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class ProjectDataModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.jofin.multivideo.data.project.ProjectDataModule INSTANCE = null;
    
    private ProjectDataModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.jofin.multivideo.data.project.db.ProjectDatabase provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.jofin.multivideo.data.project.db.ProjectDao provideProjectDao(@org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.data.project.db.ProjectDatabase database) {
        return null;
    }
}