package com.crux.screens.appearance.ui

import com.crux.core.domain.model.AppTheme

sealed interface AppearanceScreenEvent {

    data class OnClickMaterialColors(
        val isChecked: Boolean
    ) : AppearanceScreenEvent

    data class OnSelectAppTheme(
        val appTheme: AppTheme
    ) : AppearanceScreenEvent
}
