/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("COMPOSABLE_FUNCTION_REFERENCE")
package org.jdc.template.ux.about.samples

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jdc.template.ux.about.samples.components.AlertDialogSample
import org.jdc.template.ux.about.samples.components.AlertDialogWithIconSample
import org.jdc.template.ux.about.samples.components.AnimatedExtendedFloatingActionButtonSample
import org.jdc.template.ux.about.samples.components.AssistChipSample
import org.jdc.template.ux.about.samples.components.BottomAppBarWithFAB
import org.jdc.template.ux.about.samples.components.ButtonSample
import org.jdc.template.ux.about.samples.components.ButtonWithIconSample
import org.jdc.template.ux.about.samples.components.CardSample
import org.jdc.template.ux.about.samples.components.CheckboxSample
import org.jdc.template.ux.about.samples.components.CheckboxWithTextSample
import org.jdc.template.ux.about.samples.components.ChipGroupSingleLineSample
import org.jdc.template.ux.about.samples.components.CircularProgressIndicatorSample
import org.jdc.template.ux.about.samples.components.ClickableCardSample
import org.jdc.template.ux.about.samples.components.ClickableElevatedCardSample
import org.jdc.template.ux.about.samples.components.ClickableOutlinedCardSample
import org.jdc.template.ux.about.samples.components.DismissibleNavigationDrawerSample
import org.jdc.template.ux.about.samples.components.EditableExposedDropdownMenuSample
import org.jdc.template.ux.about.samples.components.ElevatedAssistChipSample
import org.jdc.template.ux.about.samples.components.ElevatedButtonSample
import org.jdc.template.ux.about.samples.components.ElevatedCardSample
import org.jdc.template.ux.about.samples.components.ElevatedFilterChipSample
import org.jdc.template.ux.about.samples.components.ElevatedSuggestionChipSample
import org.jdc.template.ux.about.samples.components.EnterAlwaysTopAppBar
import org.jdc.template.ux.about.samples.components.ExitUntilCollapsedLargeTopAppBar
import org.jdc.template.ux.about.samples.components.ExitUntilCollapsedMediumTopAppBar
import org.jdc.template.ux.about.samples.components.ExposedDropdownMenuSample
import org.jdc.template.ux.about.samples.components.ExtendedFloatingActionButtonSample
import org.jdc.template.ux.about.samples.components.ExtendedFloatingActionButtonTextSample
import org.jdc.template.ux.about.samples.components.FancyIndicatorContainerTabs
import org.jdc.template.ux.about.samples.components.FancyIndicatorTabs
import org.jdc.template.ux.about.samples.components.FancyTabs
import org.jdc.template.ux.about.samples.components.FilledIconButtonSample
import org.jdc.template.ux.about.samples.components.FilledIconToggleButtonSample
import org.jdc.template.ux.about.samples.components.FilledTonalButtonSample
import org.jdc.template.ux.about.samples.components.FilledTonalIconButtonSample
import org.jdc.template.ux.about.samples.components.FilledTonalIconToggleButtonSample
import org.jdc.template.ux.about.samples.components.FilterChipSample
import org.jdc.template.ux.about.samples.components.FilterChipWithLeadingIconSample
import org.jdc.template.ux.about.samples.components.FloatingActionButtonSample
import org.jdc.template.ux.about.samples.components.IconButtonSample
import org.jdc.template.ux.about.samples.components.IconTabs
import org.jdc.template.ux.about.samples.components.IconToggleButtonSample
import org.jdc.template.ux.about.samples.components.IndeterminateCircularProgressIndicatorSample
import org.jdc.template.ux.about.samples.components.IndeterminateLinearProgressIndicatorSample
import org.jdc.template.ux.about.samples.components.InputChipSample
import org.jdc.template.ux.about.samples.components.InputChipWithAvatarSample
import org.jdc.template.ux.about.samples.components.LargeFloatingActionButtonSample
import org.jdc.template.ux.about.samples.components.LeadingIconTabs
import org.jdc.template.ux.about.samples.components.LinearProgressIndicatorSample
import org.jdc.template.ux.about.samples.components.MenuSample
import org.jdc.template.ux.about.samples.components.ModalNavigationDrawerSample
import org.jdc.template.ux.about.samples.components.NavigationBarItemWithBadge
import org.jdc.template.ux.about.samples.components.NavigationBarSample
import org.jdc.template.ux.about.samples.components.NavigationBarWithOnlySelectedLabelsSample
import org.jdc.template.ux.about.samples.components.NavigationRailBottomAlignSample
import org.jdc.template.ux.about.samples.components.NavigationRailSample
import org.jdc.template.ux.about.samples.components.NavigationRailWithOnlySelectedLabelsSample
import org.jdc.template.ux.about.samples.components.OneLineListItem
import org.jdc.template.ux.about.samples.components.OutlinedButtonSample
import org.jdc.template.ux.about.samples.components.OutlinedCardSample
import org.jdc.template.ux.about.samples.components.OutlinedIconButtonSample
import org.jdc.template.ux.about.samples.components.OutlinedIconToggleButtonSample
import org.jdc.template.ux.about.samples.components.OutlinedTextFieldSample
import org.jdc.template.ux.about.samples.components.PasswordTextField
import org.jdc.template.ux.about.samples.components.PermanentNavigationDrawerSample
import org.jdc.template.ux.about.samples.components.PinnedTopAppBar
import org.jdc.template.ux.about.samples.components.RadioButtonSample
import org.jdc.template.ux.about.samples.components.RadioGroupSample
import org.jdc.template.ux.about.samples.components.RangeSliderSample
import org.jdc.template.ux.about.samples.components.ScaffoldWithCoroutinesSnackbar
import org.jdc.template.ux.about.samples.components.ScaffoldWithCustomSnackbar
import org.jdc.template.ux.about.samples.components.ScaffoldWithIndefiniteSnackbar
import org.jdc.template.ux.about.samples.components.ScaffoldWithSimpleSnackbar
import org.jdc.template.ux.about.samples.components.ScrollingFancyIndicatorContainerTabs
import org.jdc.template.ux.about.samples.components.ScrollingTextTabs
import org.jdc.template.ux.about.samples.components.SimpleBottomAppBar
import org.jdc.template.ux.about.samples.components.SimpleCenterAlignedTopAppBar
import org.jdc.template.ux.about.samples.components.SimpleOutlinedTextFieldSample
import org.jdc.template.ux.about.samples.components.SimpleTextFieldSample
import org.jdc.template.ux.about.samples.components.SimpleTopAppBar
import org.jdc.template.ux.about.samples.components.SliderSample
import org.jdc.template.ux.about.samples.components.SliderWithCustomThumbSample
import org.jdc.template.ux.about.samples.components.SmallFloatingActionButtonSample
import org.jdc.template.ux.about.samples.components.StepRangeSliderSample
import org.jdc.template.ux.about.samples.components.StepsSliderSample
import org.jdc.template.ux.about.samples.components.SuggestionChipSample
import org.jdc.template.ux.about.samples.components.SwitchSample
import org.jdc.template.ux.about.samples.components.SwitchWithThumbIconSample
import org.jdc.template.ux.about.samples.components.TextAndIconTabs
import org.jdc.template.ux.about.samples.components.TextArea
import org.jdc.template.ux.about.samples.components.TextButtonSample
import org.jdc.template.ux.about.samples.components.TextFieldSample
import org.jdc.template.ux.about.samples.components.TextFieldWithErrorState
import org.jdc.template.ux.about.samples.components.TextFieldWithHideKeyboardOnImeAction
import org.jdc.template.ux.about.samples.components.TextFieldWithIcons
import org.jdc.template.ux.about.samples.components.TextFieldWithPlaceholder
import org.jdc.template.ux.about.samples.components.TextFieldWithSupportingText
import org.jdc.template.ux.about.samples.components.TextTabs
import org.jdc.template.ux.about.samples.components.ThreeLineListItem
import org.jdc.template.ux.about.samples.components.TriStateCheckboxSample
import org.jdc.template.ux.about.samples.components.TwoLineListItem

