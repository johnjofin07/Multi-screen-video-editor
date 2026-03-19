package com.jofin.multivideo

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jofin.multivideo.feature.editor.EditorRoute
import com.jofin.multivideo.feature.export.ExportCompleteRoute
import com.jofin.multivideo.feature.export.ExportProgressRoute
import com.jofin.multivideo.feature.export.ExportSheetRoute
import com.jofin.multivideo.feature.picker.HomeRoute
import com.jofin.multivideo.feature.picker.PickerRoute

@Composable
fun MultiVideoApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Home) {
        composable(Routes.Home) {
            HomeRoute(
                onNewProject = { navController.navigate(Routes.Picker) },
                onOpenProject = { projectId -> navController.navigate(Routes.editor(projectId)) },
            )
        }
        composable(Routes.Picker) {
            PickerRoute(
                onBack = { navController.popBackStack() },
                onProjectCreated = { projectId ->
                    navController.navigate(Routes.editor(projectId)) {
                        popUpTo(Routes.Home)
                    }
                },
            )
        }
        composable(
            route = Routes.Editor,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType }),
        ) {
            EditorRoute(
                onBack = { navController.popBackStack() },
                onExport = { projectId -> navController.navigate(Routes.exportSheet(projectId)) },
            )
        }
        composable(
            route = Routes.ExportSheet,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType }),
        ) {
            ExportSheetRoute(
                onBack = { navController.popBackStack() },
                onStartExport = { projectId ->
                    navController.navigate(Routes.exportProgress(projectId))
                },
            )
        }
        composable(
            route = Routes.ExportProgress,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType }),
        ) {
            ExportProgressRoute(
                onBack = { navController.popBackStack(Routes.Home, false) },
                onCompleted = { projectId -> navController.navigate(Routes.exportComplete(projectId)) },
            )
        }
        composable(
            route = Routes.ExportComplete,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType }),
        ) {
            ExportCompleteRoute(onDone = { navController.popBackStack(Routes.Home, false) })
        }
    }
}

private object Routes {
    const val Home = "home"
    const val Picker = "picker"
    const val Editor = "editor/{projectId}"
    const val ExportSheet = "export-sheet/{projectId}"
    const val ExportProgress = "export-progress/{projectId}"
    const val ExportComplete = "export-complete/{projectId}"

    fun editor(projectId: String) = "editor/$projectId"
    fun exportSheet(projectId: String) = "export-sheet/$projectId"
    fun exportProgress(projectId: String) = "export-progress/$projectId"
    fun exportComplete(projectId: String) = "export-complete/$projectId"
}
