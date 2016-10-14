#!/bin/sh

BASEDIR=`dirname $0`/..
BASEDIR=`(cd "$BASEDIR"; pwd)`
COMMANDLINE_ARGS="$@"

INSTANCE_NAME=`echo $COMMANDLINE_ARGS | grep -ios "ins=.*$"`
INSTANCE_NAME=${INSTANCE_NAME##*=}
COMMANDLINE_ARGS=`echo $COMMANDLINE_ARGS | sed "s/ins=[0-9a-zA-Z\._0-9\-]*//"`

if [ -z "$INSTANCE_NAME" ] ; then
  echo instance is not setted, use default instance name "instance"
  INSTANCE_NAME="instance"
fi

# Instance configuration directory
if [ -z "$INSTANCE_CONF_DIR" ] ; then
    INSTANCE_CONF_DIR="$BASEDIR/conf"
fi

INSTANCE_CONF_FILE="${INSTANCE_CONF_DIR}/instance/${INSTANCE_NAME}.properties"
echo "$INSTANCE_CONF_FILE"
if [ ! -f "$INSTANCE_CONF_FILE" ]; then
  echo config file not found. ${INSTANCE_NAME}.properties
  exit 1
fi

SERVER_IP=`grep -ios 'server.ip=.*$' ${INSTANCE_CONF_FILE} | tr -d '\r'`
SERVER_IP=${SERVER_IP##*=}

JMX_PORT=`grep -ios 'jmx.port=.*$' ${INSTANCE_CONF_FILE} | tr -d '\r'`
JMX_PORT=${JMX_PORT##*=}

DEBUG_PORT=`grep -ios 'debug.port=.*$' ${INSTANCE_CONF_FILE} | tr -d '\r'`
DEBUG_PORT=${DEBUG_PORT##*=}

DEBUG_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=${DEBUG_PORT}"


OPTS_MEMORY=`grep -ios 'jvm.memory=.*$' ${BASEDIR}/conf/config.properties | tr -d '\r'`
OPTS_MEMORY=${OPTS_MEMORY##*=}
if [ -z "$OPTS_MEMORY" ] ; then
    OPTS_MEMORY="-Xms2G -Xmx2G"
fi

OPTS_MEMORY="$OPTS_MEMORY -server -XX:MaxPermSize=128M -Xss256K -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintTenuringDistribution -XX:+PrintGCApplicationStoppedTime"

INSTANCE_LOG=`grep -ios 'log.root.path=.*$' ${BASEDIR}/conf/config.properties | tr -d '\r'`
INSTANCE_LOG=${INSTANCE_LOG##*=}

if [ -z "$INSTANCE_LOG" ] ; then
  INSTANCE_LOG="$BASEDIR/log/$INSTANCE_NAME"
else
  INSTANCE_LOG="$INSTANCE_LOG/$INSTANCE_NAME"
fi

JAVACMD=`grep -ios 'java.cmd=.*$' ${BASEDIR}/conf/config.properties | tr -d '\r'`
JAVACMD=${JAVACMD##*=}


# If a specific java binary isn't specified search for the standard 'java' binary
if [ -z "$JAVACMD" ] ; then
  if [ -n "$JAVA_HOME"  ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
      # IBM's JDK on AIX uses strange locations for the executables
      JAVACMD="$JAVA_HOME/jre/sh/java"
    else
      JAVACMD="$JAVA_HOME/bin/java"
    fi
  else
    JAVACMD=`which java`
  fi
fi

CLASSPATH="$BASEDIR"/conf/:"$BASEDIR"/lib/*


#SUNJMX_START="-Dcom.sun.management.jmxremote.port=1005 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote -Djava.rmi.hostname=10.135.16.10"

SUNJMX_START="-Dcom.sun.management.jmxremote.port=${JMX_PORT}"
#SUNJMX_START="$SUNJMX_START -Dcom.sun.management.jmxremote.password.file=${INSTANCE_CONF_DIR}/jmx.password"
#SUNJMX_START="$SUNJMX_START -Dcom.sun.management.jmxremote.access.file=${INSTANCE_CONF_DIR}/jmx.access"

SUNJMX_START="$SUNJMX_START -Dcom.sun.management.jmxremote.authenticate=false"
SUNJMX_START="$SUNJMX_START -Dcom.sun.management.jmxremote.ssl=false"
SUNJMX_START="$SUNJMX_START -Dcom.sun.management.jmxremote"

if [ ! -z "$SERVER_IP" ] ; then
    SUNJMX_START="$SUNJMX_START -Djava.rmi.hostname=$SERVER_IP"
fi

if [ ! -x "$JAVACMD" ] ; then
  echo "Error: JAVA_HOME is not defined correctly."
  echo "  We cannot execute $JAVACMD"
  exit 1
fi


SYSTEM_ARGUMENTS="-Dinstance_name=$INSTANCE_NAME -Dinstance_log_path=$INSTANCE_LOG -Dbasedir=$BASEDIR -Dfile.encoding=UTF-8"

echo "$JAVACMD $JAVA_OPTS $OPTS_MEMORY $SUNJMX_START $DEBUG_OPTS -classpath $CLASSPATH $SYSTEM_ARGUMENTS "

nohup "$JAVACMD"  $JAVA_OPTS  $OPTS_MEMORY $SUNJMX_START $DEBUG_OPTS  -classpath "$CLASSPATH" $SYSTEM_ARGUMENTS com.mark.ifamily.Main "$@" >/dev/null 2>/dev/null &

########################################################################################################################
# support start multi instances by command line argument: ins=xxx
# for example: bin/start.sh ins=110_1
# if not any instance name specified, the process.sh will read configurations from conf/instancce/instance.properties
# global common config options defined in {conf/config.properties}
# each instance's special config options defined in {conf/instance/$instance_name.properties}
# differences config options:
#   log: log_path from pom.xml  and process.sh will convert to
#   jmx port\debug port from conf/instance/$instance_name.properties
#   instance server port from conf/instance/$instance_name.properties
