# rso-catalogues

Projekt se builda sedaj z Java 11. Nastavi JAVA_HOME na ta JDK. Pravilnost nastavitve se preveri preko:

```
mvn -version
```

Tu mora pisati, da se uporablja Java 11. Če vmes spremeniš JAVA_HOME moraš zagnati shell (in IntelliJ, če uporabljaš
terminal v njem).

### Configuration

#### Run etcd node
First install ectd locally. Figure out your Docker IP (ifconfig -> Docker ip) and save it.
Then setup a single node:

NOTE: 10.0.75.1 was my local IP and it's probably used a bunch of times in the following commands. Feel free to change :)

NOTE2: Make sure the correct IP is used in the config.yaml file, under etcd hosts (an odd number of nodes is needed)
```
# SETUP AND RUN ETCD NODE
# FROM https://coreos.com/etcd/docs/latest/v2/docker_guide.html
# Deleted volume for the time being

# HostIP is your Docker's ip.
# In the following commands, I kept my own one
export HostIP=10.0.75.1

# Run a etcd node
docker run -d -p 4001:4001 -p 2380:2380 -p 2379:2379 \
 --name etcd quay.io/coreos/etcd:v2.3.8 \
 -name etcd0 \
 -advertise-client-urls http://${HostIP}:2379,http://${HostIP}:4001 \
 -listen-client-urls http://0.0.0.0:2379,http://0.0.0.0:4001 \
 -initial-advertise-peer-urls http://${HostIP}:2380 \
 -listen-peer-urls http://0.0.0.0:2380 \
 -initial-cluster-token etcd-cluster-1 \
 -initial-cluster etcd0=http://${HostIP}:2380 \
 -initial-cluster-state new

# List nodes, I guess?
etcdctl -C http://10.0.75.1:2379 member list
```

#### Create first property

To create a property for this project, use this command:

```
etcdctl --endpoints //10.0.75.1:2379 set /environments/dev/services/rso-catalogues/1.0.0/config/config-bundle/string-property test_string
```

config-bundle is what I called the bundle. Check ConfigurationProperties.java and the @ConfigBundle annotation

string-property will be mapped in Java into stringProperty. Annotate the stringProperty field with @ConfigValue

The url should be more or less the same for all projects. Only differences will be project name (rso-catalogues),
configuration bundle name (config-bundle) and property name itself (string-property).

#### etcd browser
For easy configuration control in etcd, use etcd-browser: Link is here: https://github.com/henszey/etcd-browser
Download repo, build image and run it. Make sure to use the correct ETCD_HOST.