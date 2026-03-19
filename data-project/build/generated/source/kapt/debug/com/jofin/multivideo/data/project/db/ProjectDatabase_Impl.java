package com.jofin.multivideo.data.project.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProjectDatabase_Impl extends ProjectDatabase {
  private volatile ProjectDao _projectDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `projects` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `outputAspectRatio` TEXT NOT NULL, `exportPresetLabel` TEXT NOT NULL, `exportWidth` INTEGER NOT NULL, `exportHeight` INTEGER NOT NULL, `exportBitrate` INTEGER NOT NULL, `exportFrameRate` INTEGER NOT NULL, `exportAudioBitrate` INTEGER NOT NULL, `layoutType` TEXT NOT NULL, `audioMode` TEXT NOT NULL, `backgroundColor` INTEGER NOT NULL, `selectedAudioClipId` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `clips` (`id` TEXT NOT NULL, `projectId` TEXT NOT NULL, `uri` TEXT NOT NULL, `displayName` TEXT NOT NULL, `durationMs` INTEGER NOT NULL, `width` INTEGER NOT NULL, `height` INTEGER NOT NULL, `rotationDegrees` INTEGER NOT NULL, `trimStartMs` INTEGER NOT NULL, `trimEndMs` INTEGER NOT NULL, `startOffsetMs` INTEGER NOT NULL, `volume` REAL NOT NULL, `muted` INTEGER NOT NULL, `slotIndex` INTEGER NOT NULL, `zIndex` INTEGER NOT NULL, `mimeType` TEXT NOT NULL, `hasAudio` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`projectId`) REFERENCES `projects`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_clips_projectId` ON `clips` (`projectId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `export_status` (`projectId` TEXT NOT NULL, `state` TEXT NOT NULL, `progress` INTEGER NOT NULL, `outputUri` TEXT, `message` TEXT, PRIMARY KEY(`projectId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '8402bf653d1efc9cdb17a97db8206a8e')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `projects`");
        db.execSQL("DROP TABLE IF EXISTS `clips`");
        db.execSQL("DROP TABLE IF EXISTS `export_status`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsProjects = new HashMap<String, TableInfo.Column>(15);
        _columnsProjects.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("outputAspectRatio", new TableInfo.Column("outputAspectRatio", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("exportPresetLabel", new TableInfo.Column("exportPresetLabel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("exportWidth", new TableInfo.Column("exportWidth", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("exportHeight", new TableInfo.Column("exportHeight", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("exportBitrate", new TableInfo.Column("exportBitrate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("exportFrameRate", new TableInfo.Column("exportFrameRate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("exportAudioBitrate", new TableInfo.Column("exportAudioBitrate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("layoutType", new TableInfo.Column("layoutType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("audioMode", new TableInfo.Column("audioMode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("backgroundColor", new TableInfo.Column("backgroundColor", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("selectedAudioClipId", new TableInfo.Column("selectedAudioClipId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProjects = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProjects = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProjects = new TableInfo("projects", _columnsProjects, _foreignKeysProjects, _indicesProjects);
        final TableInfo _existingProjects = TableInfo.read(db, "projects");
        if (!_infoProjects.equals(_existingProjects)) {
          return new RoomOpenHelper.ValidationResult(false, "projects(com.jofin.multivideo.data.project.model.ProjectEntity).\n"
                  + " Expected:\n" + _infoProjects + "\n"
                  + " Found:\n" + _existingProjects);
        }
        final HashMap<String, TableInfo.Column> _columnsClips = new HashMap<String, TableInfo.Column>(17);
        _columnsClips.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClips.put("projectId", new TableInfo.Column("projectId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClips.put("uri", new TableInfo.Column("uri", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClips.put("displayName", new TableInfo.Column("displayName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClips.put("durationMs", new TableInfo.Column("durationMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClips.put("width", new TableInfo.Column("width", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClips.put("height", new TableInfo.Column("height", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClips.put("rotationDegrees", new TableInfo.Column("rotationDegrees", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClips.put("trimStartMs", new TableInfo.Column("trimStartMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClips.put("trimEndMs", new TableInfo.Column("trimEndMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClips.put("startOffsetMs", new TableInfo.Column("startOffsetMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClips.put("volume", new TableInfo.Column("volume", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClips.put("muted", new TableInfo.Column("muted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClips.put("slotIndex", new TableInfo.Column("slotIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClips.put("zIndex", new TableInfo.Column("zIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClips.put("mimeType", new TableInfo.Column("mimeType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClips.put("hasAudio", new TableInfo.Column("hasAudio", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysClips = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysClips.add(new TableInfo.ForeignKey("projects", "CASCADE", "NO ACTION", Arrays.asList("projectId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesClips = new HashSet<TableInfo.Index>(1);
        _indicesClips.add(new TableInfo.Index("index_clips_projectId", false, Arrays.asList("projectId"), Arrays.asList("ASC")));
        final TableInfo _infoClips = new TableInfo("clips", _columnsClips, _foreignKeysClips, _indicesClips);
        final TableInfo _existingClips = TableInfo.read(db, "clips");
        if (!_infoClips.equals(_existingClips)) {
          return new RoomOpenHelper.ValidationResult(false, "clips(com.jofin.multivideo.data.project.model.ClipTrackEntity).\n"
                  + " Expected:\n" + _infoClips + "\n"
                  + " Found:\n" + _existingClips);
        }
        final HashMap<String, TableInfo.Column> _columnsExportStatus = new HashMap<String, TableInfo.Column>(5);
        _columnsExportStatus.put("projectId", new TableInfo.Column("projectId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExportStatus.put("state", new TableInfo.Column("state", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExportStatus.put("progress", new TableInfo.Column("progress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExportStatus.put("outputUri", new TableInfo.Column("outputUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExportStatus.put("message", new TableInfo.Column("message", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExportStatus = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesExportStatus = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoExportStatus = new TableInfo("export_status", _columnsExportStatus, _foreignKeysExportStatus, _indicesExportStatus);
        final TableInfo _existingExportStatus = TableInfo.read(db, "export_status");
        if (!_infoExportStatus.equals(_existingExportStatus)) {
          return new RoomOpenHelper.ValidationResult(false, "export_status(com.jofin.multivideo.data.project.model.ExportStatusEntity).\n"
                  + " Expected:\n" + _infoExportStatus + "\n"
                  + " Found:\n" + _existingExportStatus);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "8402bf653d1efc9cdb17a97db8206a8e", "fefb316d76013feb795445cbae1d31e9");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "projects","clips","export_status");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `projects`");
      _db.execSQL("DELETE FROM `clips`");
      _db.execSQL("DELETE FROM `export_status`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ProjectDao.class, ProjectDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ProjectDao projectDao() {
    if (_projectDao != null) {
      return _projectDao;
    } else {
      synchronized(this) {
        if(_projectDao == null) {
          _projectDao = new ProjectDao_Impl(this);
        }
        return _projectDao;
      }
    }
  }
}