const val SampleSourceUrl = "https://cs.android.com/androidx/platform/frameworks/support/+/" +
        "androidx-main:compose/material3/" +
        "material3/samples/src/main/java/androidx/compose/material3/samples"

data class Example(
    val name: String,
    val description: String,
    val sourceUrl: String,
    val content: @Composable () -> Unit
)

private const val BadgeExampleDescription = "Badge examples"
private const val BadgeExampleSourceUrl = "$SampleSourceUrl/BadgeSamples.kt"
val BadgeExamples =
    listOf(
        Example(
            name = ::NavigationBarItemWithBadge.name,
            description = BadgeExampleDescription,
            sourceUrl = BadgeExampleSourceUrl
        ) { NavigationBarItemWithBadge() }
    )

private const val ButtonsExampleDescription = "Button examples"
private const val ButtonsExampleSourceUrl = "$SampleSourceUrl/ButtonSamples.kt"
val ButtonsExamples =
    listOf(
        Example(
            name = ::ButtonSample.name,
            description = ButtonsExampleDescription,
            sourceUrl = ButtonsExampleSourceUrl,
        ) { ButtonSample() },
        Example(
            name = ::ElevatedButtonSample.name,
            description = ButtonsExampleDescription,
            sourceUrl = ButtonsExampleSourceUrl,
        ) { ElevatedButtonSample() },
        Example(
            name = ::FilledTonalButtonSample.name,
            description = ButtonsExampleDescription,
            sourceUrl = ButtonsExampleSourceUrl,
        ) { FilledTonalButtonSample() },
        Example(
            name = ::OutlinedButtonSample.name,
            description = ButtonsExampleDescription,
            sourceUrl = ButtonsExampleSourceUrl,
        ) { OutlinedButtonSample() },
        Example(
            name = ::TextButtonSample.name,
            description = ButtonsExampleDescription,
            sourceUrl = ButtonsExampleSourceUrl,
        ) { TextButtonSample() },
        Example(
            name = ::ButtonWithIconSample.name,
            description = ButtonsExampleDescription,
            sourceUrl = ButtonsExampleSourceUrl,
        ) { ButtonWithIconSample() }
    )

