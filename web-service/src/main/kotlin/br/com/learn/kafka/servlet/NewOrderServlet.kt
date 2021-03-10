package br.com.learn.kafka.servlet

import br.com.learn.kafka.KafkaDispatcher
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.math.BigDecimal

class NewOrderServlet : HttpServlet() {

    private val orderDispatcher = KafkaDispatcher<Order>()

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        val emailAddress = req?.getParameter("email") ?: ""
        val amountValue = req?.getParameter("amount") ?: "0"
        val productId = req?.getParameter("productId")?.toInt() ?: 0

        val order = Order(productId, BigDecimal(amountValue), emailAddress)
        orderDispatcher.send("ecommerce_new_order", order.email, order)
        val email = Email(
            emailAddress,
            "Welcome",
            "Thank you and blah, blah, blah"
        )
        KafkaDispatcher<Email>().send("ecommerce_send_email", email.toString(), email)
        resp?.writer?.println("Done :)\n${order.id}")
    }
}
