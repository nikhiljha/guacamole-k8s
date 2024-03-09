package com.nikhiljha.guacamole_k8s.com.nikhiljha.guacamole_k8s

import org.apache.guacamole.net.auth.permission.ObjectPermissionSet
import org.apache.guacamole.net.auth.simple.SimpleObjectPermissionSet
import org.apache.guacamole.net.auth.simple.SimpleUser

class KubernetesUser(username: String, private val parent: KubernetesUserContext): SimpleUser(username) {
    override fun getConnectionGroupPermissions(): ObjectPermissionSet {
        return SimpleObjectPermissionSet(this.parent.connectionDirectory.identifiers)
    }

    override fun getConnectionPermissions(): ObjectPermissionSet {
        return SimpleObjectPermissionSet(this.parent.connectionGroupDirectory.identifiers)
    }
}