private const val CardsExampleDescription = "Cards examples"
private const val CardsExampleSourceUrl = "$SampleSourceUrl/CardSamples.kt"
val CardExamples = listOf(
    Example(
        name = ::CardSample.name,
        description = CardsExampleDescription,
        sourceUrl = CardsExampleSourceUrl
    ) {
        CardSample()
    },
    Example(
        name = ::ClickableCardSample.name,
        description = CardsExampleDescription,
        sourceUrl = CardsExampleSourceUrl
    ) {
        ClickableCardSample()
    },
    Example(
        name = ::ElevatedCardSample.name,
        description = CardsExampleDescription,
        sourceUrl = CardsExampleSourceUrl
    ) {
        ElevatedCardSample()
    },
    Example(
        name = ::ClickableElevatedCardSample.name,
        description = CardsExampleDescription,
        sourceUrl = CardsExampleSourceUrl
    ) {
        ClickableElevatedCardSample()
    },
    Example(
        name = ::OutlinedCardSample.name,
        description = CardsExampleDescription,
        sourceUrl = CardsExampleSourceUrl
    ) {
        OutlinedCardSample()
    },
    Example(
        name = ::ClickableOutlinedCardSample.name,
        description = CardsExampleDescription,
        sourceUrl = CardsExampleSourceUrl
    ) {
        ClickableOutlinedCardSample()
    }
)

private const val CheckboxesExampleDescription = "Checkboxes examples"
private const val CheckboxesExampleSourceUrl = "$SampleSourceUrl/CheckboxSamples.kt"
val CheckboxesExamples = listOf(
    Example(
        name = ::CheckboxSample.name,
        description = CheckboxesExampleDescription,
        sourceUrl = CheckboxesExampleSourceUrl
    ) {
        CheckboxSample()
    },
    Example(
        name = ::CheckboxWithTextSample.name,
        description = CheckboxesExampleDescription,
        sourceUrl = CheckboxesExampleSourceUrl
    ) {
        CheckboxWithTextSample()
    },
    Example(
        name = ::TriStateCheckboxSample.name,
        description = CheckboxesExampleDescription,
        sourceUrl = CheckboxesExampleSourceUrl
    ) {
        TriStateCheckboxSample()
    }
)

