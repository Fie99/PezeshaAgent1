package com.pezesha.agent.ui.main.addSme

import com.google.common.truth.Truth.assertThat
import com.pezesha.agent.ui.main.addSme.AddSmeButtonStates.ACTIVE
import com.pezesha.agent.ui.main.addSme.AddSmeButtonStates.DONE
import com.pezesha.agent.ui.main.addSme.AddSmeButtonStates.INACTIVE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddSmeViewModelTest {

    private lateinit var SUT: AddSmeViewModel

    @Before
    fun setUp() {
        SUT = AddSmeViewModel()
    }

    @Test
    fun `test consentState initial value to be ACTIVE`() = runTest {
        val expected = ACTIVE
        val result = SUT.consentState.first()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test kycState initial value to be INACTIVE`() = runTest {
        val expected = INACTIVE
        val result = SUT.kycState.first()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test crbState initial value to be INACTIVE`() = runTest {
        val expected = INACTIVE
        val result = SUT.crbState.first()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test docsState initial value to be INACTIVE`() = runTest {
        val expected = INACTIVE
        val result = SUT.docsState.first()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test quizState initial value to be INACTIVE`() = runTest {
        val expected = INACTIVE
        val result = SUT.quizState.first()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test applyState initial value to be INACTIVE`() = runTest {
        val expected = INACTIVE
        val result = SUT.applyState.first()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test addSmeStage initial value to be 1`() = runTest {
        val expected = 1
        val result = SUT.addSmeStage.first()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test different stages state when addSmeStage is 1`() = runTest {
        SUT.moveToStage(1)

        assertThat(SUT.consentState.first()).isEqualTo(ACTIVE)
        assertThat(SUT.kycState.first()).isEqualTo(INACTIVE)
        assertThat(SUT.crbState.first()).isEqualTo(INACTIVE)
        assertThat(SUT.docsState.first()).isEqualTo(INACTIVE)
        assertThat(SUT.quizState.first()).isEqualTo(INACTIVE)
        assertThat(SUT.applyState.first()).isEqualTo(INACTIVE)
    }

    @Test
    fun `test different stages state when addSmeStage is 2`() = runTest {
        SUT.moveToStage(2)

        assertThat(SUT.consentState.first()).isEqualTo(DONE)
        assertThat(SUT.kycState.first()).isEqualTo(ACTIVE)
        assertThat(SUT.crbState.first()).isEqualTo(INACTIVE)
        assertThat(SUT.docsState.first()).isEqualTo(INACTIVE)
        assertThat(SUT.quizState.first()).isEqualTo(INACTIVE)
        assertThat(SUT.applyState.first()).isEqualTo(INACTIVE)
    }

    @Test
    fun `test different stages state when addSmeStage is 3`() = runTest {
        SUT.moveToStage(3)

        assertThat(SUT.consentState.first()).isEqualTo(DONE)
        assertThat(SUT.kycState.first()).isEqualTo(DONE)
        assertThat(SUT.crbState.first()).isEqualTo(ACTIVE)
        assertThat(SUT.docsState.first()).isEqualTo(INACTIVE)
        assertThat(SUT.quizState.first()).isEqualTo(INACTIVE)
        assertThat(SUT.applyState.first()).isEqualTo(INACTIVE)
    }

    @Test
    fun `test different stages state when addSmeStage is 4`() = runTest {
        SUT.moveToStage(4)

        assertThat(SUT.consentState.first()).isEqualTo(DONE)
        assertThat(SUT.kycState.first()).isEqualTo(DONE)
        assertThat(SUT.crbState.first()).isEqualTo(DONE)
        assertThat(SUT.docsState.first()).isEqualTo(ACTIVE)
        assertThat(SUT.quizState.first()).isEqualTo(INACTIVE)
        assertThat(SUT.applyState.first()).isEqualTo(INACTIVE)
    }

    @Test
    fun `test different stages state when addSmeStage is 5`() = runTest {
        SUT.moveToStage(5)

        assertThat(SUT.consentState.first()).isEqualTo(DONE)
        assertThat(SUT.kycState.first()).isEqualTo(DONE)
        assertThat(SUT.crbState.first()).isEqualTo(DONE)
        assertThat(SUT.docsState.first()).isEqualTo(DONE)
        assertThat(SUT.quizState.first()).isEqualTo(ACTIVE)
    }

    @Test
    fun `test different stages state when addSmeStage is 6`() = runTest {
        SUT.moveToStage(6)

        assertThat(SUT.consentState.first()).isEqualTo(DONE)
        assertThat(SUT.kycState.first()).isEqualTo(DONE)
        assertThat(SUT.crbState.first()).isEqualTo(DONE)
        assertThat(SUT.docsState.first()).isEqualTo(DONE)
        assertThat(SUT.quizState.first()).isEqualTo(DONE)
        assertThat(SUT.applyState.first()).isEqualTo(ACTIVE)
    }

    @Test
    fun `test different stages state when addSmeStage is not within 1-6`() = runTest {
        SUT.moveToStage(0)

        assertThat(SUT.consentState.first()).isEqualTo(INACTIVE)
        assertThat(SUT.kycState.first()).isEqualTo(INACTIVE)
        assertThat(SUT.crbState.first()).isEqualTo(INACTIVE)
        assertThat(SUT.docsState.first()).isEqualTo(INACTIVE)
        assertThat(SUT.quizState.first()).isEqualTo(INACTIVE)
        assertThat(SUT.applyState.first()).isEqualTo(INACTIVE)
    }

}
