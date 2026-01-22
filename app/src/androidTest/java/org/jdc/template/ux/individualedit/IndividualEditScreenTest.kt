package org.jdc.template.ux.individualedit

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import kotlinx.coroutines.flow.MutableStateFlow
import org.jdc.template.shared.model.domain.type.IndividualType
import org.junit.Rule
import org.junit.Test

class IndividualEditScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testFields() {
        val firstNameFlow = MutableStateFlow("Jeff")
        val lastNameFlow = MutableStateFlow("Campbell")
        val phoneNumberFlow = MutableStateFlow("801-555-0000")
        val emailFlow = MutableStateFlow("")

        val uiState = IndividualEditUiState.Ready(
            formFields = IndividualEditFormFields(
                firstNameFlow = firstNameFlow,
                lastNameFlow = lastNameFlow,
                phoneNumberFlow = phoneNumberFlow,
                emailFlow = emailFlow,
                emailErrorFlow = MutableStateFlow(null),
                birthDateFlow = MutableStateFlow(null),
                birthDateErrorFlow = MutableStateFlow(null),
                firstNameErrorFlow = MutableStateFlow(null),
                alarmTimeFlow = MutableStateFlow(null),
                individualTypeFlow = MutableStateFlow(IndividualType.UNKNOWN),
                individualTypeErrorFlow = MutableStateFlow(null),
                availableFlow = MutableStateFlow(false),
            )
        )

        composeTestRule.setContent {
            IndividualEditContent(
                uiState = uiState,
                onFirstNameChange = {},
                onLastNameChange = {},
                onPhoneChange = {},
                onEmailChange = {},
                onBirthDateClick = {},
                onAlarmTimeClick = {},
                onIndividualTypeChange = {},
                onAvailableChange = {}
            )
        }

        composeTestRule.onNodeWithTag(IndividualEditScreenFields.FIRST_NAME.name).assertTextContains("Jeff")
        composeTestRule.onNodeWithTag(IndividualEditScreenFields.LAST_NAME.name).assertTextContains("Campbell")
        composeTestRule.onNodeWithTag(IndividualEditScreenFields.PHONE.name).assertTextContains("801-555-0000")
        composeTestRule.onNodeWithTag(IndividualEditScreenFields.EMAIL.name).assertTextContains("")
    }
}