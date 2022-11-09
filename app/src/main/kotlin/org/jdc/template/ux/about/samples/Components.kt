package org.jdc.template.ux.about.samples

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

data class Component(
    val id: Int,
    val name: String,
    val description: String,
    val examples: List<Example>
)

private var nextId: Int = 1
private fun nextId(): Int = nextId.also { nextId += 1 }

// Components are ordered alphabetically by name.

private val Badge =
    Component(
        id = nextId(),
        name = "Badge",
        description =
        "A badge can contain dynamic information, such as the presence of a new " +
                "notification or a number of pending requests. Badges can be icon only or contain " +
                "a short text.",
        examples = BadgeExamples
    )

private val BottomAppBars = Component(
    id = nextId(),
    name = "Bottom App Bar",
    description = "A bottom app bar displays navigation and key actions at the bottom of mobile " +
            "screens.",
    examples = BottomAppBarsExamples
)

private val Buttons = Component(
    id = nextId(),
    name = "Buttons",
    description = "Buttons help people initiate actions, from sending an email, to sharing a " +
            "document, to liking a post.",
    examples = ButtonsExamples,
)

private val Card = Component(
    id = nextId(),
    name = "Card",
    description = "Cards contain content and actions that relate information about a subject.",
    examples = CardExamples
)

private val Checkboxes = Component(
    id = nextId(),
    name = "Checkboxes",
    description = "Checkboxes allow the user to select one or more items from a set or turn an " +
            "option on or off.",
    examples = CheckboxesExamples
)

private val Chips = Component(
    id = nextId(),
    name = "Chips",
    description = "Chips allow users to enter information, make selections, filter content, or" +
            " trigger actions.",
    examples = ChipsExamples
)

private val Dialogs = Component(
    id = nextId(),
    name = "Dialogs",
    description = "Dialogs provide important prompts in a user flow. They can require an action, " +
            "communicate information, or help users accomplish a task.",
    examples = DialogExamples
)

private val ExtendedFloatingActionButton = Component(
    id = nextId(),
    name = "Extended FAB",
    description = "Extended FABs help people take primary actions. They're wider than FABs to " +
            "accommodate a text label and larger target area.",
    examples = ExtendedFABExamples,
)

private val FloatingActionButtons = Component(
    id = nextId(),
    name = "Floating action buttons",
    description = "The FAB represents the most important action on a screen. It puts key actions " +
            "within reach.",
    examples = FloatingActionButtonsExamples,
)

private val IconButtons = Component(
    id = nextId(),
    name = "Icon buttons",
    description = "Icon buttons allow users to take actions and make choices with a single tap.",
    examples = IconButtonExamples,
)

private val Lists = Component(
    id = nextId(),
    name = "Lists",
    description = "Lists are continuous, vertical indexes of text or images.",
    examples = ListsExamples
)

private val Menus = Component(
    id = nextId(),
    name = "Menus",
    description = "Menus display a list of choices on temporary surfaces.",
    examples = MenusExamples
)

private val NavigationBar = Component(
    id = nextId(),
    name = "Navigation bar",
    description = "Navigation bars offer a persistent and convenient way to switch between " +
            "primary destinations in an app.",
    examples = NavigationBarExamples
)

private val NavigationDrawer = Component(
    id = nextId(),
    name = "Navigation drawer",
    description = "Navigation drawers provide ergonomic access to destinations in an app.",
    examples = NavigationDrawerExamples
)

private val NavigationRail = Component(
    id = nextId(),
    name = "Navigation rail",
    description = "Navigation rails provide access to primary destinations in apps when using " +
            "tablet and desktop screens.",
    examples = NavigationRailExamples
)

private val ProgressIndicators = Component(
    id = nextId(),
    name = "Progress indicators",
    description = "Progress indicators express an unspecified wait time or display the length of " +
            "a process.",
    examples = ProgressIndicatorsExamples
)

private val RadioButtons = Component(
    id = nextId(),
    name = "Radio buttons",
    description = "Radio buttons allow the user to select one option from a set.",
    examples = RadioButtonsExamples
)

private val Sliders = Component(
    id = nextId(),
    name = "Sliders",
    description = "Sliders allow users to make selections from a range of values.",
    examples = SlidersExamples
)

private val Snackbars = Component(
    id = nextId(),
    name = "Snackbars",
    description = "Snackbars provide brief messages about app processes at the bottom of the " +
            "screen.",
    examples = SnackbarsExamples
)

private val Switches = Component(
    id = nextId(),
    name = "Switches",
    description = "Switches toggle the state of a single setting on or off.",
    examples = SwitchExamples
)

private val Tabs = Component(
    id = nextId(),
    name = "Tabs",
    description = "Tabs organize content across different screens, data sets, and other " +
            "interactions.",
    examples = TabsExamples
)

private val TextFields = Component(
    id = nextId(),
    name = "Text fields",
    description = "Text fields let users enter and edit text.",
    examples = TextFieldsExamples
)

private val TopAppBar = Component(
    id = nextId(),
    name = "Top app bar",
    description = "Top app bars display information and actions at the top of a screen.",
    examples = TopAppBarExamples
)

/** Components for the catalog, ordered alphabetically by name. */
val Components = listOf(
    Badge,
    BottomAppBars,
    Buttons,
    Card,
    Checkboxes,
    Chips,
    Dialogs,
    ExtendedFloatingActionButton,
    FloatingActionButtons,
    IconButtons,
    Lists,
    Menus,
    NavigationBar,
    NavigationDrawer,
    NavigationRail,
    ProgressIndicators,
    RadioButtons,
    Sliders,
    Snackbars,
    Switches,
    Tabs,
    TextFields,
    TopAppBar
)

