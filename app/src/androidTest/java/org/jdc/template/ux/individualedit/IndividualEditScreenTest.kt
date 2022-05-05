package org.jdc.template.ux.individualedit

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import kotlinx.coroutines.flow.MutableStateFlow
import org.jdc.template.ui.theme.AppTheme
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

        val individualEditUiState = IndividualEditUiState(
            firstNameFlow = firstNameFlow,
            firstNameOnChange = { firstNameFlow.value = it },
            lastNameFlow = lastNameFlow,
            lastNameOnChange = { lastNameFlow.value = it },
            phoneFlow = phoneNumberFlow,
            phoneOnChange = { phoneNumberFlow.value = it },
            emailFlow = emailFlow,
            emailOnChange = { emailFlow.value = it }
        )

        composeTestRule.setContent {
            AppTheme {
                IndividualEditFields(individualEditUiState)
            }
        }

        composeTestRule.onNodeWithTag("firstNameEditTextTag").assertTextContains("Jeff")
        composeTestRule.onNodeWithTag("lastNameEditTextTag").assertTextContains("Campbell")
        composeTestRule.onNodeWithTag("phoneEditTextTag").assertTextContains("801-555-0000")
        composeTestRule.onNodeWithTag("emailEditTextTag").assertTextContains("")
    }
}