private const val ChipsExampleDescription = "Chips examples"
private const val ChipsExampleSourceUrl = "$SampleSourceUrl/ChipSamples.kt"
val ChipsExamples = listOf(
    Example(
        name = ::AssistChipSample.name,
        description = ChipsExampleDescription,
        sourceUrl = ChipsExampleSourceUrl
    ) {
        AssistChipSample()
    },
    Example(
        name = ::ElevatedAssistChipSample.name,
        description = ChipsExampleDescription,
        sourceUrl = ChipsExampleSourceUrl
    ) {
        ElevatedAssistChipSample()
    },
    Example(
        name = ::FilterChipSample.name,
        description = ChipsExampleDescription,
        sourceUrl = ChipsExampleSourceUrl
    ) {
        FilterChipSample()
    },
    Example(
        name = ::ElevatedFilterChipSample.name,
        description = ChipsExampleDescription,
        sourceUrl = ChipsExampleSourceUrl
    ) {
        ElevatedFilterChipSample()
    },
    Example(
        name = ::FilterChipWithLeadingIconSample.name,
        description = ChipsExampleDescription,
        sourceUrl = ChipsExampleSourceUrl
    ) {
        FilterChipWithLeadingIconSample()
    },
    Example(
        name = ::InputChipSample.name,
        description = ChipsExampleDescription,
        sourceUrl = ChipsExampleSourceUrl
    ) {
        InputChipSample()
    },
    Example(
        name = ::InputChipWithAvatarSample.name,
        description = ChipsExampleDescription,
        sourceUrl = ChipsExampleSourceUrl
    ) {
        InputChipWithAvatarSample()
    },
    Example(
        name = ::SuggestionChipSample.name,
        description = ChipsExampleDescription,
        sourceUrl = ChipsExampleSourceUrl
    ) {
        SuggestionChipSample()
    },
    Example(
        name = ::ElevatedSuggestionChipSample.name,
        description = ChipsExampleDescription,
        sourceUrl = ChipsExampleSourceUrl
    ) {
        ElevatedSuggestionChipSample()
    },
    Example(
        name = ::ChipGroupSingleLineSample.name,
        description = ChipsExampleDescription,
        sourceUrl = ChipsExampleSourceUrl
    ) {
        ChipGroupSingleLineSample()
    }
)

private const val DialogExampleDescription = "Dialog examples"
private const val DialogExampleSourceUrl = "$SampleSourceUrl/AlertDialogSamples.kt"
val DialogExamples =
    listOf(
        Example(
            name = ::AlertDialogSample.name,
            description = DialogExampleDescription,
            sourceUrl = DialogExampleSourceUrl,
        ) { AlertDialogSample() },
        Example(
            name = ::AlertDialogWithIconSample.name,
            description = DialogExampleDescription,
            sourceUrl = DialogExampleSourceUrl,
        ) { AlertDialogWithIconSample() },
    )

private const val BottomAppBarsExampleDescription = "Bottom app bar examples"
private const val BottomAppBarsExampleSourceUrl = "$SampleSourceUrl/AppBarSamples.kt"
val BottomAppBarsExamples = listOf(
    Example(
        name = ::SimpleBottomAppBar.name,
        description = BottomAppBarsExampleDescription,
        sourceUrl = BottomAppBarsExampleSourceUrl,
    ) { SimpleBottomAppBar() },
    Example(
        name = ::BottomAppBarWithFAB.name,
        description = BottomAppBarsExampleDescription,
        sourceUrl = BottomAppBarsExampleSourceUrl,
    ) { BottomAppBarWithFAB() }
)

private const val TopAppBarExampleDescription = "Top app bar examples"
private const val TopAppBarExampleSourceUrl = "$SampleSourceUrl/AppBarSamples.kt"
val TopAppBarExamples =
    listOf(
        Example(
            name = ::SimpleTopAppBar.name,
            description = TopAppBarExampleDescription,
            sourceUrl = TopAppBarExampleSourceUrl,
        ) { SimpleTopAppBar() },
        Example(
            name = ::SimpleCenterAlignedTopAppBar.name,
            description = TopAppBarExampleDescription,
            sourceUrl = TopAppBarExampleSourceUrl,
        ) { SimpleCenterAlignedTopAppBar() },
        Example(
            name = ::PinnedTopAppBar.name,
            description = TopAppBarExampleDescription,
            sourceUrl = TopAppBarExampleSourceUrl,
        ) { PinnedTopAppBar() },
        Example(
            name = ::EnterAlwaysTopAppBar.name,
            description = TopAppBarExampleDescription,
            sourceUrl = TopAppBarExampleSourceUrl,
        ) { EnterAlwaysTopAppBar() },
        Example(
            name = ::ExitUntilCollapsedMediumTopAppBar.name,
            description = TopAppBarExampleDescription,
            sourceUrl = TopAppBarExampleSourceUrl,
        ) { ExitUntilCollapsedMediumTopAppBar() },
        Example(
            name = ::ExitUntilCollapsedLargeTopAppBar.name,
            description = TopAppBarExampleDescription,
            sourceUrl = TopAppBarExampleSourceUrl,
        ) { ExitUntilCollapsedLargeTopAppBar() },
    )

