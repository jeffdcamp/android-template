package org.jdc.template.ux.individualedit

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class IndividualEditScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testFields() {
        val uiState = IndividualEditUiState.Ready(
            formFields = IndividualEditFormFields(
                firstName = "Jeff",
                lastName = "Campbell",
                phoneNumber = "801-555-0000",
                email = "",
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