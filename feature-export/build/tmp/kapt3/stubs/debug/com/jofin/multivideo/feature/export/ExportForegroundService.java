package com.jofin.multivideo.feature.export;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\u0014\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\"\u0010\u0019\u001a\u00020\u001a2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001aH\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u001d"}, d2 = {"Lcom/jofin/multivideo/feature/export/ExportForegroundService;", "Landroid/app/Service;", "()V", "exportCoordinator", "Lcom/jofin/multivideo/feature/export/ExportCoordinator;", "getExportCoordinator", "()Lcom/jofin/multivideo/feature/export/ExportCoordinator;", "setExportCoordinator", "(Lcom/jofin/multivideo/feature/export/ExportCoordinator;)V", "projectRepository", "Lcom/jofin/multivideo/domain/repository/ProjectRepository;", "getProjectRepository", "()Lcom/jofin/multivideo/domain/repository/ProjectRepository;", "setProjectRepository", "(Lcom/jofin/multivideo/domain/repository/ProjectRepository;)V", "buildNotification", "Landroid/app/Notification;", "text", "", "createChannel", "", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onStartCommand", "", "flags", "startId", "feature-export_debug"})
public final class ExportForegroundService extends android.app.Service {
    @javax.inject.Inject()
    public com.jofin.multivideo.domain.repository.ProjectRepository projectRepository;
    @javax.inject.Inject()
    public com.jofin.multivideo.feature.export.ExportCoordinator exportCoordinator;
    
    public ExportForegroundService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.jofin.multivideo.domain.repository.ProjectRepository getProjectRepository() {
        return null;
    }
    
    public final void setProjectRepository(@org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.domain.repository.ProjectRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.jofin.multivideo.feature.export.ExportCoordinator getExportCoordinator() {
        return null;
    }
    
    public final void setExportCoordinator(@org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.feature.export.ExportCoordinator p0) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    private final void createChannel() {
    }
    
    private final android.app.Notification buildNotification(java.lang.String text) {
        return null;
    }
}