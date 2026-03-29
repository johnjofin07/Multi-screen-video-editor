package com.jofin.multivideo.feature.picker

import android.net.Uri
import android.text.format.DateUtils
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jofin.multivideo.domain.model.ProjectSummary

private val HomeBg = Color(0xFF0E0E0E)
private val CardBg = Color(0xFF1A1A1A)
private val CardBorder = Color(0xFF2A2A2A)
private val MutedText = Color(0xFF777777)
private val SubtleText = Color(0xFF999999)
private val BottomBarBg = Color(0xFF141414)

@Composable
fun HomeRoute(
    onNewProject: (String) -> Unit,
    onOpenProject: (String) -> Unit,
    viewModel: PickerViewModel = hiltViewModel(),
) {
    val recentProjects by viewModel.recentProjects.collectAsStateWithLifecycle()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(maxItems = 4),
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) viewModel.onPickerResult(uris)
    }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }
    LaunchedEffect(state.createdProjectId) {
        state.createdProjectId?.let {
            onNewProject(it)
            viewModel.consumeCreatedProject()
        }
    }

    HomeScreen(
        recentProjects = recentProjects,
        snackbarHostState = snackbarHostState,
        onNewProject = {
            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
        },
        onOpenProject = onOpenProject,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    recentProjects: List<ProjectSummary>,
    snackbarHostState: SnackbarHostState,
    onNewProject: () -> Unit,
    onOpenProject: (String) -> Unit,
) {
    Scaffold(
        containerColor = HomeBg,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                    }
                },
                title = {
                    Text(
                        "NOIR",
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        color = Color.White,
                        letterSpacing = 2.sp,
                    )
                },
                actions = {
                    TextButton(onClick = { }) {
                        Text(
                            "EXPORT",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            letterSpacing = 1.sp,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = HomeBg),
            )
        },
        bottomBar = {
            NavigationBar(containerColor = BottomBarBg, tonalElevation = 0.dp) {
                BottomNavItem(
                    icon = Icons.Default.Movie,
                    label = "PROJECT",
                    selected = true,
                    onClick = { },
                )
                BottomNavItem(
                    icon = Icons.Outlined.GridView,
                    label = "GALLERY",
                    selected = false,
                    onClick = { },
                )
                BottomNavItem(
                    icon = Icons.Default.Settings,
                    label = "SETTINGS",
                    selected = false,
                    onClick = { },
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNewProject,
                shape = CircleShape,
                containerColor = Color.White,
                contentColor = Color.Black,
            ) {
                Icon(Icons.Default.Add, contentDescription = "New project")
            }
        },
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 16.dp,
            ),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            // New Project card - spans full width
            item(span = { GridItemSpan(2) }) {
                NewProjectCard(onClick = onNewProject)
            }

            // Recent Projects header
            item(span = { GridItemSpan(2) }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        "Recent Projects",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    if (recentProjects.isNotEmpty()) {
                        Text(
                            "${recentProjects.size}  TOTAL",
                            color = MutedText,
                            fontSize = 12.sp,
                            letterSpacing = 1.sp,
                        )
                    }
                }
            }

            if (recentProjects.isEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    EmptyState()
                }
            } else {
                items(recentProjects, key = { it.id }) { project ->
                    ProjectCard(project = project, onClick = { onOpenProject(project.id) })
                }
            }
        }
    }
}

@Composable
private fun RowScope.BottomNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = label, modifier = Modifier.size(22.dp)) },
        label = {
            Text(
                label,
                fontSize = 10.sp,
                letterSpacing = 1.5.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            selectedTextColor = Color.White,
            unselectedIconColor = MutedText,
            unselectedTextColor = MutedText,
            indicatorColor = Color.Transparent,
        ),
    )
}

@Composable
private fun NewProjectCard(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardBg)
            .border(1.dp, CardBorder, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFF252525))
                .border(1.dp, CardBorder, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(22.dp),
            )
        }
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                "NEW PROJECT",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                "Start your next masterpiece",
                color = SubtleText,
                fontSize = 13.sp,
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = MutedText,
            modifier = Modifier.size(22.dp),
        )
    }
}

@Composable
private fun ProjectCard(project: ProjectSummary, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        // Thumbnail placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 11f)
                .clip(RoundedCornerShape(10.dp))
                .background(CardBg)
                .border(1.dp, CardBorder, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Default.Image,
                contentDescription = null,
                tint = Color(0xFF333333),
                modifier = Modifier.size(32.dp),
            )
            // Clip count badge (bottom-right)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp),
            ) {
                Text(
                    "${project.clipCount} clips",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(
            project.name,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            formatRelativeTime(project.updatedAt),
            color = MutedText,
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            Icons.Default.Movie,
            contentDescription = null,
            tint = Color(0xFF333333),
            modifier = Modifier.size(48.dp),
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "No projects yet",
            color = SubtleText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "Tap + to start your first edit",
            color = MutedText,
            fontSize = 13.sp,
        )
    }
}

private fun formatRelativeTime(timestampMs: Long): String {
    if (timestampMs == 0L) return ""
    return DateUtils.getRelativeTimeSpanString(
        timestampMs,
        System.currentTimeMillis(),
        DateUtils.MINUTE_IN_MILLIS,
        DateUtils.FORMAT_ABBREV_RELATIVE,
    ).toString()
}

