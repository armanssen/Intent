package com.crux.screens.drawer

sealed interface DrawerNavigationEvent {

    data object NavigateToTaskLists : DrawerNavigationEvent

    data object NavigateToCompletedTasks : DrawerNavigationEvent

    data object NavigateToAppearance : DrawerNavigationEvent

    data object NavigateToSettings : DrawerNavigationEvent

    data object NavigateToAbout : DrawerNavigationEvent
}
