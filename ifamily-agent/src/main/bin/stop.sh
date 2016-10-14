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

PIDPROC=`ps -ef | grep 'ifamily-server-runner/conf' | grep $INSTANCE_NAME_SYMBOL | grep -v 'grep'| awk '{print $2}'`

if [ -z "$PIDPROC" ];then
 echo "$APP_NAME($INSTANCE_NAME_SYMBOL) is not running"
 exit 0
fi

echo "PIDPROC: "$PIDPROC
for PID in $PIDPROC
do
if kill -9 $PID
   then echo "process $APP_NAME(Pid:$PID symbol:$INSTANCE_NAME_SYMBOL) was force stopped at " `date`
fi
done
echo stop finished.
