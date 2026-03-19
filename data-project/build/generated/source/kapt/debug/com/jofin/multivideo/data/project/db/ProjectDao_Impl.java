package com.jofin.multivideo.data.project.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.jofin.multivideo.data.project.model.ClipTrackEntity;
import com.jofin.multivideo.data.project.model.ExportStatusEntity;
import com.jofin.multivideo.data.project.model.ProjectEntity;
import com.jofin.multivideo.domain.model.AudioMode;
import com.jofin.multivideo.domain.model.LayoutType;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProjectDao_Impl implements ProjectDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProjectEntity> __insertionAdapterOfProjectEntity;

  private final ProjectTypeConverters __projectTypeConverters = new ProjectTypeConverters();

  private final EntityInsertionAdapter<ClipTrackEntity> __insertionAdapterOfClipTrackEntity;

  private final EntityInsertionAdapter<ExportStatusEntity> __insertionAdapterOfExportStatusEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteClipsForProject;

  public ProjectDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProjectEntity = new EntityInsertionAdapter<ProjectEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `projects` (`id`,`name`,`createdAt`,`updatedAt`,`outputAspectRatio`,`exportPresetLabel`,`exportWidth`,`exportHeight`,`exportBitrate`,`exportFrameRate`,`exportAudioBitrate`,`layoutType`,`audioMode`,`backgroundColor`,`selectedAudioClipId`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProjectEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        statement.bindLong(3, entity.getCreatedAt());
        statement.bindLong(4, entity.getUpdatedAt());
        if (entity.getOutputAspectRatio() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getOutputAspectRatio());
        }
        if (entity.getExportPresetLabel() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getExportPresetLabel());
        }
        statement.bindLong(7, entity.getExportWidth());
        statement.bindLong(8, entity.getExportHeight());
        statement.bindLong(9, entity.getExportBitrate());
        statement.bindLong(10, entity.getExportFrameRate());
        statement.bindLong(11, entity.getExportAudioBitrate());
        final String _tmp = __projectTypeConverters.fromLayoutType(entity.getLayoutType());
        if (_tmp == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, _tmp);
        }
        final String _tmp_1 = __projectTypeConverters.fromAudioMode(entity.getAudioMode());
        if (_tmp_1 == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, _tmp_1);
        }
        statement.bindLong(14, entity.getBackgroundColor());
        if (entity.getSelectedAudioClipId() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getSelectedAudioClipId());
        }
      }
    };
    this.__insertionAdapterOfClipTrackEntity = new EntityInsertionAdapter<ClipTrackEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `clips` (`id`,`projectId`,`uri`,`displayName`,`durationMs`,`width`,`height`,`rotationDegrees`,`trimStartMs`,`trimEndMs`,`startOffsetMs`,`volume`,`muted`,`slotIndex`,`zIndex`,`mimeType`,`hasAudio`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ClipTrackEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getProjectId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getProjectId());
        }
        if (entity.getUri() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getUri());
        }
        if (entity.getDisplayName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDisplayName());
        }
        statement.bindLong(5, entity.getDurationMs());
        statement.bindLong(6, entity.getWidth());
        statement.bindLong(7, entity.getHeight());
        statement.bindLong(8, entity.getRotationDegrees());
        statement.bindLong(9, entity.getTrimStartMs());
        statement.bindLong(10, entity.getTrimEndMs());
        statement.bindLong(11, entity.getStartOffsetMs());
        statement.bindDouble(12, entity.getVolume());
        final int _tmp = entity.getMuted() ? 1 : 0;
        statement.bindLong(13, _tmp);
        statement.bindLong(14, entity.getSlotIndex());
        statement.bindLong(15, entity.getZIndex());
        if (entity.getMimeType() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getMimeType());
        }
        final int _tmp_1 = entity.getHasAudio() ? 1 : 0;
        statement.bindLong(17, _tmp_1);
      }
    };
    this.__insertionAdapterOfExportStatusEntity = new EntityInsertionAdapter<ExportStatusEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `export_status` (`projectId`,`state`,`progress`,`outputUri`,`message`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ExportStatusEntity entity) {
        if (entity.getProjectId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getProjectId());
        }
        if (entity.getState() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getState());
        }
        statement.bindLong(3, entity.getProgress());
        if (entity.getOutputUri() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getOutputUri());
        }
        if (entity.getMessage() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getMessage());
        }
      }
    };
    this.__preparedStmtOfDeleteClipsForProject = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM clips WHERE projectId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object upsertProject(final ProjectEntity project,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProjectEntity.insert(project);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertClips(final List<ClipTrackEntity> clips,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfClipTrackEntity.insert(clips);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertExportStatus(final ExportStatusEntity status,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfExportStatusEntity.insert(status);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object replaceProject(final ProjectEntity project, final List<ClipTrackEntity> clips,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> ProjectDao.DefaultImpls.replaceProject(ProjectDao_Impl.this, project, clips, __cont), $completion);
  }

  @Override
  public Object deleteClipsForProject(final String projectId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteClipsForProject.acquire();
        int _argIndex = 1;
        if (projectId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, projectId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteClipsForProject.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ProjectEntity>> observeProjects() {
    final String _sql = "SELECT * FROM projects ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"projects"}, new Callable<List<ProjectEntity>>() {
      @Override
      @NonNull
      public List<ProjectEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfOutputAspectRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "outputAspectRatio");
          final int _cursorIndexOfExportPresetLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "exportPresetLabel");
          final int _cursorIndexOfExportWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "exportWidth");
          final int _cursorIndexOfExportHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "exportHeight");
          final int _cursorIndexOfExportBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "exportBitrate");
          final int _cursorIndexOfExportFrameRate = CursorUtil.getColumnIndexOrThrow(_cursor, "exportFrameRate");
          final int _cursorIndexOfExportAudioBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "exportAudioBitrate");
          final int _cursorIndexOfLayoutType = CursorUtil.getColumnIndexOrThrow(_cursor, "layoutType");
          final int _cursorIndexOfAudioMode = CursorUtil.getColumnIndexOrThrow(_cursor, "audioMode");
          final int _cursorIndexOfBackgroundColor = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundColor");
          final int _cursorIndexOfSelectedAudioClipId = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedAudioClipId");
          final List<ProjectEntity> _result = new ArrayList<ProjectEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProjectEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpOutputAspectRatio;
            if (_cursor.isNull(_cursorIndexOfOutputAspectRatio)) {
              _tmpOutputAspectRatio = null;
            } else {
              _tmpOutputAspectRatio = _cursor.getString(_cursorIndexOfOutputAspectRatio);
            }
            final String _tmpExportPresetLabel;
            if (_cursor.isNull(_cursorIndexOfExportPresetLabel)) {
              _tmpExportPresetLabel = null;
            } else {
              _tmpExportPresetLabel = _cursor.getString(_cursorIndexOfExportPresetLabel);
            }
            final int _tmpExportWidth;
            _tmpExportWidth = _cursor.getInt(_cursorIndexOfExportWidth);
            final int _tmpExportHeight;
            _tmpExportHeight = _cursor.getInt(_cursorIndexOfExportHeight);
            final int _tmpExportBitrate;
            _tmpExportBitrate = _cursor.getInt(_cursorIndexOfExportBitrate);
            final int _tmpExportFrameRate;
            _tmpExportFrameRate = _cursor.getInt(_cursorIndexOfExportFrameRate);
            final int _tmpExportAudioBitrate;
            _tmpExportAudioBitrate = _cursor.getInt(_cursorIndexOfExportAudioBitrate);
            final LayoutType _tmpLayoutType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfLayoutType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfLayoutType);
            }
            _tmpLayoutType = __projectTypeConverters.toLayoutType(_tmp);
            final AudioMode _tmpAudioMode;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfAudioMode)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfAudioMode);
            }
            _tmpAudioMode = __projectTypeConverters.toAudioMode(_tmp_1);
            final long _tmpBackgroundColor;
            _tmpBackgroundColor = _cursor.getLong(_cursorIndexOfBackgroundColor);
            final String _tmpSelectedAudioClipId;
            if (_cursor.isNull(_cursorIndexOfSelectedAudioClipId)) {
              _tmpSelectedAudioClipId = null;
            } else {
              _tmpSelectedAudioClipId = _cursor.getString(_cursorIndexOfSelectedAudioClipId);
            }
            _item = new ProjectEntity(_tmpId,_tmpName,_tmpCreatedAt,_tmpUpdatedAt,_tmpOutputAspectRatio,_tmpExportPresetLabel,_tmpExportWidth,_tmpExportHeight,_tmpExportBitrate,_tmpExportFrameRate,_tmpExportAudioBitrate,_tmpLayoutType,_tmpAudioMode,_tmpBackgroundColor,_tmpSelectedAudioClipId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<ProjectEntity> observeProjectEntity(final String projectId) {
    final String _sql = "SELECT * FROM projects WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (projectId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, projectId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"projects"}, new Callable<ProjectEntity>() {
      @Override
      @Nullable
      public ProjectEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfOutputAspectRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "outputAspectRatio");
          final int _cursorIndexOfExportPresetLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "exportPresetLabel");
          final int _cursorIndexOfExportWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "exportWidth");
          final int _cursorIndexOfExportHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "exportHeight");
          final int _cursorIndexOfExportBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "exportBitrate");
          final int _cursorIndexOfExportFrameRate = CursorUtil.getColumnIndexOrThrow(_cursor, "exportFrameRate");
          final int _cursorIndexOfExportAudioBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "exportAudioBitrate");
          final int _cursorIndexOfLayoutType = CursorUtil.getColumnIndexOrThrow(_cursor, "layoutType");
          final int _cursorIndexOfAudioMode = CursorUtil.getColumnIndexOrThrow(_cursor, "audioMode");
          final int _cursorIndexOfBackgroundColor = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundColor");
          final int _cursorIndexOfSelectedAudioClipId = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedAudioClipId");
          final ProjectEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpOutputAspectRatio;
            if (_cursor.isNull(_cursorIndexOfOutputAspectRatio)) {
              _tmpOutputAspectRatio = null;
            } else {
              _tmpOutputAspectRatio = _cursor.getString(_cursorIndexOfOutputAspectRatio);
            }
            final String _tmpExportPresetLabel;
            if (_cursor.isNull(_cursorIndexOfExportPresetLabel)) {
              _tmpExportPresetLabel = null;
            } else {
              _tmpExportPresetLabel = _cursor.getString(_cursorIndexOfExportPresetLabel);
            }
            final int _tmpExportWidth;
            _tmpExportWidth = _cursor.getInt(_cursorIndexOfExportWidth);
            final int _tmpExportHeight;
            _tmpExportHeight = _cursor.getInt(_cursorIndexOfExportHeight);
            final int _tmpExportBitrate;
            _tmpExportBitrate = _cursor.getInt(_cursorIndexOfExportBitrate);
            final int _tmpExportFrameRate;
            _tmpExportFrameRate = _cursor.getInt(_cursorIndexOfExportFrameRate);
            final int _tmpExportAudioBitrate;
            _tmpExportAudioBitrate = _cursor.getInt(_cursorIndexOfExportAudioBitrate);
            final LayoutType _tmpLayoutType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfLayoutType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfLayoutType);
            }
            _tmpLayoutType = __projectTypeConverters.toLayoutType(_tmp);
            final AudioMode _tmpAudioMode;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfAudioMode)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfAudioMode);
            }
            _tmpAudioMode = __projectTypeConverters.toAudioMode(_tmp_1);
            final long _tmpBackgroundColor;
            _tmpBackgroundColor = _cursor.getLong(_cursorIndexOfBackgroundColor);
            final String _tmpSelectedAudioClipId;
            if (_cursor.isNull(_cursorIndexOfSelectedAudioClipId)) {
              _tmpSelectedAudioClipId = null;
            } else {
              _tmpSelectedAudioClipId = _cursor.getString(_cursorIndexOfSelectedAudioClipId);
            }
            _result = new ProjectEntity(_tmpId,_tmpName,_tmpCreatedAt,_tmpUpdatedAt,_tmpOutputAspectRatio,_tmpExportPresetLabel,_tmpExportWidth,_tmpExportHeight,_tmpExportBitrate,_tmpExportFrameRate,_tmpExportAudioBitrate,_tmpLayoutType,_tmpAudioMode,_tmpBackgroundColor,_tmpSelectedAudioClipId);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ClipTrackEntity>> observeClips(final String projectId) {
    final String _sql = "SELECT * FROM clips WHERE projectId = ? ORDER BY slotIndex ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (projectId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, projectId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"clips"}, new Callable<List<ClipTrackEntity>>() {
      @Override
      @NonNull
      public List<ClipTrackEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "projectId");
          final int _cursorIndexOfUri = CursorUtil.getColumnIndexOrThrow(_cursor, "uri");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "width");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfRotationDegrees = CursorUtil.getColumnIndexOrThrow(_cursor, "rotationDegrees");
          final int _cursorIndexOfTrimStartMs = CursorUtil.getColumnIndexOrThrow(_cursor, "trimStartMs");
          final int _cursorIndexOfTrimEndMs = CursorUtil.getColumnIndexOrThrow(_cursor, "trimEndMs");
          final int _cursorIndexOfStartOffsetMs = CursorUtil.getColumnIndexOrThrow(_cursor, "startOffsetMs");
          final int _cursorIndexOfVolume = CursorUtil.getColumnIndexOrThrow(_cursor, "volume");
          final int _cursorIndexOfMuted = CursorUtil.getColumnIndexOrThrow(_cursor, "muted");
          final int _cursorIndexOfSlotIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "slotIndex");
          final int _cursorIndexOfZIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "zIndex");
          final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
          final int _cursorIndexOfHasAudio = CursorUtil.getColumnIndexOrThrow(_cursor, "hasAudio");
          final List<ClipTrackEntity> _result = new ArrayList<ClipTrackEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ClipTrackEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpProjectId;
            if (_cursor.isNull(_cursorIndexOfProjectId)) {
              _tmpProjectId = null;
            } else {
              _tmpProjectId = _cursor.getString(_cursorIndexOfProjectId);
            }
            final String _tmpUri;
            if (_cursor.isNull(_cursorIndexOfUri)) {
              _tmpUri = null;
            } else {
              _tmpUri = _cursor.getString(_cursorIndexOfUri);
            }
            final String _tmpDisplayName;
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _tmpDisplayName = null;
            } else {
              _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final int _tmpWidth;
            _tmpWidth = _cursor.getInt(_cursorIndexOfWidth);
            final int _tmpHeight;
            _tmpHeight = _cursor.getInt(_cursorIndexOfHeight);
            final int _tmpRotationDegrees;
            _tmpRotationDegrees = _cursor.getInt(_cursorIndexOfRotationDegrees);
            final long _tmpTrimStartMs;
            _tmpTrimStartMs = _cursor.getLong(_cursorIndexOfTrimStartMs);
            final long _tmpTrimEndMs;
            _tmpTrimEndMs = _cursor.getLong(_cursorIndexOfTrimEndMs);
            final long _tmpStartOffsetMs;
            _tmpStartOffsetMs = _cursor.getLong(_cursorIndexOfStartOffsetMs);
            final float _tmpVolume;
            _tmpVolume = _cursor.getFloat(_cursorIndexOfVolume);
            final boolean _tmpMuted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMuted);
            _tmpMuted = _tmp != 0;
            final int _tmpSlotIndex;
            _tmpSlotIndex = _cursor.getInt(_cursorIndexOfSlotIndex);
            final int _tmpZIndex;
            _tmpZIndex = _cursor.getInt(_cursorIndexOfZIndex);
            final String _tmpMimeType;
            if (_cursor.isNull(_cursorIndexOfMimeType)) {
              _tmpMimeType = null;
            } else {
              _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
            }
            final boolean _tmpHasAudio;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfHasAudio);
            _tmpHasAudio = _tmp_1 != 0;
            _item = new ClipTrackEntity(_tmpId,_tmpProjectId,_tmpUri,_tmpDisplayName,_tmpDurationMs,_tmpWidth,_tmpHeight,_tmpRotationDegrees,_tmpTrimStartMs,_tmpTrimEndMs,_tmpStartOffsetMs,_tmpVolume,_tmpMuted,_tmpSlotIndex,_tmpZIndex,_tmpMimeType,_tmpHasAudio);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<ExportStatusEntity> observeExportStatus(final String projectId) {
    final String _sql = "SELECT * FROM export_status WHERE projectId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (projectId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, projectId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"export_status"}, new Callable<ExportStatusEntity>() {
      @Override
      @Nullable
      public ExportStatusEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "projectId");
          final int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, "state");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfOutputUri = CursorUtil.getColumnIndexOrThrow(_cursor, "outputUri");
          final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
          final ExportStatusEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpProjectId;
            if (_cursor.isNull(_cursorIndexOfProjectId)) {
              _tmpProjectId = null;
            } else {
              _tmpProjectId = _cursor.getString(_cursorIndexOfProjectId);
            }
            final String _tmpState;
            if (_cursor.isNull(_cursorIndexOfState)) {
              _tmpState = null;
            } else {
              _tmpState = _cursor.getString(_cursorIndexOfState);
            }
            final int _tmpProgress;
            _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
            final String _tmpOutputUri;
            if (_cursor.isNull(_cursorIndexOfOutputUri)) {
              _tmpOutputUri = null;
            } else {
              _tmpOutputUri = _cursor.getString(_cursorIndexOfOutputUri);
            }
            final String _tmpMessage;
            if (_cursor.isNull(_cursorIndexOfMessage)) {
              _tmpMessage = null;
            } else {
              _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            }
            _result = new ExportStatusEntity(_tmpProjectId,_tmpState,_tmpProgress,_tmpOutputUri,_tmpMessage);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getProjectEntity(final String projectId,
      final Continuation<? super ProjectEntity> $completion) {
    final String _sql = "SELECT * FROM projects WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (projectId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, projectId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ProjectEntity>() {
      @Override
      @Nullable
      public ProjectEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfOutputAspectRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "outputAspectRatio");
          final int _cursorIndexOfExportPresetLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "exportPresetLabel");
          final int _cursorIndexOfExportWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "exportWidth");
          final int _cursorIndexOfExportHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "exportHeight");
          final int _cursorIndexOfExportBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "exportBitrate");
          final int _cursorIndexOfExportFrameRate = CursorUtil.getColumnIndexOrThrow(_cursor, "exportFrameRate");
          final int _cursorIndexOfExportAudioBitrate = CursorUtil.getColumnIndexOrThrow(_cursor, "exportAudioBitrate");
          final int _cursorIndexOfLayoutType = CursorUtil.getColumnIndexOrThrow(_cursor, "layoutType");
          final int _cursorIndexOfAudioMode = CursorUtil.getColumnIndexOrThrow(_cursor, "audioMode");
          final int _cursorIndexOfBackgroundColor = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundColor");
          final int _cursorIndexOfSelectedAudioClipId = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedAudioClipId");
          final ProjectEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpOutputAspectRatio;
            if (_cursor.isNull(_cursorIndexOfOutputAspectRatio)) {
              _tmpOutputAspectRatio = null;
            } else {
              _tmpOutputAspectRatio = _cursor.getString(_cursorIndexOfOutputAspectRatio);
            }
            final String _tmpExportPresetLabel;
            if (_cursor.isNull(_cursorIndexOfExportPresetLabel)) {
              _tmpExportPresetLabel = null;
            } else {
              _tmpExportPresetLabel = _cursor.getString(_cursorIndexOfExportPresetLabel);
            }
            final int _tmpExportWidth;
            _tmpExportWidth = _cursor.getInt(_cursorIndexOfExportWidth);
            final int _tmpExportHeight;
            _tmpExportHeight = _cursor.getInt(_cursorIndexOfExportHeight);
            final int _tmpExportBitrate;
            _tmpExportBitrate = _cursor.getInt(_cursorIndexOfExportBitrate);
            final int _tmpExportFrameRate;
            _tmpExportFrameRate = _cursor.getInt(_cursorIndexOfExportFrameRate);
            final int _tmpExportAudioBitrate;
            _tmpExportAudioBitrate = _cursor.getInt(_cursorIndexOfExportAudioBitrate);
            final LayoutType _tmpLayoutType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfLayoutType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfLayoutType);
            }
            _tmpLayoutType = __projectTypeConverters.toLayoutType(_tmp);
            final AudioMode _tmpAudioMode;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfAudioMode)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfAudioMode);
            }
            _tmpAudioMode = __projectTypeConverters.toAudioMode(_tmp_1);
            final long _tmpBackgroundColor;
            _tmpBackgroundColor = _cursor.getLong(_cursorIndexOfBackgroundColor);
            final String _tmpSelectedAudioClipId;
            if (_cursor.isNull(_cursorIndexOfSelectedAudioClipId)) {
              _tmpSelectedAudioClipId = null;
            } else {
              _tmpSelectedAudioClipId = _cursor.getString(_cursorIndexOfSelectedAudioClipId);
            }
            _result = new ProjectEntity(_tmpId,_tmpName,_tmpCreatedAt,_tmpUpdatedAt,_tmpOutputAspectRatio,_tmpExportPresetLabel,_tmpExportWidth,_tmpExportHeight,_tmpExportBitrate,_tmpExportFrameRate,_tmpExportAudioBitrate,_tmpLayoutType,_tmpAudioMode,_tmpBackgroundColor,_tmpSelectedAudioClipId);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getClips(final String projectId,
      final Continuation<? super List<ClipTrackEntity>> $completion) {
    final String _sql = "SELECT * FROM clips WHERE projectId = ? ORDER BY slotIndex ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (projectId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, projectId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ClipTrackEntity>>() {
      @Override
      @NonNull
      public List<ClipTrackEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "projectId");
          final int _cursorIndexOfUri = CursorUtil.getColumnIndexOrThrow(_cursor, "uri");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "width");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfRotationDegrees = CursorUtil.getColumnIndexOrThrow(_cursor, "rotationDegrees");
          final int _cursorIndexOfTrimStartMs = CursorUtil.getColumnIndexOrThrow(_cursor, "trimStartMs");
          final int _cursorIndexOfTrimEndMs = CursorUtil.getColumnIndexOrThrow(_cursor, "trimEndMs");
          final int _cursorIndexOfStartOffsetMs = CursorUtil.getColumnIndexOrThrow(_cursor, "startOffsetMs");
          final int _cursorIndexOfVolume = CursorUtil.getColumnIndexOrThrow(_cursor, "volume");
          final int _cursorIndexOfMuted = CursorUtil.getColumnIndexOrThrow(_cursor, "muted");
          final int _cursorIndexOfSlotIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "slotIndex");
          final int _cursorIndexOfZIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "zIndex");
          final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
          final int _cursorIndexOfHasAudio = CursorUtil.getColumnIndexOrThrow(_cursor, "hasAudio");
          final List<ClipTrackEntity> _result = new ArrayList<ClipTrackEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ClipTrackEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpProjectId;
            if (_cursor.isNull(_cursorIndexOfProjectId)) {
              _tmpProjectId = null;
            } else {
              _tmpProjectId = _cursor.getString(_cursorIndexOfProjectId);
            }
            final String _tmpUri;
            if (_cursor.isNull(_cursorIndexOfUri)) {
              _tmpUri = null;
            } else {
              _tmpUri = _cursor.getString(_cursorIndexOfUri);
            }
            final String _tmpDisplayName;
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _tmpDisplayName = null;
            } else {
              _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final int _tmpWidth;
            _tmpWidth = _cursor.getInt(_cursorIndexOfWidth);
            final int _tmpHeight;
            _tmpHeight = _cursor.getInt(_cursorIndexOfHeight);
            final int _tmpRotationDegrees;
            _tmpRotationDegrees = _cursor.getInt(_cursorIndexOfRotationDegrees);
            final long _tmpTrimStartMs;
            _tmpTrimStartMs = _cursor.getLong(_cursorIndexOfTrimStartMs);
            final long _tmpTrimEndMs;
            _tmpTrimEndMs = _cursor.getLong(_cursorIndexOfTrimEndMs);
            final long _tmpStartOffsetMs;
            _tmpStartOffsetMs = _cursor.getLong(_cursorIndexOfStartOffsetMs);
            final float _tmpVolume;
            _tmpVolume = _cursor.getFloat(_cursorIndexOfVolume);
            final boolean _tmpMuted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMuted);
            _tmpMuted = _tmp != 0;
            final int _tmpSlotIndex;
            _tmpSlotIndex = _cursor.getInt(_cursorIndexOfSlotIndex);
            final int _tmpZIndex;
            _tmpZIndex = _cursor.getInt(_cursorIndexOfZIndex);
            final String _tmpMimeType;
            if (_cursor.isNull(_cursorIndexOfMimeType)) {
              _tmpMimeType = null;
            } else {
              _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
            }
            final boolean _tmpHasAudio;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfHasAudio);
            _tmpHasAudio = _tmp_1 != 0;
            _item = new ClipTrackEntity(_tmpId,_tmpProjectId,_tmpUri,_tmpDisplayName,_tmpDurationMs,_tmpWidth,_tmpHeight,_tmpRotationDegrees,_tmpTrimStartMs,_tmpTrimEndMs,_tmpStartOffsetMs,_tmpVolume,_tmpMuted,_tmpSlotIndex,_tmpZIndex,_tmpMimeType,_tmpHasAudio);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object countClips(final String projectId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM clips WHERE projectId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (projectId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, projectId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
