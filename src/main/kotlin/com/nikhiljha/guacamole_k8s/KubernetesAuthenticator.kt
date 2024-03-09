package com.nikhiljha.guacamole_k8s

import com.nikhiljha.guacamole_k8s.com.nikhiljha.guacamole_k8s.KubernetesUserContext
import org.apache.guacamole.GuacamoleSecurityException
import org.apache.guacamole.environment.LocalEnvironment
import org.apache.guacamole.net.auth.AuthenticatedUser
import org.apache.guacamole.net.auth.Credentials
import org.apache.guacamole.net.auth.UserContext
import org.apache.guacamole.net.auth.simple.SimpleAuthenticationProvider
import org.apache.guacamole.protocol.GuacamoleConfiguration
import org.slf4j.LoggerFactory

class KubernetesAuthenticator: SimpleAuthenticationProvider() {
    private val logger = LoggerFactory.getLogger(KubernetesAuthenticator::class.java as Class<*>);
    private val environment = LocalEnvironment.getInstance()

    override fun getIdentifier(): String {
        return "kubernetes"
    }

    /**
     * The Kubernetes authenticator only provides authorized configurations and
     * cannot authenticate.
     */
    override fun authenticateUser(credentials: Credentials?): AuthenticatedUser? {
        return null
    }

    override fun getAuthorizedConfigurations(p0: Credentials?): MutableMap<String, GuacamoleConfiguration> {
        if (p0 == null) {
            throw GuacamoleSecurityException("No credentials provided")
        }

        return mutableMapOf()
    }

    override fun getUserContext(authenticatedUser: AuthenticatedUser?): UserContext {
        if (authenticatedUser?.identifier == null) {
            throw GuacamoleSecurityException("No authenticated user")
        }
        return KubernetesUserContext(this, authenticatedUser.identifier)
    }
}