private const val ExtendedFABExampleDescription = "Extended FAB examples"
private const val ExtendedFABExampleSourceUrl = "$SampleSourceUrl/FloatingActionButtonSamples.kt"
val ExtendedFABExamples =
    listOf(
        Example(
            name = ::ExtendedFloatingActionButtonSample.name,
            description = ExtendedFABExampleDescription,
            sourceUrl = ExtendedFABExampleSourceUrl,
        ) { ExtendedFloatingActionButtonSample() },
        Example(
            name = ::ExtendedFloatingActionButtonTextSample.name,
            description = ExtendedFABExampleDescription,
            sourceUrl = ExtendedFABExampleSourceUrl,
        ) { ExtendedFloatingActionButtonTextSample() },
        Example(
            name = ::AnimatedExtendedFloatingActionButtonSample.name,
            description = ExtendedFABExampleDescription,
            sourceUrl = ExtendedFABExampleSourceUrl,
        ) { AnimatedExtendedFloatingActionButtonSample() },
    )

private const val FloatingActionButtonsExampleDescription = "Floating action button examples"
private const val FloatingActionButtonsExampleSourceUrl =
    "$SampleSourceUrl/FloatingActionButtonSamples.kt"
val FloatingActionButtonsExamples =
    listOf(
        Example(
            name = ::FloatingActionButtonSample.name,
            description = FloatingActionButtonsExampleDescription,
            sourceUrl = FloatingActionButtonsExampleSourceUrl,
        ) { FloatingActionButtonSample() },
        Example(
            name = ::LargeFloatingActionButtonSample.name,
            description = FloatingActionButtonsExampleDescription,
            sourceUrl = FloatingActionButtonsExampleSourceUrl,
        ) { LargeFloatingActionButtonSample() },
        Example(
            name = ::SmallFloatingActionButtonSample.name,
            description = FloatingActionButtonsExampleDescription,
            sourceUrl = FloatingActionButtonsExampleSourceUrl,
        ) { SmallFloatingActionButtonSample() }
    )

private const val ListsExampleDescription = "List examples"
private const val ListsExampleSourceUrl = "$SampleSourceUrl/ListSamples.kt"
val ListsExamples = listOf(
    Example(
        name = ::OneLineListItem.name,
        description = ListsExampleDescription,
        sourceUrl = ListsExampleSourceUrl
    ) {
        OneLineListItem()
    },
    Example(
        name = ::TwoLineListItem.name,
        description = ListsExampleDescription,
        sourceUrl = ListsExampleSourceUrl
    ) {
        TwoLineListItem()
    },
    Example(
        name = ::ThreeLineListItem.name,
        description = ListsExampleDescription,
        sourceUrl = ListsExampleSourceUrl
    ) {
        ThreeLineListItem()
    },
)

private const val IconButtonExampleDescription = "Icon button examples"
private const val IconButtonExampleSourceUrl = "$SampleSourceUrl/IconButtonSamples.kt"
val IconButtonExamples =
    listOf(
        Example(
            name = ::IconButtonSample.name,
            description = IconButtonExampleDescription,
            sourceUrl = IconButtonExampleSourceUrl,
        ) { IconButtonSample() },
        Example(
            name = ::IconToggleButtonSample.name,
            description = IconButtonExampleDescription,
            sourceUrl = IconButtonExampleSourceUrl,
        ) { IconToggleButtonSample() },
        Example(
            name = ::FilledIconButtonSample.name,
            description = IconButtonExampleDescription,
            sourceUrl = IconButtonExampleSourceUrl,
        ) { FilledIconButtonSample() },
        Example(
            name = ::FilledIconToggleButtonSample.name,
            description = IconButtonExampleDescription,
            sourceUrl = IconButtonExampleSourceUrl,
        ) { FilledIconToggleButtonSample() },
        Example(
            name = ::FilledTonalIconButtonSample.name,
            description = IconButtonExampleDescription,
            sourceUrl = IconButtonExampleSourceUrl,
        ) { FilledTonalIconButtonSample() },
        Example(
            name = ::FilledTonalIconToggleButtonSample.name,
            description = IconButtonExampleDescription,
            sourceUrl = IconButtonExampleSourceUrl,
        ) { FilledTonalIconToggleButtonSample() },
        Example(
            name = ::OutlinedIconButtonSample.name,
            description = IconButtonExampleDescription,
            sourceUrl = IconButtonExampleSourceUrl,
        ) { OutlinedIconButtonSample() },
        Example(
            name = ::OutlinedIconToggleButtonSample.name,
            description = IconButtonExampleDescription,
            sourceUrl = IconButtonExampleSourceUrl,
        ) { OutlinedIconToggleButtonSample() }
    )

