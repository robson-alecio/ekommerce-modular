package br.com.learn.kafka.servlet

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe

class NewOrderServletTest : ShouldSpec() {

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        println("before")
    }

    init {
        should("do something") {
            "blah" shouldBe "bleh"
        }

        should("do something else") {
            "blah" shouldBe "bleh"
        }
    }
}