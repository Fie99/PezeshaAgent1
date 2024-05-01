package com.pezesha.agent.ui.main.addSme.consent.getConsent

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.pezesha.agent.MainCoroutineRule
import com.pezesha.agent.domain.register.RegisterRepository
import com.pezesha.agent.domain.register.useCase.GetRequestConsentUseCase
import com.pezesha.agent.domain.register.useCase.GetVerifyConsentUseCase
import com.pezesha.agent.domain.user.UserRepository
import com.pezesha.agent.domain.user.usecase.GetUserCountryCode
import com.pezesha.agent.getValueForTest
import com.pezesha.agent.ui.main.addSme.consent.GetConsentViewModel
import com.pezesha.agent.utils.FormValidator
import com.pezesha.agent.utils.extensions.formatPhoneNumber
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class GetConsentViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineRule()

    private lateinit var SUT: GetConsentViewModel

    private val formValidator = mockk<FormValidator>()
    private val userRepository = mockk<UserRepository>()
    private val registerRepository = mockk<RegisterRepository>()

    private lateinit var getUserCountryCode: GetUserCountryCode
    private lateinit var getVerifyConsentUseCase: GetVerifyConsentUseCase
    private lateinit var getRequestConsentUseCase: GetRequestConsentUseCase

    @Before
    fun setUp() {
        getUserCountryCode = GetUserCountryCode(repository = userRepository)

        getVerifyConsentUseCase = GetVerifyConsentUseCase(repository = registerRepository)
        getRequestConsentUseCase = GetRequestConsentUseCase(repository = registerRepository)

        SUT = GetConsentViewModel(
            formValidator = formValidator,
            getUserCountryCode = getUserCountryCode,
            getRequestConsentUseCase = getRequestConsentUseCase,
            getVerifyConsentUseCase = getVerifyConsentUseCase
        )

        // Mock repository result
        coEvery { userRepository.getUserPhoneCode() } returns "+254"

        // Simulate the behavior of the Success instance
//        val result = Resource.Success("Success")

//        coEvery { registerRepository.sendConsent("") } returns result
//        coEvery { registerRepository.verifyConsent("") } returns result

        // Mock the extension function
        mockkStatic("com.pezesha.agent.utils.extensions.StringExtensionsKt")

        // Define the behavior of the extension function for the test case
        coEvery { any<String>().formatPhoneNumber(any()) } returns "12345"
    }

    @Test
    fun `test initial value of phone to be empty string`() = runTest {
        val expected = ""
        val result = SUT.phone.first()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test initial value of phoneValid to be false`() = runTest {
        val result = SUT.phoneValid.first()
        assertThat(result).isFalse()
    }

    @Test
    fun `test initial value of phoneError to be null`() {
        val result = SUT.phoneError.getValueForTest()
        assertThat(result).isNull()
    }

    @Test
    fun `test initial value of consentCode to be empty string`() = runTest {
        val expected = ""
        val result = SUT.consentCode.first()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test initial value of consentCodeValid to be false`() = runTest {
        val result = SUT.consentCodeValid.first()
        assertThat(result).isFalse()
    }

    @Test
    fun `test initial value of consentCodeError to be null`() {
        val result = SUT.consentCodeError.getValueForTest()
        assertThat(result).isNull()
    }


    @Test
    fun `test setPhone with valid phone number`() = runTest {
        // Mock formValidator result
        coEvery { formValidator.isPhoneNumberValid(any()) } returns Pair(null, true)

        val expected = "+254712345678"
        val expectedErrorMsg = null
        val expectedIsPhoneValid = true

        // Mock the extension function
        mockkStatic("com.pezesha.agent.utils.extensions.StringExtensionsKt")
        coEvery { any<String>().formatPhoneNumber(any()) } returns expected

        SUT.setPhone("+254712345678")

        assertThat(SUT.phone.first()).isEqualTo(expected)
        assertThat(SUT.phoneValid.first()).isEqualTo(expectedIsPhoneValid)
        assertThat(SUT.phoneError.getValueForTest()).isEqualTo(expectedErrorMsg)
    }

    @Test
    fun `test setPhone with invalid phone number`() = runTest {
        // Mock formValidator result
        coEvery { formValidator.isPhoneNumberValid(any()) } returns Pair(
            "Invalid Phone Number",
            false
        )

        val expected = "12345"
        val expectedErrorMsg = "Invalid Phone Number"
        val expectedIsPhoneValid = false

        SUT.setPhone("")

        val phoneError: String? = SUT.phoneError.getValueForTest()

        assertThat(SUT.phone.first()).isEqualTo(expected)
        assertThat(SUT.phoneValid.first()).isEqualTo(expectedIsPhoneValid)
        assertThat(phoneError).isEqualTo(expectedErrorMsg)
        assertThat(phoneError).isNotNull()
    }

    @Test
    fun `test setConsentCode with valid consent code`() = runTest {
        // Mock formValidator result
        coEvery { formValidator.isCodeValid(any()) } returns Pair(null, true)

        SUT.setConsentCode("123456")

        val expected = "123456"
        val expectedErrorMsg = null
        val expectedIsPhoneValid = true

        assertThat(SUT.consentCode.first()).isEqualTo(expected)
        assertThat(SUT.consentCodeValid.first()).isEqualTo(expectedIsPhoneValid)
        assertThat(SUT.consentCodeError.getValueForTest()).isEqualTo(expectedErrorMsg)
    }

    @Test
    fun `test setConsentCode with invalid consent code`() = runTest {
        // Mock formValidator result
        coEvery { formValidator.isCodeValid(any()) } returns Pair("Invalid Code", false)

        SUT.setConsentCode("1234")

        val expected = "1234"
        val expectedErrorMsg = "Invalid Code"
        val expectedIsPhoneValid = false

        val codeError: String? = SUT.consentCodeError.getValueForTest()

        assertThat(SUT.consentCode.first()).isEqualTo(expected)
        assertThat(SUT.consentCodeValid.first()).isEqualTo(expectedIsPhoneValid)
        assertThat(codeError).isEqualTo(expectedErrorMsg)
        assertThat(codeError).isNotNull()
    }

    @Test
    fun `test isSubmitEnabled when both phone and consent code are valid`() = runTest {
        // Mock formValidator results
        coEvery { formValidator.isPhoneNumberValid(any()) } returns Pair(null, true)
        coEvery { formValidator.isCodeValid(any()) } returns Pair(null, true)

        SUT.setPhone("")
        SUT.setConsentCode("")

        // Act
        val result = SUT.isSubmitEnabled.first()

        // Assert
        assertThat(result).isTrue()
    }


    @Test
    fun `test isSubmitEnabled when either phone or consent code is invalid`() = runTest {
        // Mock formValidator results
        coEvery { formValidator.isPhoneNumberValid(any()) } returns Pair(
            "Invalid Phone Number",
            false
        )
        coEvery { formValidator.isCodeValid(any()) } returns Pair(null, true)

        SUT.setPhone("")
        SUT.setConsentCode("")

        // Act
        val result = SUT.isSubmitEnabled.first()

        // Assert
        assertThat(result).isFalse()
    }

}