private const val MenusExampleDescription = "Menus examples"
private const val MenusExampleSourceUrl = "$SampleSourceUrl/MenuSamples.kt"
val MenusExamples = listOf(
    Example(
        name = ::MenuSample.name,
        description = MenusExampleDescription,
        sourceUrl = MenusExampleSourceUrl
    ) {
        MenuSample()
    },
    Example(
        name = ::ExposedDropdownMenuSample.name,
        description = MenusExampleDescription,
        sourceUrl = MenusExampleSourceUrl
    ) {
        ExposedDropdownMenuSample()
    },
    Example(
        name = ::EditableExposedDropdownMenuSample.name,
        description = MenusExampleDescription,
        sourceUrl = MenusExampleSourceUrl
    ) {
        EditableExposedDropdownMenuSample()
    },
)

private const val NavigationBarExampleDescription = "Navigation bar examples"
private const val NavigationBarExampleSourceUrl = "$SampleSourceUrl/NavigationBarSamples.kt"
val NavigationBarExamples =
    listOf(
        Example(
            name = ::NavigationBarSample.name,
            description = NavigationBarExampleDescription,
            sourceUrl = NavigationBarExampleSourceUrl,
        ) { NavigationBarSample() },
        Example(
            name = ::NavigationBarWithOnlySelectedLabelsSample.name,
            description = NavigationBarExampleDescription,
            sourceUrl = NavigationBarExampleSourceUrl,
        ) { NavigationBarWithOnlySelectedLabelsSample() },
    )

private const val NavigationRailExampleDescription = "Navigation rail examples"
private const val NavigationRailExampleSourceUrl = "$SampleSourceUrl/NavigationRailSamples.kt"
val NavigationRailExamples =
    listOf(
        Example(
            name = ::NavigationRailSample.name,
            description = NavigationRailExampleDescription,
            sourceUrl = NavigationRailExampleSourceUrl,
        ) { NavigationRailSample() },
        Example(
            name = ::NavigationRailWithOnlySelectedLabelsSample.name,
            description = NavigationRailExampleDescription,
            sourceUrl = NavigationRailExampleSourceUrl,
        ) { NavigationRailWithOnlySelectedLabelsSample() },
        Example(
            name = ::NavigationRailBottomAlignSample.name,
            description = NavigationRailExampleDescription,
            sourceUrl = NavigationRailExampleSourceUrl,
        ) { NavigationRailBottomAlignSample() },
    )

private const val NavigationDrawerExampleDescription = "Navigation drawer examples"
private const val NavigationDrawerExampleSourceUrl = "$SampleSourceUrl/DrawerSamples.kt"
val NavigationDrawerExamples = listOf(
    Example(
        name = ::ModalNavigationDrawerSample.name,
        description = NavigationDrawerExampleDescription,
        sourceUrl = NavigationDrawerExampleSourceUrl
    ) {
        ModalNavigationDrawerSample()
    },
    Example(
        name = ::PermanentNavigationDrawerSample.name,
        description = NavigationDrawerExampleDescription,
        sourceUrl = NavigationDrawerExampleSourceUrl
    ) {
        PermanentNavigationDrawerSample()
    },
    Example(
        name = ::DismissibleNavigationDrawerSample.name,
        description = NavigationDrawerExampleDescription,
        sourceUrl = NavigationDrawerExampleSourceUrl
    ) {
        DismissibleNavigationDrawerSample()
    }
)

private const val ProgressIndicatorsExampleDescription = "Progress indicators examples"
private const val ProgressIndicatorsExampleSourceUrl = "$SampleSourceUrl/" +
        "ProgressIndicatorSamples.kt"
val ProgressIndicatorsExamples = listOf(
    Example(
        name = ::LinearProgressIndicatorSample.name,
        description = ProgressIndicatorsExampleDescription,
        sourceUrl = ProgressIndicatorsExampleSourceUrl
    ) {
        LinearProgressIndicatorSample()
    },
    Example(
        name = ::IndeterminateLinearProgressIndicatorSample.name,
        description = ProgressIndicatorsExampleDescription,
        sourceUrl = ProgressIndicatorsExampleSourceUrl
    ) {
        IndeterminateLinearProgressIndicatorSample()
    },
    Example(
        name = ::CircularProgressIndicatorSample.name,
        description = ProgressIndicatorsExampleDescription,
        sourceUrl = ProgressIndicatorsExampleSourceUrl
    ) {
        CircularProgressIndicatorSample()
    },
    Example(
        name = ::IndeterminateCircularProgressIndicatorSample.name,
        description = ProgressIndicatorsExampleDescription,
        sourceUrl = ProgressIndicatorsExampleSourceUrl
    ) {
        IndeterminateCircularProgressIndicatorSample()
    }
)

