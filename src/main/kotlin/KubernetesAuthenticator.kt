package com.nikhiljha

import org.apache.guacamole.net.auth.Credentials
import org.apache.guacamole.net.auth.simple.SimpleAuthenticationProvider
import org.apache.guacamole.protocol.GuacamoleConfiguration

class KubernetesAuthenticator: SimpleAuthenticationProvider() {
    override fun getIdentifier(): String {
        return "kubernetes"
    }

    override fun getAuthorizedConfigurations(p0: Credentials?): MutableMap<String, GuacamoleConfiguration> {
        return mutableMapOf()
    }
}