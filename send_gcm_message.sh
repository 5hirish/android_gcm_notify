#! /usr/bin/bash

echo 'Enter the Title:'
read title
echo 'Enter the Message:'
read message
echo 'Select the topic: 1)FE 2)SE 3)TE 4)BE'
read ch

topic="/topics/FE"

case $ch in

	1)
		topic="/topics/FE";;
	2)
		topic="/topics/SE";;
	3)
		topic="/topics/TE";;
	4)
		topic="/topics/BE";;
esac

echo $topic
mtime=$(date +'%d/%b at %I:%M %p')
echo $mtime

#echo "curl -s https://gcm-http.googleapis.com/gcm/send -H Authorization: key=AIzaSyCZA3xTFBzLZvMsJNUqc7ZE2doCNCGZBxE -H Content-Type: application/json -d {to: $topic, data: {title:$title,message: $message,time:$mtime}}"
curl -s "https://gcm-http.googleapis.com/gcm/send" -H "Authorization: key=AIzaSyCZA3xTFBzLZvMsJNUqc7ZE2doCNCGZBxE" -H "Content-Type: application/json" -d '{"to": "'"$topic"'", "data": {"title":"'"$title"'","message":"'"$message"'","time":"'"$mtime"'"}}'