private const val RadioButtonsExampleDescription = "Radio buttons examples"
private const val RadioButtonsExampleSourceUrl = "$SampleSourceUrl/RadioButtonSamples.kt"
val RadioButtonsExamples = listOf(
    Example(
        name = ::RadioButtonSample.name,
        description = RadioButtonsExampleDescription,
        sourceUrl = RadioButtonsExampleSourceUrl
    ) {
        RadioButtonSample()
    },
    Example(
        name = ::RadioGroupSample.name,
        description = RadioButtonsExampleDescription,
        sourceUrl = RadioButtonsExampleSourceUrl
    ) {
        RadioGroupSample()
    },
)

private const val SlidersExampleDescription = "Sliders examples"
private const val SlidersExampleSourceUrl = "$SampleSourceUrl/SliderSample.kt"
val SlidersExamples = listOf(
    Example(
        name = ::SliderSample.name,
        description = SlidersExampleDescription,
        sourceUrl = SlidersExampleSourceUrl
    ) {
        SliderSample()
    },
    Example(
        name = ::StepsSliderSample.name,
        description = SlidersExampleDescription,
        sourceUrl = SlidersExampleSourceUrl
    ) {
        StepsSliderSample()
    },
    Example(
        name = ::RangeSliderSample.name,
        description = SlidersExampleDescription,
        sourceUrl = SlidersExampleSourceUrl
    ) {
        RangeSliderSample()
    },
    Example(
        name = ::StepRangeSliderSample.name,
        description = SlidersExampleDescription,
        sourceUrl = SlidersExampleSourceUrl
    ) {
        StepRangeSliderSample()
    },
    Example(
        name = ::SliderWithCustomThumbSample.name,
        description = SlidersExampleDescription,
        sourceUrl = SlidersExampleSourceUrl
    ) {
        SliderWithCustomThumbSample()
    },
//    Example(
//        name = ::SliderWithCustomTrackAndThumb.name,
//        description = SlidersExampleDescription,
//        sourceUrl = SlidersExampleSourceUrl
//    ) {
//        SliderWithCustomTrackAndThumb()
//    }
)

private const val SnackbarsExampleDescription = "Snackbars examples"
private const val SnackbarsExampleSourceUrl = "$SampleSourceUrl/ScaffoldSamples.kt"
val SnackbarsExamples = listOf(
    Example(
        name = ::ScaffoldWithSimpleSnackbar.name,
        description = SnackbarsExampleDescription,
        sourceUrl = SnackbarsExampleSourceUrl
    ) {
        ScaffoldWithSimpleSnackbar()
    },
    Example(
        name = ::ScaffoldWithIndefiniteSnackbar.name,
        description = SnackbarsExampleDescription,
        sourceUrl = SnackbarsExampleSourceUrl
    ) {
        ScaffoldWithIndefiniteSnackbar()
    },
    Example(
        name = ::ScaffoldWithCustomSnackbar.name,
        description = SnackbarsExampleDescription,
        sourceUrl = SnackbarsExampleSourceUrl
    ) {
        ScaffoldWithCustomSnackbar()
    },
    Example(
        name = ::ScaffoldWithCoroutinesSnackbar.name,
        description = SnackbarsExampleDescription,
        sourceUrl = SnackbarsExampleSourceUrl
    ) {
        ScaffoldWithCoroutinesSnackbar()
    }
)

private const val SwitchExampleDescription = "Switch examples"
private const val SwitchExampleSourceUrl = "$SampleSourceUrl/Switch.kt"
val SwitchExamples = listOf(
    Example(
        name = ::SwitchSample.name,
        description = SwitchExampleDescription,
        sourceUrl = SwitchExampleSourceUrl
    ) {
        SwitchSample()
    },

    Example(
        name = ::SwitchWithThumbIconSample.name,
        description = SwitchExampleDescription,
        sourceUrl = SwitchExampleSourceUrl
    ) {
        SwitchWithThumbIconSample()
    },
)

