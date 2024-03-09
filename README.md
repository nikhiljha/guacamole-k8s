# guacamole-k8s

This repository contains a Guacamole plugin that automatically discovers Kubernetes
services and adds them to the Guacamole interface.

## Usage

First, make sure your authentication method of choice has priority over this one.
For example, if you're using the `openid` plugin, make sure it's prioritized in
`$GUACAMOLE_HOME/guacamole.properties`.

```properties
extension-priority: openid
```

Then, make sure your Guacamole instance has access to the Kubernetes API. This
can be done by creating a cluster role that allows access to services.

```hcl
resource "kubernetes_cluster_role" "guacamole" {
  metadata {
    name = "guacamole"
  }
  rule {
    api_groups = [""]
    resources  = ["services"]
    verbs      = ["get", "list", "watch"]
  }
}
```

Create a Kubernetes pod that runs a VNC server. Then, create an associated service
that has the following labels...

```yaml
guacamole.njha.dev/enabled: "true"
```

And also the following annotations (replace `5900` with the port the VNC server
is running on). The `owner` annotation is used to determine who can access the
service. Authentication can be handled by any one of the other Guacamole plugins.

```yaml
guacamole.njha.dev/vnc: "5900"
guacamole.njha.dev/owner: "foo@example.com"
```
