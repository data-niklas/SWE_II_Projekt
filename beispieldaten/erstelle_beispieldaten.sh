#!/bin/env sh
PORT=8081
HOST=localhost
URL="http://$HOST:$PORT"
DIR="$(dirname "$0")"

function create_type(){
    find $DIR/$1 -type f | grep .json | xargs -I % curl --header "Content-Type: application/json" \
    --request POST \
    --data "@%" \
    $URL/$1
}

create_type bahnhof
create_type zug
create_type strecke
echo "Daten wurden erfolgreich? angelegt"
