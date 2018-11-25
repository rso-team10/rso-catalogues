# rso-catalogues

Projekt se builda sedaj z Java 11. Nastavi JAVA_HOME na ta JDK. Pravilnost nastavitve se preveri preko:

```
mvn -version
```

Tu mora pisati, da se uporablja Java 11. Če vmes spremeniš JAVA_HOME moraš zagnati shell (in IntelliJ, če uporabljaš
terminal v njem).

### Configuration

#### IMPORTANT
Before everything, make sure to have a .env file in the root directory!

```
chmod u+x create-env.sh
./create-env-sh
```

#### Run etcd node
First install ectd locally. Figure out your Docker IP (ifconfig -> Docker ip) and save it.
Then setup a single node:

NOTE: Make sure the correct IP is used in the config.yaml file, under etcd hosts (an odd number of nodes is needed)
```
# SETUP AND RUN ETCD NODE
# FROM https://coreos.com/etcd/docs/latest/v2/docker_guide.html
# Deleted volume for the time being

# Run a etcd node
docker run -d -p 2379:2379 \
   --name etcd \
   --volume=/tmp/etcd-data:/etcd-data \
   quay.io/coreos/etcd:latest \
   /usr/local/bin/etcd \
   --name my-etcd-1 \
   --data-dir /etcd-data \
   --listen-client-urls http://0.0.0.0:2379 \
   --advertise-client-urls http://0.0.0.0:2379 \
   --listen-peer-urls http://0.0.0.0:2380 \
   --initial-advertise-peer-urls http://0.0.0.0:2380 \
   --initial-cluster my-etcd-1=http://0.0.0.0:2380 \
   --initial-cluster-token my-etcd-token \
   --initial-cluster-state new \
   --auto-compaction-retention 1 \
   -cors="*"
   
# List nodes, I guess?
etcdctl -C http://0.0.0.0:2379 member list
```

#### Create first property

To create a property for this project, use this command:

```
etcdctl --endpoints http://0.0.0.0:2379 set environments/dev/services/rso-catalogues/1.0.0/config/config-bundle/string-property test_string
```

config-bundle is what I called the bundle. Check ConfigurationProperties.java and the @ConfigBundle annotation

string-property will be mapped in Java into stringProperty. Annotate the stringProperty field with @ConfigValue

The url should be more or less the same for all projects. Only differences will be project name (rso-catalogues),
configuration bundle name (config-bundle) and property name itself (string-property).

#### etcd browser
For easy configuration control in etcd, use etcd-browser: Link is here: https://github.com/henszey/etcd-browser
Download repo, build image and run it. Make sure to use the correct ETCD_HOST.