private const val TabsExampleDescription = "Tabs examples"
private const val TabsExampleSourceUrl = "$SampleSourceUrl/TabSamples.kt"
val TabsExamples = listOf(
    Example(
        name = ::TextTabs.name,
        description = TabsExampleDescription,
        sourceUrl = TabsExampleSourceUrl
    ) {
        TextTabs()
    },
    Example(
        name = ::IconTabs.name,
        description = TabsExampleDescription,
        sourceUrl = TabsExampleSourceUrl
    ) {
        IconTabs()
    },
    Example(
        name = ::TextAndIconTabs.name,
        description = TabsExampleDescription,
        sourceUrl = TabsExampleSourceUrl
    ) {
        TextAndIconTabs()
    },
    Example(
        name = ::LeadingIconTabs.name,
        description = TabsExampleDescription,
        sourceUrl = TabsExampleSourceUrl
    ) {
        LeadingIconTabs()
    },
    Example(
        name = ::ScrollingTextTabs.name,
        description = TabsExampleDescription,
        sourceUrl = TabsExampleSourceUrl
    ) {
        ScrollingTextTabs()
    },
    Example(
        name = ::FancyTabs.name,
        description = TabsExampleDescription,
        sourceUrl = TabsExampleSourceUrl
    ) {
        FancyTabs()
    },
    Example(
        name = ::FancyIndicatorTabs.name,
        description = TabsExampleDescription,
        sourceUrl = TabsExampleSourceUrl
    ) {
        FancyIndicatorTabs()
    },
    Example(
        name = ::FancyIndicatorContainerTabs.name,
        description = TabsExampleDescription,
        sourceUrl = TabsExampleSourceUrl
    ) {
        FancyIndicatorContainerTabs()
    },
    Example(
        name = ::ScrollingFancyIndicatorContainerTabs.name,
        description = TabsExampleDescription,
        sourceUrl = TabsExampleSourceUrl
    ) {
        ScrollingFancyIndicatorContainerTabs()
    }
)

private const val TextFieldsExampleDescription = "Text fields examples"
private const val TextFieldsExampleSourceUrl = "$SampleSourceUrl/TextFieldSamples.kt"
val TextFieldsExamples = listOf(
    Example(
        name = ::SimpleTextFieldSample.name,
        description = TextFieldsExampleDescription,
        sourceUrl = TextFieldsExampleSourceUrl
    ) {
        SimpleTextFieldSample()
    },
    Example(
        name = ::TextFieldSample.name,
        description = TextFieldsExampleDescription,
        sourceUrl = TextFieldsExampleSourceUrl
    ) {
        TextFieldSample()
    },
    Example(
        name = ::SimpleOutlinedTextFieldSample.name,
        description = TextFieldsExampleDescription,
        sourceUrl = TextFieldsExampleSourceUrl
    ) {
        SimpleOutlinedTextFieldSample()
    },
    Example(
        name = ::OutlinedTextFieldSample.name,
        description = TextFieldsExampleDescription,
        sourceUrl = TextFieldsExampleSourceUrl
    ) {
        OutlinedTextFieldSample()
    },
    Example(
        name = ::TextFieldWithIcons.name,
        description = TextFieldsExampleDescription,
        sourceUrl = TextFieldsExampleSourceUrl
    ) {
        TextFieldWithIcons()
    },
    Example(
        name = ::TextFieldWithPlaceholder.name,
        description = TextFieldsExampleDescription,
        sourceUrl = TextFieldsExampleSourceUrl
    ) {
        TextFieldWithPlaceholder()
    },
    Example(
        name = ::TextFieldWithErrorState.name,
        description = TextFieldsExampleDescription,
        sourceUrl = TextFieldsExampleSourceUrl
    ) {
        TextFieldWithErrorState()
    },
    Example(
        name = ::TextFieldWithSupportingText.name,
        description = TextFieldsExampleDescription,
        sourceUrl = TextFieldsExampleSourceUrl
    ) {
        TextFieldWithSupportingText()
    },
    Example(
        name = ::PasswordTextField.name,
        description = TextFieldsExampleDescription,
        sourceUrl = TextFieldsExampleSourceUrl
    ) {
        PasswordTextField()
    },
    Example(
        name = ::TextFieldWithHideKeyboardOnImeAction.name,
        description = TextFieldsExampleDescription,
        sourceUrl = TextFieldsExampleSourceUrl
    ) {
        TextFieldWithHideKeyboardOnImeAction()
    },
    Example(
        name = ::TextArea.name,
        description = TextFieldsExampleDescription,
        sourceUrl = TextFieldsExampleSourceUrl
    ) {
        TextArea()
    }
).map {
    // By default text field samples are minimal and don't have a `width` modifier to restrict the
    // width. As a result, they grow horizontally if enough text is typed. To prevent this behavior
    // in Catalog app the code below restricts the width of every text field sample
    it.copy(content = {
        Box(
            Modifier
                .wrapContentWidth()
                .width(280.dp)
        ) { it.content() }
    })
}