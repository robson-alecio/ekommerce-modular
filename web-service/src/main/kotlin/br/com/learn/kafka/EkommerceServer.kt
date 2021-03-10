package br.com.learn.kafka

import br.com.learn.kafka.servlet.NewOrderServlet
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder

object EkommerceServer {

    @JvmStatic
    fun main(args: Array<String>) {
        val server = Server(8080)

        val context = ServletContextHandler()
        context.contextPath = "/"
        context.addServlet(ServletHolder(NewOrderServlet()), "/order/new")

        server.handler = context

        server.start()
        server.join()
    }

}