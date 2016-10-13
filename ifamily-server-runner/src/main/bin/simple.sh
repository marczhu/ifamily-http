#!/bin/sh

BASEDIR=`dirname $0`/..
BASEDIR=`(cd "$BASEDIR"; pwd)`

CLASSPATH="$BASEDIR"/conf/:"$BASEDIR"/lib/*
LOG_DIR="$BASEDIR/log/"
LOG_LEVEL="debug"
INSTANCE_CONF_DIR="$BASEDIR/conf"


SUNJMX_START="-Dcom.sun.management.jmxremote.port=1005 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote -Djava.rmi.hostname=10.135.16.10"

SUNJMX_START="-Dcom.sun.management.jmxremote.port=11099 "
SUNJMX_START="$SUNJMX_START -Dcom.sun.management.jmxremote.password.file=${INSTANCE_CONF_DIR}/jmx.password"
SUNJMX_START="$SUNJMX_START -Dcom.sun.management.jmxremote.access.file=${INSTANCE_CONF_DIR}/jmx.access"
SUNJMX_START="$SUNJMX_START -Dcom.sun.management.jmxremote.ssl=false"
SUNJMX_START="$SUNJMX_START -Dcom.sun.management.jmxremote"

#SUNJMX_START="$SUNJMX_START -Djava.rmi.hostname=${SERVER_IP}"


if [ -z "$OPTS_MEMORY" ] ; then
    OPTS_MEMORY="-Xms2G -Xmx2G -server -XX:MaxPermSize=256M -Xss256K "
fi

DEBUG_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5055"


/usr/java/jdk1.6.0_45/bin/java $OPTS_MEMORY $SUNJMX_START $DEBUG_OPTS -classpath $CLASSPATH com.haier.scf.Main "$@"
#nohup /usr/java/jdk1.6.0_45/bin/java $OPTS_MEMORY $SUNJMX_START $DEBUG_OPTS -classpath $CLASSPATH com.haier.scf.Main "$@" >/dev/null 2>/dev/null &
