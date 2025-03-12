package com.crux.ui.appearance.ui

import com.crux.domain.model.AppTheme

sealed interface AppearanceScreenEvent {

    data class OnClickMaterialColors(
        val isChecked: Boolean
    ) : AppearanceScreenEvent

    data class OnSelectAppTheme(
        val appTheme: AppTheme
    ) : AppearanceScreenEvent
}
