package com.intent.screens.appearance.ui

import com.intent.core.domain.model.AppTheme

sealed interface AppearanceScreenEvent {

    data class OnClickMaterialColors(
        val isChecked: Boolean
    ) : AppearanceScreenEvent

    data class OnSelectAppTheme(
        val appTheme: AppTheme
    ) : AppearanceScreenEvent
}
