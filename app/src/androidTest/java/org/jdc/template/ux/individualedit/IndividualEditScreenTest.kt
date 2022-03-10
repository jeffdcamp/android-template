package org.jdc.template.ux.individualedit

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import kotlinx.coroutines.flow.MutableStateFlow
import org.jdc.template.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class IndividualEditScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testFields() {
        val firstNameFlow = MutableStateFlow("Jeff")
        val lastNameFlow = MutableStateFlow("Campbell")
        val phoneNumberFlow = MutableStateFlow("801-555-0000")
        val emailFlow = MutableStateFlow("")
        val birthDateFlow = MutableStateFlow<LocalDate?>(null)
        val alarmTimeFlow = MutableStateFlow<LocalTime?>(null)

        val individualState = IndividualState(
            firstNameFlow,
            { firstNameFlow.value = it },
            lastNameFlow,
            { lastNameFlow.value = it },
            phoneNumberFlow,
            { phoneNumberFlow.value = it },
            emailFlow,
            { emailFlow.value = it },
            birthDateFlow,
            {},
            alarmTimeFlow,
            {}
        )

        composeTestRule.setContent {
            AppTheme {
                IndividualEditFields(individualState)
            }
        }

        composeTestRule.onNodeWithTag("firstNameEditTextTag").assertTextContains("Jeff")
        composeTestRule.onNodeWithTag("lastNameEditTextTag").assertTextContains("Campbell")
        composeTestRule.onNodeWithTag("phoneEditTextTag").assertTextContains("801-555-0000")
        composeTestRule.onNodeWithTag("emailEditTextTag").assertTextContains("")
    }
}