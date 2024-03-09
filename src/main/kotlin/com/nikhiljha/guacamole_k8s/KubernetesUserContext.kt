package com.nikhiljha.guacamole_k8s.com.nikhiljha.guacamole_k8s

import com.nikhiljha.guacamole_k8s.KubernetesAuthenticator
import io.fabric8.kubernetes.client.KubernetesClientBuilder
import org.apache.guacamole.net.auth.*
import org.apache.guacamole.net.auth.simple.SimpleConnection
import org.apache.guacamole.net.auth.simple.SimpleDirectory
import org.apache.guacamole.protocol.GuacamoleConfiguration


class KubernetesUserContext(private val parent: KubernetesAuthenticator, private val username: String) :
    AbstractUserContext() {
    private val logger = org.slf4j.LoggerFactory.getLogger(KubernetesUserContext::class.java)
    private var user: User? = null
    private val client = KubernetesClientBuilder().build()

    override fun self(): User {
        if (user != null) {
            return user!!
        }

        val user = KubernetesUser(username, this)
        return user
    }

    override fun getAuthenticationProvider(): AuthenticationProvider {
        return parent
    }

    override fun getConnectionDirectory(): Directory<Connection> {
        val connections: HashMap<String, Connection> = HashMap()
        client.services().inAnyNamespace().withLabel("guacamole.njha.dev/enabled", "true")
            .list().items.forEach {
                if (it.metadata.annotations["guacamole.njha.dev/owner"] != username) {
                    return@forEach
                }
                val name = "${it.metadata.namespace}/${it.metadata.name}"
                val vncPort = it.metadata.annotations["guacamole.njha.dev/vnc"]
                if (vncPort != null) {
                    val conn = GuacamoleConfiguration()
                    conn.protocol = "vnc"
                    conn.setParameter("hostname", it.spec.clusterIP)
                    if (vncPort.toInt() !in 0..65535) {
                        logger.warn("Invalid port ($vncPort) for VNC connection: $name")
                        return@forEach
                    }
                    conn.setParameter("port", vncPort)
                    val connection = SimpleConnection(name, name, conn)
                    connection.parentIdentifier = "ROOT"
                    connections[name] = connection
                }
            }

        return SimpleDirectory(connections)
    }
}
