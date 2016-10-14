#!/bin/sh
APP_NAME=ifamily-agent
INSTANCE_NAME_SYMBOL="$1"
if [ -z "$INSTANCE_NAME_SYMBOL" ];then
    INSTANCE_NAME_SYMBOL="instance_name=instance"
else
    INSTANCE_NAME_SYMBOL=`echo $INSTANCE_NAME_SYMBOL | grep -ios "ins=.*$"`
    INSTANCE_NAME_SYMBOL=${INSTANCE_NAME_SYMBOL##*=}
    INSTANCE_NAME_SYMBOL="instance_name=$INSTANCE_NAME_SYMBOL"
fi

PIDPROC=`ps -ef | grep '$APP_NAME/conf' | grep $INSTANCE_NAME_SYMBOL | grep -v 'grep'| awk '{print $2}'`

COMMANDLINE_ARGS="$@"
echo "COMMAND LINE: $0 $COMMANDLINE_ARGS"
if [ -z "$PIDPROC" ];then
 PRG="$0"
 PRGDIR=`dirname "$PRG"`
 cd $PRGDIR
 sh process.sh $COMMANDLINE_ARGS &
 PIDPROC=`ps -ef | grep '$APP_NAME/conf' | grep $INSTANCE_NAME_SYMBOL | grep -v 'grep'| awk '{print $2}'`
 echo $PIDPROC
else
 echo "$APP_NAME(pid:$PIDPROC symbol:$INSTANCE_NAME_SYMBOL) is running"
fi